# Refactoring to Patterns: Before / After

> 🇹🇷 [Türkçe](../tr/refactoring.md) · **Code:** [`refactoring`](../../src/main/java/designpatterns/refactoring) · **Tests:** [`RefactoringTest`](../../src/test/java/designpatterns/refactoring/RefactoringTest.java)

Patterns are rarely designed up front; they usually **appear while cleaning up code that already works**.
This page shows the naive "before" code you find in real projects, the smell that tells you something is
wrong, and the pattern that removes it.

Every "before" class in the [`refactoring`](../../src/main/java/designpatterns/refactoring) package is real, compiling code, and
[`RefactoringTest`](../../src/test/java/designpatterns/refactoring/RefactoringTest.java) proves that the "before" and "after" versions **behave exactly the same**.
That is the definition of a refactoring: better structure, same behaviour.

## How to refactor safely

1. **Pin the current behaviour with tests** (characterization tests) – even ugly code has users.
2. Change the structure in **small steps**, running the tests after each one.
3. Don't add features while refactoring. Two hats: either you refactor or you add behaviour.
4. Stop when the smell is gone. The goal is simpler code, not more patterns.

## Smell → pattern

| Smell you see | Pattern to consider |
|---|---|
| Constructors with many parameters, swapped `boolean`s | [Builder](#builder) |
| `switch (type) { case ...: return new ... }` repeated in places | [Factory Method](#factory-method) |
| A subclass for every combination of features | [Decorator](#decorator) |
| An `if / else if` ladder deciding who handles a request | [Chain of Responsibility](#chain-of-responsibility) |
| A class calling every interested party directly after a change | [Observer](#observer) |
| `switch (status)` in every method | [State](#state) |
| `switch (campaign)` choosing an algorithm | [Strategy](#strategy) |
| Copy-pasted methods that differ in a few lines | [Template Method](#template-method) |
| `if (x != null)` everywhere for an expected "missing" case | [Null Object](#null-object) |
| `findByAAndBAndC` finder method explosion | [Specification](#specification) |
| `new` and `now()` inside business logic, untestable code | [Dependency Injection](#dependency-injection) |

---

### Builder

**Before** – telescoping constructors ([`CarBefore`](../../src/main/java/designpatterns/refactoring/builder/CarBefore.java)):

```java
new CarBefore("BMW", "Z4", "Red", 340, true, false);   // which boolean is the sunroof?
```

**After** – [Builder](creational/builder.md):

```java
Car.builder().brand("BMW").model("Z4").color("Red").horsePower(340).sunroof(true).build();
```

Readable at the call site, optional fields have defaults, validation happens once in `build()`, and the
`Car` stays immutable.

### Factory Method

**Before** – one class knows every brand and model ([`CarDealerBefore`](../../src/main/java/designpatterns/refactoring/factorymethod/CarDealerBefore.java)):

```java
Car car = switch (brand.toUpperCase()) {
    case "AUDI" -> switch (model.toUpperCase()) { case "A4" -> new AudiA4(); ... };
    case "BMW"  -> switch (model.toUpperCase()) { case "Z4" -> new BmwZ4();  ... };
    default -> throw new IllegalArgumentException("Unknown brand " + brand);
};
```

**After** – [Factory Method](creational/factory-method.md): `new AudiFactory().order("A4")`. Adding Mercedes is a
new `MercedesFactory` class; the ordering logic (`order`, quality check) is written once and never changes.

### Decorator

**Before** – a subclass per combination ([`CoffeeBefore`](../../src/main/java/designpatterns/refactoring/decorator/CoffeeBefore.java)):
`EspressoWithMilk`, `EspressoWithCaramel`, `EspressoWithMilkAndCaramel`... 2 coffees × 4 extras = **32 classes**,
and the price of milk is repeated in many of them.

**After** – [Decorator](structural/decorator.md): 2 + 4 classes, combined at runtime, even "double milk":

```java
Coffee coffee = new Caramel(new Milk(new Milk(new Espresso())));
```

### Chain of Responsibility

**Before** – a hard-coded ladder ([`ExpenseApprovalBefore`](../../src/main/java/designpatterns/refactoring/chainofresponsibility/ExpenseApprovalBefore.java)):

```java
if (amount <= 1000)        return "Team lead approved ...";
else if (amount <= 10000)  return "Manager approved ...";
else if (amount <= 100000) return "Director approved ...";
else                       return "Nobody can approve ...";
```

**After** – [Chain of Responsibility](behavioral/chain-of-responsibility.md): each approver knows only its own limit;
the chain is assembled (and can be reordered or extended with a CFO) outside:
`new TeamLead().setNext(new Manager()).setNext(new Director())`.

### Observer

**Before** – the subject calls everybody itself ([`StockMarketBefore`](../../src/main/java/designpatterns/refactoring/observer/StockMarketBefore.java)):

```java
mobileApp.onPriceChanged(symbol, oldPrice, newPrice);
priceAlert.onPriceChanged(symbol, oldPrice, newPrice);
// smsService.send(...);   <- next sprint, edit this class again
```

**After** – [Observer](behavioral/observer.md): `market.subscribe(app)`. The market only knows the
`StockObserver` interface; new subscribers don't touch it and can subscribe/unsubscribe at runtime.

### State

**Before** – a status field and a `switch` in every method ([`OrderBefore`](../../src/main/java/designpatterns/refactoring/state/OrderBefore.java)):

```java
public void cancel() {
    switch (status) {
        case NEW  -> { history.add("cancelled before payment"); status = CANCELLED; }
        case PAID -> { history.add("cancelled, money refunded"); status = CANCELLED; }
        default   -> throw invalid("cancel");
    }
}
// ...and the same shape in pay(), ship(), deliver()
```

**After** – [State](behavioral/state.md): all rules of one state live in one place (`OrderStates.PAID`), and
`Order.cancel()` is a single line: `state = state.cancel(this)`.

### Strategy

**Before** – the cart knows every campaign ([`ShoppingCartBefore`](../../src/main/java/designpatterns/refactoring/strategy/ShoppingCartBefore.java)):

```java
switch (campaign) {
    case "TEN_PERCENT": return subtotal.multiply(new BigDecimal("0.90"))...;
    case "200_OFF_OVER_1500": ...
    case "BUY_3_PAY_2": ...
    default: throw new IllegalArgumentException("Unknown campaign: " + campaign);
}
```

**After** – [Strategy](behavioral/strategy.md): `cart.setDiscount(DiscountStrategy.percentage(10))`. Each campaign
is a small, separately testable object (or a lambda); marketing's next idea doesn't touch the cart.

### Template Method

**Before** – two copies of the same algorithm ([`ReportsBefore`](../../src/main/java/designpatterns/refactoring/templatemethod/ReportsBefore.java)):
`toCsv` and `toJson` both filter invalid rows, write a header, join rows and add a footer.
"Also skip refunds" must be changed in both – one is always forgotten.

**After** – [Template Method](behavioral/template-method.md): `ReportExporter.export()` owns the steps;
`CsvReportExporter` and `JsonReportExporter` only provide what is different (header, row format, separator, footer).

### Null Object

**Before** – `null` means "guest" ([`CheckoutBefore`](../../src/main/java/designpatterns/refactoring/nullobject/CheckoutBefore.java)):

```java
String name  = customer != null ? customer.name() : "Guest";
int discount = customer != null ? customer.discountPercent() : 0;
```

**After** – [Null Object](enterprise/null-object.md): the directory returns `GuestCustomer.INSTANCE`; the checkout
code has no null checks at all.

### Specification

**Before** – finder explosion ([`ProductFinderBefore`](../../src/main/java/designpatterns/refactoring/specification/ProductFinderBefore.java)):
`findByCategoryAndPriceLessThan`, `findInStockWithRatingAtLeast`, `findByCategoryAndOutOfStock`... with
"in stock" copied into each.

**After** – [Specification](enterprise/specification.md): one `findAll(spec)` and small named rules:

```java
catalog.findAll(inCategory("books").and(inStock().not()));
```

### Dependency Injection

**Before** – the service builds its own dependencies ([`WelcomeServiceBefore`](../../src/main/java/designpatterns/refactoring/dependencyinjection/WelcomeServiceBefore.java)):

```java
private final EmailNotifier notifier = new EmailNotifier();
int hour = LocalTime.now().getHour();
```

The test can only check that the message ends with `", Cem!"` – the greeting depends on when it runs.

**After** – [Dependency Injection](enterprise/dependency-injection.md): `new WelcomeService(notifier, clock)`.
The test chooses the time and captures the message: `assertEquals("Good evening, Cem!", ...)`.
