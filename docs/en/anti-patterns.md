# Anti-patterns

> 🇹🇷 [Türkçe](../tr/anti-patterns.md)

An **anti-pattern** is a common response to a recurring problem that *looks* like a good idea but
causes more problems than it solves. Knowing them is as useful as knowing the patterns: most code
reviews are about spotting these.

| Anti-pattern | In one sentence | Way out |
|---|---|---|
| [God Object](#god-object) | One class knows and does everything | SRP, Facade, extract classes |
| [Singleton abuse](#singleton-abuse) | Global mutable state with a pattern's name on it | Dependency Injection |
| [Service Locator](#service-locator) | Classes fetch their own dependencies from a registry | Dependency Injection |
| [Golden Hammer (pattern-itis)](#golden-hammer-pattern-itis) | Using a favourite pattern everywhere | YAGNI, KISS |
| [Anemic Domain Model](#anemic-domain-model) | Entities with only getters/setters, logic in services | Rich domain model |
| [Primitive Obsession](#primitive-obsession) | Strings and ints instead of small types | Value objects / records |
| [Boolean flag parameters](#boolean-flag-parameters) | `send(msg, true, false)` | Separate methods, Builder, enums |
| [Exception swallowing](#exception-swallowing) | `catch (Exception e) {}` | Handle, wrap or rethrow |
| [Copy-paste programming](#copy-paste-programming) | The same logic in many places | Extract method, Template Method, Strategy |
| [Lava Flow](#lava-flow) | Dead code nobody dares to delete | Tests + version control + delete |
| [Distributed Monolith](#distributed-monolith) | Microservices that must be deployed together | Clear boundaries, async events |
| [Shared Database](#shared-database) | Many services writing the same tables | Database per service, Outbox, CQRS |

---

## God Object

**What:** A class like `OrderManager` with 3 000 lines, 40 dependencies and methods for orders,
payments, e-mails, PDF invoices and reporting.

**Why it hurts:** every change touches it, merge conflicts never end, it can't be tested without
mocking half of the system, and nobody understands it completely.

**Way out:** split by responsibility (Single Responsibility Principle). Extract cohesive groups of
methods into their own classes; put a [Facade](structural/facade.md) in front if callers need one entry point.

## Singleton abuse

**What:** `AppContext.getInstance().getUser()`, `Config.getInstance()`, `Cache.getInstance()` used from
everywhere.

```java
public BigDecimal price(Product p) {
    BigDecimal rate = CurrencyService.getInstance().rate();   // hidden dependency
    ...
}
```

**Why it hurts:** a singleton with mutable state is a **global variable**. Dependencies are hidden in
method bodies, tests affect each other through shared state, and replacing it with a fake is hard.

**Way out:** keep "only one instance" but let a container manage it and **inject** it
([Dependency Injection](enterprise/dependency-injection.md)). A stateless singleton (an `enum` with pure
functions) is fine. See [Singleton](creational/singleton.md).

## Service Locator

**What:** classes ask a global registry for their collaborators.

```java
public class WelcomeService {
    public void welcome(String email) {
        Notifier notifier = ServiceLocator.get(Notifier.class);
        ...
    }
}
```

**Why it hurts:** the constructor lies – you can't see what the class needs without reading every
method; missing registrations are discovered at runtime; tests must configure the global locator.

**Way out:** constructor injection. Everything the class needs is visible in its constructor.

## Golden Hammer (pattern-itis)

**What:** "I just learned Abstract Factory" → `UserFactoryFactoryProvider`. Patterns applied because
they are cool, not because there is a problem.

**Why it hurts:** five classes and three interfaces for something an `if` would solve. More code to
read, more indirection to debug, no benefit.

**Way out:** **YAGNI** (You Aren't Gonna Need It) and **KISS**. Start simple, introduce a pattern
when you feel the pain it solves – see [Refactoring to patterns](refactoring.md).

## Anemic Domain Model

**What:** entities are data bags; all rules live in "service" classes.

```java
order.setStatus(Status.SHIPPED);          // any code can put the order in any state
order.setTotal(order.getTotal().add(x));  // invariants are not protected
```

**Why it hurts:** business rules are scattered and duplicated across services; an object can be in an
invalid state because it can't protect itself.

**Way out:** put behaviour next to the data: `order.ship()`, `order.addLine(product, qty)`. The
[State](behavioral/state.md) example is a rich model: `Order` enforces its own transitions.
(For simple CRUD apps an anemic model is acceptable – don't over-engineer.)

## Primitive Obsession

**What:** `String iban`, `String email`, `BigDecimal amount` plus `String currency`, `long userId` and
`long orderId` that can be swapped.

**Why it hurts:** validation is repeated everywhere (or forgotten), and `transfer(orderId, userId)`
compiles even when the arguments are in the wrong order.

**Way out:** small value types – Java **records** make them one line:

```java
public record Email(String value) {
    public Email {
        if (!value.contains("@")) throw new IllegalArgumentException("invalid e-mail");
    }
}
public record Money(BigDecimal amount, Currency currency) { }
```

## Boolean flag parameters

**What:** `report.export(sales, true, false, true)`.

**Why it hurts:** unreadable at the call site, and each flag doubles the number of code paths inside
the method.

**Way out:** separate methods (`exportWithHeader`), an `enum` (`Format.CSV`), a parameter object or a
[Builder](creational/builder.md). If the flag selects an algorithm, it's a [Strategy](behavioral/strategy.md).

## Exception swallowing

**What:**

```java
try {
    paymentClient.charge(order);
} catch (Exception e) {
    // TODO
}
```

**Why it hurts:** the customer isn't charged, the order is shipped, and there is no log line to tell
you why. The hardest bugs to find are the ones that leave no trace.

**Way out:** handle it (retry, fallback – see [Circuit Breaker](microservice/circuit-breaker.md)), wrap it in a
meaningful exception, or let it propagate. At the very least log it with context.

## Copy-paste programming

**What:** the same 20 lines in `CsvExporter` and `JsonExporter`, or the same validation in five
controllers.

**Why it hurts:** a bug fix is applied to one copy and forgotten in the others (*shotgun surgery*).

**Way out:** extract a method or class; if the copies differ in a few steps use
[Template Method](behavioral/template-method.md) or [Strategy](behavioral/strategy.md).

## Lava Flow

**What:** commented-out code, unused classes, `OldPaymentService2`, feature flags that were turned on
three years ago – nobody knows if they are still needed, so nobody deletes them.

**Why it hurts:** it confuses readers, slows builds, and someone eventually "fixes" dead code.

**Way out:** tests give you confidence, version control remembers everything: delete it. Use
`@Deprecated(forRemoval = true)` for public APIs and remove them on schedule.

## Distributed Monolith

**What:** the system is split into microservices, but every feature changes five services that must be
deployed together; services call each other synchronously in long chains.

**Why it hurts:** you get the complexity of a distributed system (network failures, latency, partial
deployments) **and** the coupling of a monolith.

**Way out:** draw service boundaries around business capabilities (DDD bounded contexts), prefer
asynchronous events, protect synchronous calls with a [Circuit Breaker](microservice/circuit-breaker.md),
and keep transactions local ([Saga](microservice/saga.md)). Sometimes the honest answer is a modular monolith.

## Shared Database

**What:** the order, payment and shipping services all read and write the same tables.

**Why it hurts:** a schema change in one service breaks the others; the database becomes the hidden
integration API; services can't be scaled or deployed independently.

**Way out:** **database per service**. Share data through APIs and events
([Transactional Outbox](microservice/transactional-outbox.md)); build local read models when a service needs
another service's data ([CQRS](microservice/cqrs.md)).

---

Further reading: *AntiPatterns* (Brown et al., 1998), *Refactoring* (Martin Fowler), *Clean Code*
(Robert C. Martin), *Microservices Patterns* (Chris Richardson).
