# Kalıplara Doğru Refactoring: Önce / Sonra

> 🇬🇧 [English](../en/refactoring.md) · **Kod:** [`refactoring`](../../src/main/java/designpatterns/refactoring) · **Testler:** [`RefactoringTest`](../../src/test/java/designpatterns/refactoring/RefactoringTest.java)

Kalıplar nadiren baştan tasarlanır; genelde **zaten çalışan kodu temizlerken ortaya çıkarlar**. Bu sayfa
gerçek projelerde karşılaşılan saf "önce" kodunu, bir şeylerin yanlış olduğunu söyleyen kötü kokuyu (smell)
ve onu ortadan kaldıran kalıbı gösterir.

[`refactoring`](../../src/main/java/designpatterns/refactoring) paketindeki her "önce" sınıfı gerçek, derlenen koddur ve [`RefactoringTest`](../../src/test/java/designpatterns/refactoring/RefactoringTest.java)
"önce" ile "sonra" versiyonlarının **birebir aynı davrandığını** kanıtlar. Refactoring'in tanımı da budur:
daha iyi yapı, aynı davranış.

## Güvenli refactoring nasıl yapılır?

1. **Mevcut davranışı testlerle sabitleyin** (characterization test) – çirkin kodun da kullanıcıları vardır.
2. Yapıyı **küçük adımlarla** değiştirin ve her adımdan sonra testleri çalıştırın.
3. Refactoring yaparken özellik eklemeyin. İki şapka: ya refactor edersiniz ya davranış eklersiniz.
4. Kötü koku gidince durun. Amaç daha fazla kalıp değil, daha basit koddur.

## Kötü koku → kalıp

