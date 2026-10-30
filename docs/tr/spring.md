# Spring Boot'ta Tasarım Kalıpları

> 🇬🇧 [English](../en/spring.md) · **Kod:** [`spring-boot-examples`](../../spring-boot-examples) · **Testler:** [`SpringPatternsTest`](../../spring-boot-examples/src/test/java/designpatterns/spring/SpringPatternsTest.java)

Spring bu repodaki kalıplardan inşa edilmiştir. Onları tanıdığınızda "Spring sihrinin" büyük kısmı sıradan
nesne yönelimli tasarıma dönüşür ve bilinen tuzakları (ör. `@Transactional`'ın sınıf içi çağrıda
çalışmaması) apaçık hale gelir.

[`spring-boot-examples`](../../spring-boot-examples) modülü ayrı bir Maven projesidir (Spring Boot 4.1, Java 25):

```bash
./mvnw -f spring-boot-examples verify            # derleme + testler
./mvnw -f spring-boot-examples spring-boot:run   # örnekleri yazdır
```

## Spring hangi kalıbı nerede kullanır?

| Kalıp | Spring'de | Bu modüldeki örnek |
|---|---|---|
| [Dependency Injection](enterprise/dependency-injection.md) | IoC container'ın kendisi; constructor injection | her sınıf |
| [Singleton](creational/singleton.md) | Varsayılan bean scope'u (container başına tek örnek, `getInstance()` yok) | [`ReportService`](../../spring-boot-examples/src/main/java/designpatterns/spring/scope/ReportService.java) |
| [Prototype](creational/prototype.md) | `@Scope("prototype")` – her istekte yeni örnek | [`ReportBuilder`](../../spring-boot-examples/src/main/java/designpatterns/spring/scope/ReportBuilder.java) |
| [Factory Method](creational/factory-method.md) / [Abstract Factory](creational/abstract-factory.md) | `BeanFactory`, `@Bean` metotları, `FactoryBean`, `ObjectProvider` | [`ReportService`](../../spring-boot-examples/src/main/java/designpatterns/spring/scope/ReportService.java) |
| [Strategy](behavioral/strategy.md) | Aynı arayüzün birden fazla bean'i; `PasswordEncoder`, `ResourceLoader` | [`CheckoutService`](../../spring-boot-examples/src/main/java/designpatterns/spring/strategy/CheckoutService.java) |
| [Observer](behavioral/observer.md) | `ApplicationEventPublisher` + `@EventListener` | [`OrderService`](../../spring-boot-examples/src/main/java/designpatterns/spring/observer/OrderService.java) |
| [Proxy](structural/proxy.md) | `@Transactional`, `@Cacheable`, `@Async`, `@PreAuthorize`, Spring Data repository'leri | [`ExchangeRateService`](../../spring-boot-examples/src/main/java/designpatterns/spring/proxy/ExchangeRateService.java) |
| [Decorator](structural/decorator.md) | `@Primary` sarmalayıcı bean'ler, `BeanPostProcessor`, `HttpServletRequestWrapper` | [`AuditingNotifier`](../../spring-boot-examples/src/main/java/designpatterns/spring/decorator/AuditingNotifier.java) |
| [Chain of Responsibility](behavioral/chain-of-responsibility.md) | Servlet filter'ları, `SecurityFilterChain`, `HandlerInterceptor`'lar, `@Order` | [`OrderValidationService`](../../spring-boot-examples/src/main/java/designpatterns/spring/chainofresponsibility/OrderValidationService.java) |
| [Template Method](behavioral/template-method.md) | `JdbcTemplate`, `RestTemplate`, `TransactionTemplate`, `AbstractController` | – |
| [Adapter](structural/adapter.md) | Spring MVC'deki `HandlerAdapter`, `MessageListenerAdapter` | – |
| [Facade](structural/facade.md) | `JdbcClient`, `RestClient` birçok alt seviye sınıfı gizler | – |
| [Repository](enterprise/repository.md) | Spring Data (`JpaRepository`, metot adından türetilen sorgular) | – |
| [Specification](enterprise/specification.md) | Spring Data JPA `Specification<T>` | – |
| [Circuit Breaker](microservice/circuit-breaker.md) | Resilience4j / Spring Cloud Circuit Breaker | – |

