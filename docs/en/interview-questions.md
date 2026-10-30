# Interview Questions & Exercises

> 🇹🇷 [Türkçe](../tr/interview-questions.md) · More Java interview topics: [cemdrman/interview-preparation](https://github.com/cemdrman/interview-preparation)

Questions that come up again and again in Java interviews, with short answers. Try to answer first,
then open the answer. The exercises at the end are small changes to this repository – the best way to
really learn a pattern is to extend one.

## General

**1. What is a design pattern? Is it a library?**

<details markdown="1"><summary>Answer</summary>

A named, proven solution template for a recurring design problem. It is not code you import; it's a
structure you adapt. Its value is also the shared vocabulary: "wrap it in a decorator" explains a design in
four words.
</details>

**2. What are the three GoF categories?**

<details markdown="1"><summary>Answer</summary>

Creational (how objects are created), Structural (how objects are composed), Behavioral (how objects
communicate and share responsibility). See the [introduction](00-introduction.md).
</details>

**3. "Favour composition over inheritance" – which patterns apply it?**

<details markdown="1"><summary>Answer</summary>

Strategy, State, Decorator, Bridge, Composite, Observer, Chain of Responsibility... Composition can be
changed at runtime and doesn't lock you into a class hierarchy; inheritance is fixed at compile time
and exposes parent internals (fragile base class).
</details>

**4. When should you *not* use a pattern?**

<details markdown="1"><summary>Answer</summary>

When there is no problem yet. A pattern adds classes and indirection; if a simple `if` or a lambda is
enough, use it (YAGNI, KISS). See [Anti-patterns → Golden Hammer](anti-patterns.md#golden-hammer-pattern-itis).
</details>

**5. Name patterns used in the JDK.**

<details markdown="1"><summary>Answer</summary>

Iterator (`Iterator`), Decorator (`BufferedInputStream`), Adapter (`Arrays.asList`, `InputStreamReader`),
Strategy (`Comparator`), Observer (`PropertyChangeListener`, `Flow`), Factory Method (`List.of`,
`Calendar.getInstance`), Builder (`StringBuilder`, `HttpRequest.newBuilder`), Proxy (`java.lang.reflect.Proxy`),
Flyweight (`Integer.valueOf` cache), Template Method (`AbstractList`), Command (`Runnable`), Singleton (`Runtime`).
</details>

## Creational

**6. How do you write a thread-safe singleton? Which way is best?**

<details markdown="1"><summary>Answer</summary>

Eager static field, holder idiom (lazy + thread-safe thanks to class loading), double-checked locking with
a **`volatile`** field, or an **`enum`**. `enum` is the simplest and is safe against reflection and
serialization. In applications, prefer a container-managed singleton (Spring bean). See [Singleton](creational/singleton.md).
</details>

**7. Why does double-checked locking need `volatile`?**

<details markdown="1"><summary>Answer</summary>

`instance = new X()` is not atomic: allocate, run constructor, assign reference. Without `volatile` these
can be reordered, so another thread may see a non-null reference to an object whose constructor hasn't
finished. `volatile` gives a happens-before guarantee.
</details>

**8. How can a singleton be broken?**

<details markdown="1"><summary>Answer</summary>

Reflection (calling the private constructor), serialization (deserialization creates a new object unless
`readResolve` returns the instance), cloning, and multiple class loaders. An `enum` singleton is immune
to the first two.
</details>

**9. Simple Factory vs Factory Method vs Abstract Factory?**

<details markdown="1"><summary>Answer</summary>

Simple factory: one method with a `switch` that creates objects (not a GoF pattern). Factory Method: a
subclass decides which product to create (inheritance). Abstract Factory: an object creates a **family**
of related products (composition), e.g. light/dark UI widgets.
</details>

**10. When is Builder better than a constructor?**

<details markdown="1"><summary>Answer</summary>

Many parameters, especially optional ones; when you want immutability with readable construction; when
validation involves several fields. For 2–3 required parameters a constructor or a static factory is
clearer. Records + a compact constructor cover many simple cases.
</details>

**11. Shallow vs deep copy? Why is `Cloneable` considered broken?**

<details markdown="1"><summary>Answer</summary>

A shallow copy shares mutable fields (lists, dates) with the original; a deep copy copies them too.
`Cloneable` has no `clone()` method, `Object.clone()` is protected, does a shallow copy, bypasses
constructors and throws a checked exception. Prefer copy constructors or `copy()` methods. See [Prototype](creational/prototype.md).
</details>

## Structural

**12. Adapter vs Decorator vs Proxy vs Facade?**

<details markdown="1"><summary>Answer</summary>

Adapter **changes** the interface. Decorator **keeps** it and adds behaviour. Proxy **keeps** it and
controls access (lazy, security, remote, caching). Facade offers a **new, simpler** interface over many classes.
</details>

**13. JDK dynamic proxy vs CGLIB proxy?**

<details markdown="1"><summary>Answer</summary>

JDK proxies (`java.lang.reflect.Proxy`) implement interfaces only. CGLIB/ByteBuddy create a subclass, so
they work without interfaces but can't proxy `final` classes/methods. Spring Boot uses class-based proxies
by default. Both share the self-invocation problem.
</details>

**14. What is intrinsic vs extrinsic state in Flyweight? Example from the JDK?**

<details markdown="1"><summary>Answer</summary>

Intrinsic: shared, immutable (tree type, texture). Extrinsic: per-object context passed in (x, y). JDK:
`Integer.valueOf(-128..127)` returns cached instances – that's why `==` works for small integers and fails for big ones.
</details>

**15. Why is `java.io` a good example of Decorator?**

<details markdown="1"><summary>Answer</summary>

`new BufferedReader(new InputStreamReader(new FileInputStream(f)))` – every class is a `Reader`/`InputStream`
that wraps another and adds one responsibility (buffering, decoding...). The downside: many small classes.
</details>

## Behavioral

**16. Strategy vs State?**

<details markdown="1"><summary>Answer</summary>

Same structure. In Strategy the **client** chooses the algorithm and strategies don't know each other. In
State the **state objects** decide the transitions and know the next state.
</details>

**17. Strategy vs Template Method?**

<details markdown="1"><summary>Answer</summary>

Template Method varies **steps** through inheritance (fixed at compile time). Strategy varies the **whole
algorithm** through composition (changeable at runtime). Modern Java leans to Strategy with lambdas.
</details>

**18. What problems can Observer cause?**

<details markdown="1"><summary>Answer</summary>

Memory leaks (subscribers never unsubscribed – *lapsed listener*), unexpected notification order, a slow
observer blocking the subject, exceptions in one observer stopping the others, and
`ConcurrentModificationException` if observers unsubscribe while being notified (use `CopyOnWriteArrayList`).
</details>

**19. How does Command support undo?**

<details markdown="1"><summary>Answer</summary>

Each command stores what it needs to reverse itself (`undo()`); a history stack keeps executed commands,
undo pops and reverses, redo uses a second stack. See [Command](behavioral/command.md).
</details>

**20. What is double dispatch? How do sealed interfaces change Visitor?**

<details markdown="1"><summary>Answer</summary>

Visitor picks the method using two runtime types: the element (`accept`) and the visitor (`visitX`). With
sealed interfaces + pattern matching for `switch` you can write the operation as an exhaustive `switch`
without `accept` methods; the compiler tells you when a new type isn't handled. See [Visitor](behavioral/visitor.md).
</details>

**21. Why does an `Iterator` throw `ConcurrentModificationException`?**

<details markdown="1"><summary>Answer</summary>

`ArrayList`'s iterator is fail-fast: it compares the list's `modCount` with the value it saw at creation.
Modifying the list (except through `iterator.remove()`) changes `modCount`, and the next `next()` throws.
</details>

## Enterprise & microservices

**22. Dependency Injection vs Service Locator?**

<details markdown="1"><summary>Answer</summary>

DI pushes dependencies in (visible in the constructor, easy to test). Service Locator lets the class pull
them from a registry (hidden dependencies, global state). DI is preferred. See [Dependency Injection](enterprise/dependency-injection.md).
</details>

**23. Repository vs DAO?**

<details markdown="1"><summary>Answer</summary>

DAO is table/persistence oriented (`insert`, `update`). Repository is domain oriented and behaves like a
collection of aggregates (`findActiveCustomersIn(city)`). See [Repository](enterprise/repository.md).
</details>

**24. Why not use a distributed transaction (2PC) between microservices? What instead?**

<details markdown="1"><summary>Answer</summary>

2PC locks resources across services, needs all participants (and brokers) to support it, and the
coordinator is a single point of failure. Use a [Saga](microservice/saga.md): local transactions + compensations.
</details>

**25. What is the dual-write problem and how does the Outbox solve it?**

<details markdown="1"><summary>Answer</summary>

Writing to the DB and publishing to a broker are two operations; one can fail. The outbox writes the event
to a table in the same DB transaction, and a relay publishes it later → at-least-once delivery, so
consumers must be idempotent. See [Transactional Outbox](microservice/transactional-outbox.md).
</details>

**26. Explain the states of a circuit breaker.**

<details markdown="1"><summary>Answer</summary>

CLOSED (calls pass, failures counted) → OPEN after a threshold (calls rejected/fallback) → HALF_OPEN
after a wait (trial calls) → CLOSED on success or OPEN on failure. See [Circuit Breaker](microservice/circuit-breaker.md).
</details>

**27. Does CQRS require event sourcing or microservices?**

<details markdown="1"><summary>Answer</summary>

No. CQRS only means separate models for writes and reads. It's often combined with event sourcing and
separate stores, but can be two sets of classes over one database. See [CQRS](microservice/cqrs.md).
</details>

## Spring

**28. Why doesn't `@Transactional` work when a method calls another method of the same class?**

<details markdown="1"><summary>Answer</summary>

Transactions are applied by a proxy around the bean. `this.method()` calls the real object, not the proxy.
See the runnable example in [Patterns in Spring Boot](spring.md#proxy-self-invocation).
</details>

**29. Are singleton beans thread-safe?**

<details markdown="1"><summary>Answer</summary>

Not automatically. One instance serves all threads, so it must be stateless (or use thread-safe state).
"Singleton" refers to the number of instances, not to thread safety.
</details>

**30. What happens if you inject a prototype bean into a singleton?**

<details markdown="1"><summary>Answer</summary>

It is created once, when the singleton is created – so it effectively becomes a singleton too. Use
`ObjectProvider<T>`, `@Lookup` or a scoped proxy to get a new instance each time.
</details>

---

## Exercises

Each exercise extends the existing code. Add a test first, make it pass, run `./mvnw verify`.

| # | Pattern | Task |
|---|---|---|
| 1 | [Factory Method](creational/factory-method.md) | Add a `MercedesFactory` producing `C200` and `G63`. Did you have to change `CarFactory`? |
| 2 | [Builder](creational/builder.md) | Add a required `vin` (17 characters) to `Car.Builder` and validate it in `build()`. |
| 3 | [Decorator](structural/decorator.md) | Add `OatMilk` and a `Size` decorator that multiplies the price (S ×1, M ×1.2, L ×1.4). Does the order of decorators matter now? |
| 4 | [Adapter](structural/adapter.md) | Add a second legacy API that works with kuruş (`long`) instead of `BigDecimal` and adapt it. |
| 5 | [Proxy](structural/proxy.md) | Write a caching proxy for `DocumentService` that returns the same content for repeated reads. |
| 6 | [Strategy](behavioral/strategy.md) | Add "free shipping over 2000 TL" and a strategy that combines two strategies (the best one wins). |
| 7 | [State](behavioral/state.md) | Add a `RETURNED` state reachable only from `DELIVERED` within 14 days (use a `Clock`). |
| 8 | [Observer](behavioral/observer.md) | Make a `PriceAlert` fire only once and then unsubscribe itself. |
| 9 | [Chain of Responsibility](behavioral/chain-of-responsibility.md) | Add a `Cfo` approver between Director and rejection, and a rule that weekend requests always go to the Manager. |
| 10 | [Visitor](behavioral/visitor.md) | Add a `Hexagon` shape. Which classes did the compiler force you to change? |
| 11 | [Specification](enterprise/specification.md) | Add `nameContains(String)` and find "in-stock books with 'code' in the name". |
| 12 | [Circuit Breaker](microservice/circuit-breaker.md) | Count failures in a sliding window of the last 10 calls instead of consecutive failures. |
| 13 | [Saga](microservice/saga.md) | Add a "send invoice" step that can't be compensated. Where should it go in the list and why? |
| 14 | [Transactional Outbox](microservice/transactional-outbox.md) | Write an idempotent consumer that ignores events it has already processed (Inbox). |
| 15 | [Spring](spring.md) | Make `LoyaltyPointsListener` asynchronous with `@Async` (+ `@EnableAsync`) and prove in a test that it runs on another thread. |