| Gördüğünüz kötü koku | Düşünülecek kalıp |
|---|---|
| Çok parametreli constructor'lar, yer değiştirmiş `boolean`'lar | [Builder](#builder) |
| Birçok yerde tekrarlanan `switch (type) { case ...: return new ... }` | [Factory Method](#factory-method) |
| Her özellik kombinasyonu için bir alt sınıf | [Decorator](#decorator) |
| İsteği kimin ele alacağına karar veren `if / else if` merdiveni | [Chain of Responsibility](#chain-of-responsibility) |
| Bir değişiklikten sonra ilgili herkesi doğrudan çağıran sınıf | [Observer](#observer) |
| Her metotta `switch (status)` | [State](#state) |
| Algoritma seçen `switch (campaign)` | [Strategy](#strategy) |
| Birkaç satırı farklı, kopyala-yapıştır metotlar | [Template Method](#template-method) |
| Beklenen bir "yok" durumu için her yerde `if (x != null)` | [Null Object](#null-object) |
| `findByAAndBAndC` arama metodu patlaması | [Specification](#specification) |
| İş mantığının içinde `new` ve `now()`, test edilemeyen kod | [Dependency Injection](#dependency-injection) |

---

### Builder

**Önce** – iç içe constructor'lar (telescoping) ([`CarBefore`](../../src/main/java/designpatterns/refactoring/builder/CarBefore.java)):

```java
new CarBefore("BMW", "Z4", "Red", 340, true, false);   // sunroof hangi boolean?
```

**Sonra** – [Builder](creational/builder.md):

```java
Car.builder().brand("BMW").model("Z4").color("Red").horsePower(340).sunroof(true).build();
```

Çağrıldığı yerde okunabilir, opsiyonel alanların varsayılanı var, doğrulama `build()` içinde bir kez yapılır
ve `Car` değişmez kalır.

### Factory Method

**Önce** – tek bir sınıf tüm markaları ve modelleri biliyor ([`CarDealerBefore`](../../src/main/java/designpatterns/refactoring/factorymethod/CarDealerBefore.java)):

```java
Car car = switch (brand.toUpperCase()) {
    case "AUDI" -> switch (model.toUpperCase()) { case "A4" -> new AudiA4(); ... };
    case "BMW"  -> switch (model.toUpperCase()) { case "Z4" -> new BmwZ4();  ... };
    default -> throw new IllegalArgumentException("Unknown brand " + brand);
};
```

**Sonra** – [Factory Method](creational/factory-method.md): `new AudiFactory().order("A4")`. Mercedes eklemek yeni
bir `MercedesFactory` sınıfıdır; sipariş mantığı (`order`, kalite kontrol) bir kez yazılır ve hiç değişmez.

### Decorator

**Önce** – her kombinasyon için bir alt sınıf ([`CoffeeBefore`](../../src/main/java/designpatterns/refactoring/decorator/CoffeeBefore.java)):
`EspressoWithMilk`, `EspressoWithCaramel`, `EspressoWithMilkAndCaramel`... 2 kahve × 4 ekstra = **32 sınıf**;
sütün fiyatı birçoğunda tekrarlanıyor.

**Sonra** – [Decorator](structural/decorator.md): 2 + 4 sınıf, çalışma zamanında birleştirilir, "çift süt" bile olur:

```java
Coffee coffee = new Caramel(new Milk(new Milk(new Espresso())));
```

### Chain of Responsibility

**Önce** – sabit kodlanmış merdiven ([`ExpenseApprovalBefore`](../../src/main/java/designpatterns/refactoring/chainofresponsibility/ExpenseApprovalBefore.java)):

```java
if (amount <= 1000)        return "Team lead approved ...";
else if (amount <= 10000)  return "Manager approved ...";
else if (amount <= 100000) return "Director approved ...";
else                       return "Nobody can approve ...";
```

**Sonra** – [Chain of Responsibility](behavioral/chain-of-responsibility.md): her onaylayıcı sadece kendi limitini bilir;
zincir dışarıda kurulur (sırası değiştirilebilir, CFO eklenebilir):
`new TeamLead().setNext(new Manager()).setNext(new Director())`.

### Observer

**Önce** – subject herkesi kendisi çağırıyor ([`StockMarketBefore`](../../src/main/java/designpatterns/refactoring/observer/StockMarketBefore.java)):

```java
mobileApp.onPriceChanged(symbol, oldPrice, newPrice);
priceAlert.onPriceChanged(symbol, oldPrice, newPrice);
// smsService.send(...);   <- gelecek sprint, bu sınıf yine değişecek
```

**Sonra** – [Observer](behavioral/observer.md): `market.subscribe(app)`. Borsa sadece `StockObserver` arayüzünü bilir;
yeni aboneler ona dokunmaz ve çalışma zamanında abone olup ayrılabilir.

### State

**Önce** – bir status alanı ve her metotta bir `switch` ([`OrderBefore`](../../src/main/java/designpatterns/refactoring/state/OrderBefore.java)):

```java
public void cancel() {
    switch (status) {
        case NEW  -> { history.add("cancelled before payment"); status = CANCELLED; }
        case PAID -> { history.add("cancelled, money refunded"); status = CANCELLED; }
        default   -> throw invalid("cancel");
    }
}
// ...ve pay(), ship(), deliver() içinde aynı şekil
```

**Sonra** – [State](behavioral/state.md): bir durumun bütün kuralları tek yerde (`OrderStates.PAID`) ve
`Order.cancel()` tek satır: `state = state.cancel(this)`.

### Strategy

**Önce** – sepet tüm kampanyaları biliyor ([`ShoppingCartBefore`](../../src/main/java/designpatterns/refactoring/strategy/ShoppingCartBefore.java)):

```java
switch (campaign) {
    case "TEN_PERCENT": return subtotal.multiply(new BigDecimal("0.90"))...;
    case "200_OFF_OVER_1500": ...
    case "BUY_3_PAY_2": ...
    default: throw new IllegalArgumentException("Unknown campaign: " + campaign);
}
```

**Sonra** – [Strategy](behavioral/strategy.md): `cart.setDiscount(DiscountStrategy.percentage(10))`. Her kampanya
küçük, ayrı test edilebilen bir nesne (ya da lambda); pazarlamanın bir sonraki fikri sepete dokunmaz.

### Template Method

**Önce** – aynı algoritmanın iki kopyası ([`ReportsBefore`](../../src/main/java/designpatterns/refactoring/templatemethod/ReportsBefore.java)):
`toCsv` ve `toJson` ikisi de geçersiz satırları eler, başlık yazar, satırları birleştirir ve alt bilgi ekler.
"İadeleri de atla" değişikliği ikisinde de yapılmalı – biri hep unutulur.

**Sonra** – [Template Method](behavioral/template-method.md): adımlar `ReportExporter.export()`'a aittir;
`CsvReportExporter` ve `JsonReportExporter` sadece farklı olanı (başlık, satır formatı, ayırıcı, alt bilgi) verir.

### Null Object

**Önce** – `null` "misafir" demek ([`CheckoutBefore`](../../src/main/java/designpatterns/refactoring/nullobject/CheckoutBefore.java)):

```java
String name  = customer != null ? customer.name() : "Guest";
int discount = customer != null ? customer.discountPercent() : 0;
```

**Sonra** – [Null Object](enterprise/null-object.md): rehber `GuestCustomer.INSTANCE` döndürür; ödeme kodunda hiç
null kontrolü yok.

### Specification

**Önce** – arama metodu patlaması ([`ProductFinderBefore`](../../src/main/java/designpatterns/refactoring/specification/ProductFinderBefore.java)):
`findByCategoryAndPriceLessThan`, `findInStockWithRatingAtLeast`, `findByCategoryAndOutOfStock`... ve her birine
kopyalanmış "stokta var" kuralı.

**Sonra** – [Specification](enterprise/specification.md): tek bir `findAll(spec)` ve küçük, isimli kurallar:

```java
catalog.findAll(inCategory("books").and(inStock().not()));
```

### Dependency Injection

**Önce** – servis kendi bağımlılıklarını oluşturuyor ([`WelcomeServiceBefore`](../../src/main/java/designpatterns/refactoring/dependencyinjection/WelcomeServiceBefore.java)):

```java
private final EmailNotifier notifier = new EmailNotifier();
int hour = LocalTime.now().getHour();
```

Test sadece mesajın `", Cem!"` ile bittiğini kontrol edebiliyor – selamlama testin çalıştığı saate bağlı.

**Sonra** – [Dependency Injection](enterprise/dependency-injection.md): `new WelcomeService(notifier, clock)`.
Test saati kendisi seçer ve mesajı yakalar: `assertEquals("Good evening, Cem!", ...)`.
