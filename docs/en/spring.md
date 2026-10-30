# Design Patterns in Spring Boot

> 🇹🇷 [Türkçe](../tr/spring.md) · **Code:** [`spring-boot-examples`](../../spring-boot-examples) · **Tests:** [`SpringPatternsTest`](../../spring-boot-examples/src/test/java/designpatterns/spring/SpringPatternsTest.java)

Spring is built out of the patterns in this repository. Once you recognize them, a lot of "Spring magic"
becomes plain object-oriented design – and its well-known pitfalls (like `@Transactional` not working on
self-invocation) become obvious.

The [`spring-boot-examples`](../../spring-boot-examples) module is a separate Maven project (Spring Boot 4.1, Java 25):

```bash
./mvnw -f spring-boot-examples verify            # build + tests
./mvnw -f spring-boot-examples spring-boot:run   # print the demos
```

## Where Spring uses which pattern

| Pattern | In Spring | Example in this module |
|---|---|---|
| [Dependency Injection](enterprise/dependency-injection.md) | The whole IoC container; constructor injection | every class |
| [Singleton](creational/singleton.md) | Default bean scope (one instance per container, no `getInstance()`) | [`ReportService`](../../spring-boot-examples/src/main/java/designpatterns/spring/scope/ReportService.java) |
| [Prototype](creational/prototype.md) | `@Scope("prototype")` – a new instance per request | [`ReportBuilder`](../../spring-boot-examples/src/main/java/designpatterns/spring/scope/ReportBuilder.java) |
| [Factory Method](creational/factory-method.md) / [Abstract Factory](creational/abstract-factory.md) | `BeanFactory`, `@Bean` methods, `FactoryBean`, `ObjectProvider` | [`ReportService`](../../spring-boot-examples/src/main/java/designpatterns/spring/scope/ReportService.java) |
| [Strategy](behavioral/strategy.md) | Several beans of one interface; `PasswordEncoder`, `ResourceLoader` | [`CheckoutService`](../../spring-boot-examples/src/main/java/designpatterns/spring/strategy/CheckoutService.java) |
| [Observer](behavioral/observer.md) | `ApplicationEventPublisher` + `@EventListener` | [`OrderService`](../../spring-boot-examples/src/main/java/designpatterns/spring/observer/OrderService.java) |
| [Proxy](structural/proxy.md) | `@Transactional`, `@Cacheable`, `@Async`, `@PreAuthorize`, Spring Data repositories | [`ExchangeRateService`](../../spring-boot-examples/src/main/java/designpatterns/spring/proxy/ExchangeRateService.java) |
| [Decorator](structural/decorator.md) | `@Primary` wrapper beans, `BeanPostProcessor`, `HttpServletRequestWrapper` | [`AuditingNotifier`](../../spring-boot-examples/src/main/java/designpatterns/spring/decorator/AuditingNotifier.java) |
| [Chain of Responsibility](behavioral/chain-of-responsibility.md) | Servlet filters, `SecurityFilterChain`, `HandlerInterceptor`s, `@Order` | [`OrderValidationService`](../../spring-boot-examples/src/main/java/designpatterns/spring/chainofresponsibility/OrderValidationService.java) |
| [Template Method](behavioral/template-method.md) | `JdbcTemplate`, `RestTemplate`, `TransactionTemplate`, `AbstractController` | – |
| [Adapter](structural/adapter.md) | `HandlerAdapter` in Spring MVC, `MessageListenerAdapter` | – |
| [Facade](structural/facade.md) | `JdbcClient`, `RestClient` hide many low-level classes | – |
| [Repository](enterprise/repository.md) | Spring Data (`JpaRepository`, derived queries) | – |
| [Specification](enterprise/specification.md) | Spring Data JPA `Specification<T>` | – |
| [Circuit Breaker](microservice/circuit-breaker.md) | Resilience4j / Spring Cloud Circuit Breaker | – |

## Strategy: inject all implementations as a map

Every `PaymentMethod` bean is collected by Spring into a `Map` keyed by bean name. Adding a payment
method is adding a class – no `switch`, no change in `CheckoutService`.

```java
@Component("wallet")
class WalletPayment implements PaymentMethod { ... }

@Service
public class CheckoutService {
    public CheckoutService(Map<String, PaymentMethod> methods) { ... }
    public String pay(String method, BigDecimal amount) { return methods.get(method).pay(amount); }
}
```

`List<PaymentMethod>` works too (sorted by `@Order`), which is how the chain below is built.

## Observer: application events

```java
publisher.publishEvent(new OrderPlacedEvent(orderId, email, amount));   // subject

@EventListener
void on(OrderPlacedEvent event) { ... }                                  // observer
```

`OrderService` doesn't know about e-mails or loyalty points. Listeners run **synchronously in the same
transaction** by default; use `@Async` for asynchronous listeners, `@TransactionalEventListener` to run
after commit, and the [Transactional Outbox](microservice/transactional-outbox.md) (e.g. Spring Modulith) when
the event must not be lost.

## Proxy: `@Cacheable` and the self-invocation pitfall

Spring injects a generated subclass (`ExchangeRateService$$SpringCGLIB$$0`) that checks the cache
before calling the real method:

```text
injected class                        : ExchangeRateService$$SpringCGLIB$$0
rate("USD") x3 -> remote calls        : 1
self-invocation x2 -> remote calls    : 3 (cache bypassed!)
```

`toTry()` calls `this.rate()` – `this` is the real object, not the proxy, so the annotation is ignored.
**The same happens with `@Transactional`, `@Async` and `@PreAuthorize`.** Fixes: move the method to
another bean, or restructure so that the annotated method is called from outside.

## Decorator: `@Primary` + `@Qualifier`

```java
@Primary
@Component
public class AuditingNotifier implements Notifier {
    public AuditingNotifier(@Qualifier("emailNotifier") Notifier delegate) { ... }
    public String send(String to, String msg) { auditLog.add(...); return delegate.send(to, msg) + " [audited]"; }
}
```

Every client asking for a `Notifier` gets the decorator; only the decorator gets the real e-mail notifier.

## Chain of Responsibility: `List` + `@Order`

```java
@Component @Order(1) class CustomerValidator implements OrderValidator { ... }
@Component @Order(2) class QuantityValidator implements OrderValidator { ... }
@Component @Order(3) class CouponValidator   implements OrderValidator { ... }

public OrderValidationService(List<OrderValidator> chain)   // injected in @Order order
```

The first validator that finds a problem stops the chain – the same idea as servlet filters and Spring
Security's filter chain.

## Singleton, Prototype and `ObjectProvider`

Beans are singletons by default – one instance per container, so they must be **stateless**. A
stateful helper like `ReportBuilder` is `@Scope("prototype")`. Injecting a prototype into a singleton
directly would create it only once, so `ReportService` uses `ObjectProvider<ReportBuilder>` as a
factory and gets a fresh builder for each report.