## Strategy: bütün gerçekleştirmeleri map olarak enjekte etmek

Spring her `PaymentMethod` bean'ini bean adıyla anahtarlanmış bir `Map`'te toplar. Yeni bir ödeme yöntemi
eklemek bir sınıf eklemektir – `switch` yok, `CheckoutService`'te değişiklik yok.

```java
@Component("wallet")
class WalletPayment implements PaymentMethod { ... }

@Service
public class CheckoutService {
    public CheckoutService(Map<String, PaymentMethod> methods) { ... }
    public String pay(String method, BigDecimal amount) { return methods.get(method).pay(amount); }
}
```

`List<PaymentMethod>` de çalışır (`@Order`'a göre sıralanır); aşağıdaki zincir bu şekilde kuruluyor.

## Observer: uygulama olayları

```java
publisher.publishEvent(new OrderPlacedEvent(orderId, email, amount));   // subject

@EventListener
void on(OrderPlacedEvent event) { ... }                                  // observer
```

`OrderService` e-postaları ya da sadakat puanlarını bilmez. Listener'lar varsayılan olarak **senkron ve aynı
transaction içinde** çalışır; asenkron için `@Async`, commit sonrası çalıştırmak için
`@TransactionalEventListener`, olayın kaybolmaması gerekiyorsa [Transactional Outbox](microservice/transactional-outbox.md)
(ör. Spring Modulith) kullanın.

## Proxy: `@Cacheable` ve sınıf içi çağrı tuzağı

Spring, gerçek metodu çağırmadan önce cache'e bakan, çalışma zamanında üretilmiş bir alt sınıf
(`ExchangeRateService$$SpringCGLIB$$0`) enjekte eder:

```text
injected class                        : ExchangeRateService$$SpringCGLIB$$0
rate("USD") x3 -> remote calls        : 1
self-invocation x2 -> remote calls    : 3 (cache bypassed!)
```

`toTry()`, `this.rate()`'i çağırır – `this` proxy değil gerçek nesnedir, bu yüzden annotation yok sayılır.
**Aynısı `@Transactional`, `@Async` ve `@PreAuthorize` için de geçerlidir.** Çözüm: metodu başka bir bean'e
taşımak ya da annotation'lı metodun dışarıdan çağrılacağı şekilde yeniden düzenlemek.

## Decorator: `@Primary` + `@Qualifier`

```java
@Primary
@Component
public class AuditingNotifier implements Notifier {
    public AuditingNotifier(@Qualifier("emailNotifier") Notifier delegate) { ... }
    public String send(String to, String msg) { auditLog.add(...); return delegate.send(to, msg) + " [audited]"; }
}
```

`Notifier` isteyen her istemci decorator'ı alır; gerçek e-posta notifier'ını sadece decorator alır.

## Chain of Responsibility: `List` + `@Order`

```java
@Component @Order(1) class CustomerValidator implements OrderValidator { ... }
@Component @Order(2) class QuantityValidator implements OrderValidator { ... }
@Component @Order(3) class CouponValidator   implements OrderValidator { ... }

public OrderValidationService(List<OrderValidator> chain)   // @Order sırasıyla enjekte edilir
```

Problem bulan ilk doğrulayıcı zinciri durdurur – servlet filter'ları ve Spring Security'nin filter zinciriyle
aynı fikir.

## Singleton, Prototype ve `ObjectProvider`

Bean'ler varsayılan olarak singleton'dır – container başına tek örnek, dolayısıyla **durumsuz** olmalıdırlar.
`ReportBuilder` gibi durum tutan bir yardımcı `@Scope("prototype")`'tır. Bir prototype'ı singleton'a doğrudan
enjekte etmek onu sadece bir kez oluşturur; bu yüzden `ReportService`, `ObjectProvider<ReportBuilder>`'ı bir
fabrika olarak kullanır ve her rapor için yeni bir builder alır.
