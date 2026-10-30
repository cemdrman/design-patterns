# Design Patterns in Java · Java ile Tasarım Kalıpları

All **23 Gang of Four** design patterns plus **9 enterprise & microservice patterns**, implemented with
**Java 25**, each with a runnable demo, JUnit tests and a detailed explanation in English and Turkish.

Gang of Four'un **23 tasarım kalıbının tamamı** ve **9 kurumsal / mikroservis kalıbı**; **Java 25** ile
yazılmış, her biri çalıştırılabilir örnek, JUnit testleri ve Türkçe / İngilizce detaylı anlatımla.

<div class="grid cards" markdown>

-   :flag_gb: **English**

    ---

    [Introduction](en/00-introduction.md) · [Refactoring to patterns](en/refactoring.md) ·
    [Anti-patterns](en/anti-patterns.md) · [Spring Boot](en/spring.md) ·
    [Interview questions](en/interview-questions.md)

-   :flag_tr: **Türkçe**

    ---

    [Giriş](tr/00-introduction.md) · [Kalıplara doğru refactoring](tr/refactoring.md) ·
    [Anti-pattern'ler](tr/anti-patterns.md) · [Spring Boot](tr/spring.md) ·
    [Mülakat soruları](tr/interview-questions.md)

</div>

| Creational / Oluşturucu | Structural / Yapısal | Behavioral / Davranışsal | Enterprise / Kurumsal | Microservice / Mikroservis |
|---|---|---|---|---|
| Singleton ([EN](en/creational/singleton.md) · [TR](tr/creational/singleton.md)) | Adapter ([EN](en/structural/adapter.md) · [TR](tr/structural/adapter.md)) | Chain of Responsibility ([EN](en/behavioral/chain-of-responsibility.md) · [TR](tr/behavioral/chain-of-responsibility.md)) | Dependency Injection ([EN](en/enterprise/dependency-injection.md) · [TR](tr/enterprise/dependency-injection.md)) | Circuit Breaker ([EN](en/microservice/circuit-breaker.md) · [TR](tr/microservice/circuit-breaker.md)) |
| Factory Method ([EN](en/creational/factory-method.md) · [TR](tr/creational/factory-method.md)) | Bridge ([EN](en/structural/bridge.md) · [TR](tr/structural/bridge.md)) | Command ([EN](en/behavioral/command.md) · [TR](tr/behavioral/command.md)) | Repository ([EN](en/enterprise/repository.md) · [TR](tr/enterprise/repository.md)) | Saga ([EN](en/microservice/saga.md) · [TR](tr/microservice/saga.md)) |
| Abstract Factory ([EN](en/creational/abstract-factory.md) · [TR](tr/creational/abstract-factory.md)) | Composite ([EN](en/structural/composite.md) · [TR](tr/structural/composite.md)) | Interpreter ([EN](en/behavioral/interpreter.md) · [TR](tr/behavioral/interpreter.md)) | Null Object ([EN](en/enterprise/null-object.md) · [TR](tr/enterprise/null-object.md)) | Transactional Outbox ([EN](en/microservice/transactional-outbox.md) · [TR](tr/microservice/transactional-outbox.md)) |
| Builder ([EN](en/creational/builder.md) · [TR](tr/creational/builder.md)) | Decorator ([EN](en/structural/decorator.md) · [TR](tr/structural/decorator.md)) | Iterator ([EN](en/behavioral/iterator.md) · [TR](tr/behavioral/iterator.md)) | Specification ([EN](en/enterprise/specification.md) · [TR](tr/enterprise/specification.md)) | CQRS ([EN](en/microservice/cqrs.md) · [TR](tr/microservice/cqrs.md)) |
| Prototype ([EN](en/creational/prototype.md) · [TR](tr/creational/prototype.md)) | Facade ([EN](en/structural/facade.md) · [TR](tr/structural/facade.md)) | Mediator ([EN](en/behavioral/mediator.md) · [TR](tr/behavioral/mediator.md)) | Object Pool ([EN](en/enterprise/object-pool.md) · [TR](tr/enterprise/object-pool.md)) | |
| | Flyweight ([EN](en/structural/flyweight.md) · [TR](tr/structural/flyweight.md)) | Memento ([EN](en/behavioral/memento.md) · [TR](tr/behavioral/memento.md)) | | |
| | Proxy ([EN](en/structural/proxy.md) · [TR](tr/structural/proxy.md)) | Observer ([EN](en/behavioral/observer.md) · [TR](tr/behavioral/observer.md)) | | |
| | | State ([EN](en/behavioral/state.md) · [TR](tr/behavioral/state.md)) | | |
| | | Strategy ([EN](en/behavioral/strategy.md) · [TR](tr/behavioral/strategy.md)) | | |
| | | Template Method ([EN](en/behavioral/template-method.md) · [TR](tr/behavioral/template-method.md)) | | |
| | | Visitor ([EN](en/behavioral/visitor.md) · [TR](tr/behavioral/visitor.md)) | | |

```bash
git clone https://github.com/cemdrman/design-patterns.git
cd design-patterns
./mvnw verify                 # build + tests (JDK 25+)
./mvnw -q compile exec:java   # run every demo
```

Source code: [github.com/cemdrman/design-patterns](https://github.com/cemdrman/design-patterns)
