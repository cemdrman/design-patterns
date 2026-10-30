# Introduction to Design Patterns

> 🇹🇷 [Türkçe](../tr/00-introduction.md)

## What is a design pattern?

A design pattern is a **proven, reusable solution to a commonly occurring problem** in software
design. It is not a finished piece of code you can copy; it is a template that describes the
problem, the solution, and the consequences, which you adapt to your own code.

The term became popular with the book *Design Patterns: Elements of Reusable Object-Oriented
Software* (1994) by Erich Gamma, Richard Helm, Ralph Johnson and John Vlissides — the
**Gang of Four (GoF)**. The book describes **23 patterns** in three categories.

Patterns also give developers a **shared vocabulary**: "let's put a decorator around the
repository" explains a whole design in a few words.

## The three categories

| Category | Question it answers | Patterns |
|---|---|---|
| **Creational** | *How are objects created?* | [Singleton](creational/singleton.md), [Factory Method](creational/factory-method.md), [Abstract Factory](creational/abstract-factory.md), [Builder](creational/builder.md), [Prototype](creational/prototype.md) |
| **Structural** | *How are objects composed into bigger structures?* | [Adapter](structural/adapter.md), [Bridge](structural/bridge.md), [Composite](structural/composite.md), [Decorator](structural/decorator.md), [Facade](structural/facade.md), [Flyweight](structural/flyweight.md), [Proxy](structural/proxy.md) |
| **Behavioral** | *How do objects communicate and share responsibilities?* | [Chain of Responsibility](behavioral/chain-of-responsibility.md), [Command](behavioral/command.md), [Interpreter](behavioral/interpreter.md), [Iterator](behavioral/iterator.md), [Mediator](behavioral/mediator.md), [Memento](behavioral/memento.md), [Observer](behavioral/observer.md), [State](behavioral/state.md), [Strategy](behavioral/strategy.md), [Template Method](behavioral/template-method.md), [Visitor](behavioral/visitor.md) |

## Beyond the Gang of Four

The GoF book is from 1994. Since then other patterns became just as common in everyday Java work:

| Group | Patterns |
|---|---|
| **Enterprise** | [Dependency Injection](enterprise/dependency-injection.md), [Repository](enterprise/repository.md), [Null Object](enterprise/null-object.md), [Specification](enterprise/specification.md), [Object Pool](enterprise/object-pool.md) |
| **Microservice** | [Circuit Breaker](microservice/circuit-breaker.md), [Saga](microservice/saga.md), [Transactional Outbox](microservice/transactional-outbox.md), [CQRS](microservice/cqrs.md) |

## Principles behind the patterns

Almost every pattern applies a few basic principles:

- **Program to an interface, not an implementation.** Depend on `PaymentProcessor`, not on
  `LegacyBankApi`.
- **Favor composition over inheritance.** Decorator, Strategy, Bridge, State... all replace
  inheritance with "has-a" relationships.
- **Encapsulate what varies.** Find the part that changes and put it behind an abstraction.
- **SOLID**
  - **S**ingle Responsibility – a class should have one reason to change.
  - **O**pen/Closed – open for extension, closed for modification.
  - **L**iskov Substitution – subtypes must be usable wherever the base type is expected.
  - **I**nterface Segregation – many small interfaces are better than one big interface.
  - **D**ependency Inversion – depend on abstractions, not on concrete classes.

## Reading the diagrams

Every pattern page contains a [Mermaid](https://mermaid.js.org) class diagram, rendered directly
by GitHub.

| Notation | Meaning |
|---|---|
| `A <\|-- B` | B **extends** A (inheritance) |
| `A <\|.. B` | B **implements** A |
| `A --> B` | A **uses / references** B (association) |
| `A o--> B` | A **has** B (aggregation, B can live without A) |
| `A ..> B` | A **depends on / creates** B |
| `+` `-` `#` | public, private, protected |
| `method()*` | abstract method |
| `method()$` | static method |

## Which pattern should I use?

| If you need to... | Consider |
|---|---|
| have exactly one instance | Singleton (or let a DI container manage it) |
| let subclasses decide which object to create | Factory Method |
| create families of objects that must match | Abstract Factory |
| create a complex object step by step / avoid huge constructors | Builder |
| copy existing objects | Prototype |
| make an incompatible interface fit | Adapter |
| vary two dimensions independently | Bridge |
| treat trees and single objects the same way | Composite |
| add behaviour at runtime without subclassing | Decorator |
| give a simple entry point to a complex subsystem | Facade |
| share data between thousands of similar objects | Flyweight |
| control access, lazy load, cache or log transparently | Proxy |
| let several objects try to handle a request | Chain of Responsibility |
| undo/redo, queue or log operations | Command |
| evaluate a small language / rule set | Interpreter |
| traverse a collection without exposing its internals | Iterator |
| reduce chaotic dependencies between objects | Mediator |
| save and restore state (snapshots) | Memento |
| notify many objects when something changes | Observer |
| change behaviour depending on state, get rid of `switch (status)` | State |
| switch between algorithms at runtime | Strategy |
| reuse an algorithm skeleton and vary some steps | Template Method |
| add operations to a stable class hierarchy | Visitor |

## Commonly confused patterns

| Patterns | Difference |
|---|---|
| **Adapter / Decorator / Proxy / Facade** | Adapter *changes* the interface, Decorator *keeps* it and adds behaviour, Proxy *keeps* it and controls access, Facade *creates a new simpler* interface for many classes. |
| **Factory Method / Abstract Factory** | One product via inheritance vs. a family of products via composition. |
| **Strategy / State** | Same structure. Strategies are chosen by the client and are independent; states switch themselves and know about each other. |
| **Strategy / Template Method** | Composition (swap the whole algorithm) vs. inheritance (override some steps). |
| **Composite / Decorator** | Both are recursive. A composite has many children and sums them; a decorator has one child and enhances it. |
| **Mediator / Observer** | Mediator centralizes communication; Observer distributes it through subscriptions. They are often used together. |
| **Command / Strategy** | A command is *something to do* (often with undo); a strategy is *a way to do* something. |

## Warning: don't over-engineer

Patterns are tools, not goals. Adding a pattern where a simple `if` is enough makes the code harder
to read. Apply a pattern when you **feel the pain** it solves (duplicated conditionals, class
explosions, rigid dependencies), or when you are confident that the flexibility will be needed.
See [Refactoring to patterns](refactoring.md) for how patterns emerge from real code, and
[Anti-patterns](anti-patterns.md) for what overusing them looks like.

Many patterns are also simpler in modern Java: lambdas replace small Strategy/Command classes,
records make immutable value objects trivial, and sealed interfaces with pattern matching are an
alternative to Visitor. This repository shows both the classic form and the modern alternative where
it makes sense.
