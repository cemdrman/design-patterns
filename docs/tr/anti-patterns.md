# Anti-pattern'ler

> 🇬🇧 [English](../en/anti-patterns.md)

**Anti-pattern**, tekrar eden bir probleme verilen, iyi bir fikir gibi *görünen* ama çözdüğünden fazla
problem yaratan yaygın bir cevaptır. Onları bilmek kalıpları bilmek kadar faydalıdır: kod incelemelerinin
çoğu bunları yakalamakla ilgilidir.

| Anti-pattern | Tek cümleyle | Çıkış yolu |
|---|---|---|
| [God Object](#god-object) | Tek bir sınıf her şeyi bilir ve yapar | SRP, Facade, sınıf çıkarma |
| [Singleton istismarı](#singleton-abuse) | Kalıp adı takılmış global değişken durum | Dependency Injection |
| [Service Locator](#service-locator) | Sınıflar bağımlılıklarını bir kayıt defterinden kendileri alır | Dependency Injection |
| [Golden Hammer (pattern-itis)](#golden-hammer-pattern-itis) | Sevilen kalıbı her yerde kullanmak | YAGNI, KISS |
| [Anemic Domain Model](#anemic-domain-model) | Sadece getter/setter'ı olan entity'ler, mantık servislerde | Zengin domain modeli |
| [Primitive Obsession](#primitive-obsession) | Küçük tipler yerine String ve int'ler | Değer nesneleri / record'lar |
| [Boolean bayrak parametreleri](#boolean-flag-parameters) | `send(msg, true, false)` | Ayrı metotlar, Builder, enum'lar |
| [Exception yutmak](#exception-swallowing) | `catch (Exception e) {}` | Ele al, sar ya da tekrar fırlat |
| [Kopyala-yapıştır programlama](#copy-paste-programming) | Aynı mantık birçok yerde | Metot çıkarma, Template Method, Strategy |
| [Lava Flow](#lava-flow) | Kimsenin silmeye cesaret edemediği ölü kod | Testler + versiyon kontrolü + silmek |
| [Dağıtık monolit](#distributed-monolith) | Birlikte deploy edilmesi gereken mikroservisler | Net sınırlar, asenkron olaylar |
| [Paylaşılan veritabanı](#shared-database) | Birçok servisin aynı tablolara yazması | Servis başına veritabanı, Outbox, CQRS |

---

<a id="god-object"></a>
## God Object (Tanrı Nesne)

**Nedir:** 3 000 satırlık, 40 bağımlılığı olan, siparişler, ödemeler, e-postalar, PDF faturalar ve raporlama
için metotlar barındıran `OrderManager` gibi bir sınıf.

**Neden zararlı:** her değişiklik ona dokunur, merge conflict'ler bitmez, sistemin yarısını mock'lamadan
test edilemez ve kimse onu tamamen anlamaz.

**Çıkış yolu:** sorumluluğa göre bölün (Single Responsibility Principle). Birbiriyle uyumlu metot gruplarını
kendi sınıflarına çıkarın; çağıranların tek bir giriş noktasına ihtiyacı varsa önüne bir
[Facade](structural/facade.md) koyun.

<a id="singleton-abuse"></a>
## Singleton istismarı

**Nedir:** her yerden kullanılan `AppContext.getInstance().getUser()`, `Config.getInstance()`, `Cache.getInstance()`.

```java
public BigDecimal price(Product p) {
    BigDecimal rate = CurrencyService.getInstance().rate();   // gizli bağımlılık
    ...
}
```

**Neden zararlı:** değişebilir durumu olan bir singleton **global değişkendir**. Bağımlılıklar metot
gövdelerinde gizlenir, testler paylaşılan durum üzerinden birbirini etkiler ve sahte nesneyle değiştirmek zordur.

**Çıkış yolu:** "tek örnek" kalsın ama onu bir container yönetsin ve **enjekte** etsin
([Dependency Injection](enterprise/dependency-injection.md)). Durumsuz bir singleton (saf fonksiyonları olan bir
`enum`) sorun değildir. [Singleton](creational/singleton.md) sayfasına bakın.

<a id="service-locator"></a>
## Service Locator

**Nedir:** sınıflar iş birlikçilerini global bir kayıt defterinden ister.

```java
public class WelcomeService {
    public void welcome(String email) {
        Notifier notifier = ServiceLocator.get(Notifier.class);
        ...
    }
}
```

**Neden zararlı:** constructor yalan söyler – sınıfın neye ihtiyaç duyduğunu her metodu okumadan göremezsiniz;
eksik kayıtlar çalışma zamanında ortaya çıkar; testler global locator'ı ayarlamak zorundadır.

**Çıkış yolu:** constructor injection. Sınıfın ihtiyaç duyduğu her şey constructor'ında görünür.

<a id="golden-hammer-pattern-itis"></a>
## Golden Hammer (pattern-itis)

**Nedir:** "Abstract Factory'yi yeni öğrendim" → `UserFactoryFactoryProvider`. Kalıplar bir problem olduğu için
değil, havalı oldukları için uygulanır. ("Elinde çekiç olana her şey çivi görünür.")

**Neden zararlı:** bir `if`'in çözeceği iş için beş sınıf ve üç arayüz. Okunacak daha fazla kod, debug edilecek
daha fazla dolaylılık, sıfır fayda.

**Çıkış yolu:** **YAGNI** (You Aren't Gonna Need It – ihtiyacın olmayacak) ve **KISS**. Basit başlayın, bir kalıbı
çözdüğü acıyı hissettiğinizde ekleyin – [Kalıplara doğru refactoring](refactoring.md) sayfasına bakın.

<a id="anemic-domain-model"></a>
## Anemic Domain Model (Kansız Domain Modeli)

**Nedir:** entity'ler veri torbasıdır; bütün kurallar "servis" sınıflarındadır.

```java
order.setStatus(Status.SHIPPED);          // herhangi bir kod siparişi herhangi bir duruma sokabilir
order.setTotal(order.getTotal().add(x));  // değişmezler (invariant) korunmuyor
```

**Neden zararlı:** iş kuralları servislere dağılır ve tekrarlanır; nesne kendini koruyamadığı için geçersiz bir
durumda olabilir.

**Çıkış yolu:** davranışı verinin yanına koyun: `order.ship()`, `order.addLine(product, qty)`.
[State](behavioral/state.md) örneği zengin bir modeldir: `Order` kendi geçişlerini kendisi uygular.
(Basit CRUD uygulamalarında kansız model kabul edilebilir – aşırı mühendislik yapmayın.)

<a id="primitive-obsession"></a>
## Primitive Obsession (İlkel Tip Takıntısı)

**Nedir:** `String iban`, `String email`, `BigDecimal amount` artı `String currency`, yer değiştirebilen
`long userId` ve `long orderId`.

**Neden zararlı:** doğrulama her yerde tekrarlanır (ya da unutulur) ve argümanlar yanlış sırada olsa bile
`transfer(orderId, userId)` derlenir.

**Çıkış yolu:** küçük değer tipleri – Java **record**'ları bunu tek satıra indirir:

```java
public record Email(String value) {
    public Email {
        if (!value.contains("@")) throw new IllegalArgumentException("invalid e-mail");
    }
}
public record Money(BigDecimal amount, Currency currency) { }
```

<a id="boolean-flag-parameters"></a>
## Boolean bayrak parametreleri

**Nedir:** `report.export(sales, true, false, true)`.

**Neden zararlı:** çağrıldığı yerde okunamaz ve her bayrak metot içindeki kod yollarının sayısını ikiye katlar.

**Çıkış yolu:** ayrı metotlar (`exportWithHeader`), bir `enum` (`Format.CSV`), bir parametre nesnesi ya da bir
[Builder](creational/builder.md). Bayrak bir algoritma seçiyorsa o bir [Strategy](behavioral/strategy.md)'dir.

<a id="exception-swallowing"></a>
## Exception yutmak

**Nedir:**

```java
try {
    paymentClient.charge(order);
} catch (Exception e) {
    // TODO
}
```

**Neden zararlı:** müşteriden para çekilmez, sipariş kargolanır ve nedenini söyleyecek tek bir log satırı yoktur.
Bulunması en zor hatalar iz bırakmayanlardır.

**Çıkış yolu:** ele alın (retry, fallback – [Circuit Breaker](microservice/circuit-breaker.md)'a bakın), anlamlı bir
exception'a sarın ya da yukarı fırlatın. En azından bağlamıyla birlikte loglayın.

<a id="copy-paste-programming"></a>
## Kopyala-yapıştır programlama

**Nedir:** `CsvExporter` ve `JsonExporter`'da aynı 20 satır ya da beş controller'da aynı doğrulama.

**Neden zararlı:** bir hata düzeltmesi bir kopyaya uygulanır, diğerlerinde unutulur (*shotgun surgery*).

**Çıkış yolu:** bir metot ya da sınıf çıkarın; kopyalar birkaç adımda farklıysa
[Template Method](behavioral/template-method.md) ya da [Strategy](behavioral/strategy.md) kullanın.

<a id="lava-flow"></a>
## Lava Flow (Lav Akışı)

**Nedir:** yorum satırına alınmış kod, kullanılmayan sınıflar, `OldPaymentService2`, üç yıl önce açılmış feature
flag'ler – kimse hâlâ gerekli olup olmadığını bilmediği için kimse silmez.

**Neden zararlı:** okuyanın kafasını karıştırır, build'leri yavaşlatır ve bir gün biri ölü kodu "düzeltir".

**Çıkış yolu:** testler güven verir, versiyon kontrolü her şeyi hatırlar: silin. Public API'ler için
`@Deprecated(forRemoval = true)` kullanın ve planlı şekilde kaldırın.

<a id="distributed-monolith"></a>
## Dağıtık monolit (Distributed Monolith)

**Nedir:** sistem mikroservislere bölünmüş ama her özellik birlikte deploy edilmesi gereken beş servisi
değiştiriyor; servisler birbirini uzun zincirler halinde senkron çağırıyor.

**Neden zararlı:** dağıtık sistemin karmaşıklığını (ağ hataları, gecikme, kısmi deploy'lar) **ve** monolitin
bağımlılığını birlikte alırsınız.

**Çıkış yolu:** servis sınırlarını iş yetkinliklerine göre çizin (DDD bounded context), asenkron olayları tercih
edin, senkron çağrıları [Circuit Breaker](microservice/circuit-breaker.md) ile koruyun ve transaction'ları yerel
tutun ([Saga](microservice/saga.md)). Bazen dürüst cevap modüler bir monolittir.

<a id="shared-database"></a>
## Paylaşılan veritabanı (Shared Database)

**Nedir:** sipariş, ödeme ve kargo servislerinin hepsi aynı tabloları okuyup yazıyor.

**Neden zararlı:** bir servisteki şema değişikliği diğerlerini bozar; veritabanı gizli entegrasyon API'sine
dönüşür; servisler bağımsız ölçeklenemez ve deploy edilemez.

**Çıkış yolu:** **servis başına veritabanı**. Veriyi API'ler ve olaylarla paylaşın
([Transactional Outbox](microservice/transactional-outbox.md)); bir servis başka bir servisin verisine ihtiyaç
duyduğunda yerel okuma modelleri kurun ([CQRS](microservice/cqrs.md)).

---

İleri okuma: *AntiPatterns* (Brown ve ark., 1998), *Refactoring* (Martin Fowler), *Clean Code*
(Robert C. Martin), *Microservices Patterns* (Chris Richardson).
