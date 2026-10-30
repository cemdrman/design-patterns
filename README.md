# **Design Patterns in Java**

[![CI](https://github.com/cemdrman/design-patterns/actions/workflows/ci.yml/badge.svg)](https://github.com/cemdrman/design-patterns/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)

> 🇹🇷 Türkçe için: [README.tr.md](README.tr.md)

## **All 23 Gang of Four design patterns plus 9 enterprise & microservice patterns, explained in English and Turkish, implemented with Java 25.**

Every pattern comes with:

- a **runnable demo** based on a real-life scenario (cars, payments, orders, chat rooms...),
- **JUnit 5 tests** that show the expected behaviour,
- a **detailed explanation** in [English](docs/en/00-introduction.md) and [Turkish](docs/tr/00-introduction.md):
  intent, problem, solution, Mermaid class diagram, pros & cons, usages in the JDK / Spring,
  related patterns and, where useful, interview questions.

Code, class names and comments are in English; the explanations are available in both languages.

## Requirements

- **JDK 25** or newer ([Adoptium Temurin](https://adoptium.net), [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) etc.)
- You don't need to install Maven; the project ships with the Maven Wrapper (`mvnw`).

## Running

```bash
git clone https://github.com/cemdrman/design-patterns.git
cd design-patterns
```

Build and run all tests:

```bash
./mvnw verify
```

Run the demos of all 32 patterns one after another:

```bash
./mvnw -q compile exec:java
```

Run a single demo (use `mvnw.cmd` on Windows):

```bash
./mvnw -q compile exec:java -Dexec.mainClass=designpatterns.behavioral.strategy.StrategyDemo
```

You can also open the project **as a Maven project** in IntelliJ IDEA, Eclipse, VS Code or NetBeans
and run the `main` method of any `*Demo` class.

## Contents

Start with the introduction: [EN](docs/en/00-introduction.md) · [TR](docs/tr/00-introduction.md) —
categories, SOLID, how to read the diagrams, *"which pattern should I use?"* and commonly confused patterns.

### Creational

| # | Pattern | Example | Docs | Code |
|---|---|---|---|---|
| 1 | **Singleton** | Connection pool, app settings, ID generator | [EN](docs/en/creational/singleton.md) · [TR](docs/tr/creational/singleton.md) | [`singleton`](src/main/java/designpatterns/creational/singleton) |
| 2 | **Factory Method** | Audi / BMW car factories | [EN](docs/en/creational/factory-method.md) · [TR](docs/tr/creational/factory-method.md) | [`factorymethod`](src/main/java/designpatterns/creational/factorymethod) |
| 3 | **Abstract Factory** | Light / dark UI themes | [EN](docs/en/creational/abstract-factory.md) · [TR](docs/tr/creational/abstract-factory.md) | [`abstractfactory`](src/main/java/designpatterns/creational/abstractfactory) |
| 4 | **Builder** | Car order + car manual | [EN](docs/en/creational/builder.md) · [TR](docs/tr/creational/builder.md) | [`builder`](src/main/java/designpatterns/creational/builder) |
| 5 | **Prototype** | Person / company addresses | [EN](docs/en/creational/prototype.md) · [TR](docs/tr/creational/prototype.md) | [`prototype`](src/main/java/designpatterns/creational/prototype) |

### Structural

| # | Pattern | Example | Docs | Code |
|---|---|---|---|---|
| 6 | **Adapter** | Legacy bank payment API | [EN](docs/en/structural/adapter.md) · [TR](docs/tr/structural/adapter.md) | [`adapter`](src/main/java/designpatterns/structural/adapter) |
| 7 | **Bridge** | Notifications × e-mail / SMS | [EN](docs/en/structural/bridge.md) · [TR](docs/tr/structural/bridge.md) | [`bridge`](src/main/java/designpatterns/structural/bridge) |
| 8 | **Composite** | File system tree | [EN](docs/en/structural/composite.md) · [TR](docs/tr/structural/composite.md) | [`composite`](src/main/java/designpatterns/structural/composite) |
| 9 | **Decorator** | Coffee with add-ons | [EN](docs/en/structural/decorator.md) · [TR](docs/tr/structural/decorator.md) | [`decorator`](src/main/java/designpatterns/structural/decorator) |
| 10 | **Facade** | Order placement | [EN](docs/en/structural/facade.md) · [TR](docs/tr/structural/facade.md) | [`facade`](src/main/java/designpatterns/structural/facade) |
| 11 | **Flyweight** | 100 000 trees in a forest | [EN](docs/en/structural/flyweight.md) · [TR](docs/tr/structural/flyweight.md) | [`flyweight`](src/main/java/designpatterns/structural/flyweight) |
| 12 | **Proxy** | Lazy images, protected documents | [EN](docs/en/structural/proxy.md) · [TR](docs/tr/structural/proxy.md) | [`proxy`](src/main/java/designpatterns/structural/proxy) |

### Behavioral

| # | Pattern | Example | Docs | Code |
|---|---|---|---|---|
| 13 | **Chain of Responsibility** | Expense approval | [EN](docs/en/behavioral/chain-of-responsibility.md) · [TR](docs/tr/behavioral/chain-of-responsibility.md) | [`chainofresponsibility`](src/main/java/designpatterns/behavioral/chainofresponsibility) |
| 14 | **Command** | Text editor with undo/redo | [EN](docs/en/behavioral/command.md) · [TR](docs/tr/behavioral/command.md) | [`command`](src/main/java/designpatterns/behavioral/command) |
| 15 | **Interpreter** | Postfix arithmetic language | [EN](docs/en/behavioral/interpreter.md) · [TR](docs/tr/behavioral/interpreter.md) | [`interpreter`](src/main/java/designpatterns/behavioral/interpreter) |
| 16 | **Iterator** | Book shelf | [EN](docs/en/behavioral/iterator.md) · [TR](docs/tr/behavioral/iterator.md) | [`iterator`](src/main/java/designpatterns/behavioral/iterator) |
| 17 | **Mediator** | Chat room | [EN](docs/en/behavioral/mediator.md) · [TR](docs/tr/behavioral/mediator.md) | [`mediator`](src/main/java/designpatterns/behavioral/mediator) |
| 18 | **Memento** | Document versions | [EN](docs/en/behavioral/memento.md) · [TR](docs/tr/behavioral/memento.md) | [`memento`](src/main/java/designpatterns/behavioral/memento) |
| 19 | **Observer** | Stock market | [EN](docs/en/behavioral/observer.md) · [TR](docs/tr/behavioral/observer.md) | [`observer`](src/main/java/designpatterns/behavioral/observer) |
| 20 | **State** | Order lifecycle | [EN](docs/en/behavioral/state.md) · [TR](docs/tr/behavioral/state.md) | [`state`](src/main/java/designpatterns/behavioral/state) |
| 21 | **Strategy** | Shopping cart discounts | [EN](docs/en/behavioral/strategy.md) · [TR](docs/tr/behavioral/strategy.md) | [`strategy`](src/main/java/designpatterns/behavioral/strategy) |
| 22 | **Template Method** | CSV / JSON report export | [EN](docs/en/behavioral/template-method.md) · [TR](docs/tr/behavioral/template-method.md) | [`templatemethod`](src/main/java/designpatterns/behavioral/templatemethod) |
| 23 | **Visitor** | Shapes: area & SVG export | [EN](docs/en/behavioral/visitor.md) · [TR](docs/tr/behavioral/visitor.md) | [`visitor`](src/main/java/designpatterns/behavioral/visitor) |

### Beyond GoF: enterprise patterns

| # | Pattern | Example | Docs | Code |
|---|---|---|---|---|
| 24 | **Dependency Injection** | Welcome service + a tiny IoC container | [EN](docs/en/enterprise/dependency-injection.md) · [TR](docs/tr/enterprise/dependency-injection.md) | [`dependencyinjection`](src/main/java/designpatterns/enterprise/dependencyinjection) |
| 25 | **Repository** | Customer repository | [EN](docs/en/enterprise/repository.md) · [TR](docs/tr/enterprise/repository.md) | [`repository`](src/main/java/designpatterns/enterprise/repository) |
| 26 | **Null Object** | Guest customer instead of `null` | [EN](docs/en/enterprise/null-object.md) · [TR](docs/tr/enterprise/null-object.md) | [`nullobject`](src/main/java/designpatterns/enterprise/nullobject) |
| 27 | **Specification** | Combinable product filters | [EN](docs/en/enterprise/specification.md) · [TR](docs/tr/enterprise/specification.md) | [`specification`](src/main/java/designpatterns/enterprise/specification) |
| 28 | **Object Pool** | 50 virtual threads sharing 3 PDF renderers | [EN](docs/en/enterprise/object-pool.md) · [TR](docs/tr/enterprise/object-pool.md) | [`objectpool`](src/main/java/designpatterns/enterprise/objectpool) |

### Beyond GoF: microservice patterns

| # | Pattern | Example | Docs | Code |
|---|---|---|---|---|
| 29 | **Circuit Breaker** | Exchange rate service with fallback | [EN](docs/en/microservice/circuit-breaker.md) · [TR](docs/tr/microservice/circuit-breaker.md) | [`circuitbreaker`](src/main/java/designpatterns/microservice/circuitbreaker) |
| 30 | **Saga** | Order: stock → payment → shipping, with compensations | [EN](docs/en/microservice/saga.md) · [TR](docs/tr/microservice/saga.md) | [`saga`](src/main/java/designpatterns/microservice/saga) |
| 31 | **Transactional Outbox** | Order events that survive a broker outage | [EN](docs/en/microservice/transactional-outbox.md) · [TR](docs/tr/microservice/transactional-outbox.md) | [`outbox`](src/main/java/designpatterns/microservice/outbox) |
| 32 | **CQRS** | Product write model + read projection | [EN](docs/en/microservice/cqrs.md) · [TR](docs/tr/microservice/cqrs.md) | [`cqrs`](src/main/java/designpatterns/microservice/cqrs) |

## Project structure

```text
design-patterns
├── docs/
│   ├── en/                      English explanations
│   └── tr/                      Turkish explanations
├── src/main/java/designpatterns
│   ├── creational/              Singleton, Factory Method, Abstract Factory, Builder, Prototype
│   ├── structural/              Adapter, Bridge, Composite, Decorator, Facade, Flyweight, Proxy
│   ├── behavioral/              Chain of Responsibility, Command, Interpreter, Iterator, Mediator,
│   │                            Memento, Observer, State, Strategy, Template Method, Visitor
│   ├── enterprise/              Dependency Injection, Repository, Null Object, Specification, Object Pool
│   ├── microservice/            Circuit Breaker, Saga, Transactional Outbox, CQRS
│   ├── common/                  Console (demo output), ManualClock (time travel for demos/tests)
│   └── RunAllDemos.java         Runs every demo
├── src/test/java/designpatterns JUnit 5 tests, one class per pattern
├── pom.xml
└── mvnw / mvnw.cmd              Maven Wrapper
```

Each pattern lives in its own package: the participants of the pattern (one class per role) and a
`*Demo` class with a `main` method.

## Sample output

```text
============================================================
 Strategy
============================================================
subtotal                              : 1849.90

--- Same cart, different strategies ---
no discount                           : 1849.90
10% off                               : 1664.91
200 TL off over 1500 TL               : 1649.90
buy 3 pay 2                           : 1699.90
lambda: round down to 100             : 1800.00
```

## Modern Java features used

Records, sealed interfaces, pattern matching for `switch`, switch expressions, lambdas and functional
interfaces, virtual threads (`Executors.newVirtualThreadPerTaskExecutor`), `Math.clamp`,
`List.getFirst()` (sequenced collections)... The docs point out where these make a classic pattern
simpler (e.g. Strategy with lambdas, Visitor with sealed types).

## History

This repository started in 2016 with five creational patterns written in Turkish. In 2026 it was
rewritten from scratch: all 23 GoF patterns, Maven, Java 25, tests and bilingual documentation.
The original examples (car factory, car order builder, address prototype, connection pool singleton)
were kept and modernized.

### **Feel free to get in touch if you want to contribute.**
