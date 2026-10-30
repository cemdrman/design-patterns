# Mülakat Soruları ve Alıştırmalar

> 🇬🇧 [English](../en/interview-questions.md) · Diğer Java mülakat konuları: [cemdrman/interview-preparation](https://github.com/cemdrman/interview-preparation)

Java mülakatlarında tekrar tekrar sorulan sorular ve kısa cevapları. Önce kendiniz cevaplamayı deneyin,
sonra cevabı açın. Sondaki alıştırmalar bu repoda yapılacak küçük değişikliklerdir – bir kalıbı gerçekten
öğrenmenin en iyi yolu onu genişletmektir.

## Genel

**1. Tasarım kalıbı nedir? Bir kütüphane midir?**

<details markdown="1"><summary>Cevap</summary>

Tekrar eden bir tasarım problemine verilmiş, isimlendirilmiş ve kanıtlanmış bir çözüm şablonudur. Import
ettiğiniz bir kod değil, kendinize uyarladığınız bir yapıdır. Değeri ortak bir dil sağlamasıdır da:
"decorator ile sar" dört kelimeyle bir tasarımı anlatır.
</details>

**2. GoF'un üç kategorisi nedir?**

<details markdown="1"><summary>Cevap</summary>

Oluşturucu (nesneler nasıl oluşturulur), Yapısal (nesneler nasıl birleştirilir), Davranışsal (nesneler nasıl
iletişim kurar ve sorumluluğu paylaşır). [Giriş](00-introduction.md) sayfasına bakın.
</details>

**3. "Kalıtım yerine kompozisyonu tercih et" – hangi kalıplar bunu uygular?**

<details markdown="1"><summary>Cevap</summary>

Strategy, State, Decorator, Bridge, Composite, Observer, Chain of Responsibility... Kompozisyon çalışma
zamanında değiştirilebilir ve sizi bir sınıf hiyerarşisine kilitlemez; kalıtım derleme zamanında sabittir ve
üst sınıfın iç detaylarını açığa çıkarır (fragile base class).
</details>

**4. Ne zaman kalıp *kullanmamalısınız*?**

<details markdown="1"><summary>Cevap</summary>

Ortada henüz bir problem yokken. Kalıp sınıf ve dolaylılık ekler; basit bir `if` ya da lambda yetiyorsa onu
kullanın (YAGNI, KISS). [Anti-pattern'ler → Golden Hammer](anti-patterns.md#golden-hammer-pattern-itis) bölümüne bakın.
</details>

**5. JDK'da kullanılan kalıplara örnek verin.**

<details markdown="1"><summary>Cevap</summary>

Iterator (`Iterator`), Decorator (`BufferedInputStream`), Adapter (`Arrays.asList`, `InputStreamReader`),
Strategy (`Comparator`), Observer (`PropertyChangeListener`, `Flow`), Factory Method (`List.of`,
`Calendar.getInstance`), Builder (`StringBuilder`, `HttpRequest.newBuilder`), Proxy (`java.lang.reflect.Proxy`),
Flyweight (`Integer.valueOf` cache'i), Template Method (`AbstractList`), Command (`Runnable`), Singleton (`Runtime`).
</details>

## Oluşturucu kalıplar

**6. Thread-safe bir singleton nasıl yazılır? En iyi yol hangisi?**

<details markdown="1"><summary>Cevap</summary>

Eager statik alan, holder idiom (class loading sayesinde lazy + thread-safe), **`volatile`** alanla
double-checked locking ya da bir **`enum`**. `enum` en basitidir ve reflection ile serialization'a karşı
güvenlidir. Uygulamalarda container'ın yönettiği singleton'ı (Spring bean) tercih edin.
[Singleton](creational/singleton.md) sayfasına bakın.
</details>

**7. Double-checked locking neden `volatile` gerektirir?**

<details markdown="1"><summary>Cevap</summary>

`instance = new X()` atomik değildir: bellek ayır, constructor'ı çalıştır, referansı ata. `volatile` olmadan
bu adımlar yeniden sıralanabilir ve başka bir thread, constructor'ı bitmemiş bir nesnenin null olmayan
referansını görebilir. `volatile` happens-before garantisi verir.
</details>

**8. Bir singleton nasıl bozulabilir?**

<details markdown="1"><summary>Cevap</summary>

Reflection (private constructor'ı çağırmak), serialization (`readResolve` örneği döndürmezse deserialization
yeni nesne oluşturur), clone ve birden fazla class loader. `enum` singleton ilk ikisine karşı bağışıktır.
</details>

**9. Simple Factory, Factory Method ve Abstract Factory farkı nedir?**

<details markdown="1"><summary>Cevap</summary>

Simple factory: nesne oluşturan `switch`'li tek bir metot (GoF kalıbı değildir). Factory Method: hangi ürünün
oluşturulacağına alt sınıf karar verir (kalıtım). Abstract Factory: bir nesne birbiriyle ilişkili ürünlerden
oluşan bir **aile** oluşturur (kompozisyon), ör. açık/koyu tema bileşenleri.
</details>

**10. Builder ne zaman constructor'dan iyidir?**

<details markdown="1"><summary>Cevap</summary>

Çok sayıda, özellikle opsiyonel parametre varsa; okunabilir oluşturmayla değişmezlik istiyorsanız; doğrulama
birden fazla alanı ilgilendiriyorsa. 2-3 zorunlu parametre için constructor ya da statik factory daha nettir.
Record'lar + compact constructor birçok basit durumu karşılar.
</details>

**11. Sığ ve derin kopya farkı nedir? `Cloneable` neden bozuk kabul edilir?**

<details markdown="1"><summary>Cevap</summary>

Sığ kopya değişebilir alanları (listeler, tarihler) orijinalle paylaşır; derin kopya onları da kopyalar.
`Cloneable`'da `clone()` metodu yoktur, `Object.clone()` protected'dır, sığ kopya yapar, constructor'ları
atlar ve checked exception fırlatır. Copy constructor'ları ya da `copy()` metotlarını tercih edin.
[Prototype](creational/prototype.md) sayfasına bakın.
</details>

## Yapısal kalıplar

**12. Adapter, Decorator, Proxy ve Facade farkı nedir?**

<details markdown="1"><summary>Cevap</summary>

Adapter arayüzü **değiştirir**. Decorator arayüzü **korur** ve davranış ekler. Proxy arayüzü **korur** ve
erişimi kontrol eder (lazy, güvenlik, uzak nesne, cache). Facade birçok sınıfın üzerine **yeni ve daha basit**
bir arayüz koyar.
</details>

**13. JDK dynamic proxy ile CGLIB proxy arasındaki fark nedir?**

<details markdown="1"><summary>Cevap</summary>

JDK proxy'leri (`java.lang.reflect.Proxy`) sadece arayüzleri gerçekleştirir. CGLIB/ByteBuddy bir alt sınıf
üretir; arayüz olmadan çalışır ama `final` sınıf/metotları proxy'leyemez. Spring Boot varsayılan olarak
sınıf tabanlı proxy kullanır. İkisinde de sınıf içi çağrı problemi vardır.
</details>

**14. Flyweight'te intrinsic ve extrinsic durum nedir? JDK'dan örnek?**

<details markdown="1"><summary>Cevap</summary>

Intrinsic: paylaşılan, değişmez (ağaç tipi, doku). Extrinsic: dışarıdan verilen, nesneye özel bağlam (x, y).
JDK: `Integer.valueOf(-128..127)` cache'lenmiş örnekler döndürür – küçük sayılarda `==`'nün çalışıp büyüklerde
çalışmamasının sebebi budur.
</details>

**15. `java.io` neden iyi bir Decorator örneğidir?**

<details markdown="1"><summary>Cevap</summary>

`new BufferedReader(new InputStreamReader(new FileInputStream(f)))` – her sınıf başka birini saran ve tek bir
sorumluluk (buffer, karakter çözme...) ekleyen bir `Reader`/`InputStream`'dir. Dezavantajı: çok sayıda küçük sınıf.
</details>

## Davranışsal kalıplar

**16. Strategy ile State farkı nedir?**

<details markdown="1"><summary>Cevap</summary>

Yapıları aynıdır. Strategy'de algoritmayı **istemci** seçer ve stratejiler birbirini bilmez. State'te geçişlere
**durum nesneleri** karar verir ve bir sonraki durumu bilir.
</details>

**17. Strategy ile Template Method farkı nedir?**

<details markdown="1"><summary>Cevap</summary>

Template Method **adımları** kalıtımla değiştirir (derleme zamanında sabit). Strategy **bütün algoritmayı**
kompozisyonla değiştirir (çalışma zamanında değişebilir). Modern Java lambda'larla Strategy'ye yatkındır.
</details>

**18. Observer hangi problemlere yol açabilir?**

<details markdown="1"><summary>Cevap</summary>

Bellek sızıntıları (hiç abonelikten çıkmayan aboneler – *lapsed listener*), beklenmeyen bildirim sırası, yavaş
bir observer'ın subject'i bloklaması, bir observer'daki exception'ın diğerlerini durdurması ve bildirim
sırasında abonelikten çıkılırsa `ConcurrentModificationException` (`CopyOnWriteArrayList` kullanın).
</details>

**19. Command geri almayı (undo) nasıl destekler?**

<details markdown="1"><summary>Cevap</summary>

Her komut kendini geri almak için gerekeni saklar (`undo()`); bir geçmiş yığını çalıştırılan komutları tutar,
undo en üsttekini alıp geri alır, redo ikinci bir yığın kullanır. [Command](behavioral/command.md) sayfasına bakın.
</details>

**20. Double dispatch nedir? Sealed interface'ler Visitor'ı nasıl değiştirir?**

<details markdown="1"><summary>Cevap</summary>

Visitor metodu iki çalışma zamanı tipine göre seçer: eleman (`accept`) ve ziyaretçi (`visitX`). Sealed
interface + `switch` için pattern matching ile işlemi `accept` metotları olmadan, eksiksiz (exhaustive) bir
`switch` olarak yazabilirsiniz; yeni bir tip ele alınmadığında derleyici söyler. [Visitor](behavioral/visitor.md) sayfasına bakın.
</details>

**21. Bir `Iterator` neden `ConcurrentModificationException` fırlatır?**

<details markdown="1"><summary>Cevap</summary>

`ArrayList`'in iterator'ı fail-fast'tir: listenin `modCount` değerini oluşturulduğu andaki değerle karşılaştırır.
Listeyi (`iterator.remove()` dışında) değiştirmek `modCount`'u değiştirir ve sonraki `next()` exception fırlatır.
</details>

## Kurumsal ve mikroservis

**22. Dependency Injection ile Service Locator farkı nedir?**

<details markdown="1"><summary>Cevap</summary>

DI bağımlılıkları içeri verir (constructor'da görünür, test etmesi kolay). Service Locator sınıfın onları bir
kayıt defterinden çekmesine izin verir (gizli bağımlılık, global durum). DI tercih edilir.
[Dependency Injection](enterprise/dependency-injection.md) sayfasına bakın.
</details>

**23. Repository ile DAO farkı nedir?**

<details markdown="1"><summary>Cevap</summary>

DAO tablo/kalıcılık odaklıdır (`insert`, `update`). Repository domain odaklıdır ve aggregate'lerden oluşan
bir koleksiyon gibi davranır (`findActiveCustomersIn(city)`). [Repository](enterprise/repository.md) sayfasına bakın.
</details>

**24. Mikroservisler arasında neden dağıtık transaction (2PC) kullanılmaz? Yerine ne kullanılır?**

<details markdown="1"><summary>Cevap</summary>

2PC servisler arasında kaynakları kilitler, tüm katılımcıların (ve broker'ların) desteklemesini gerektirir ve
koordinatör tek hata noktasıdır. [Saga](microservice/saga.md) kullanın: yerel transaction'lar + telafiler.
</details>

**25. Çift yazma (dual write) problemi nedir, Outbox bunu nasıl çözer?**

<details markdown="1"><summary>Cevap</summary>

Veritabanına yazmak ve broker'a yayınlamak iki ayrı işlemdir; biri başarısız olabilir. Outbox olayı aynı DB
transaction'ında bir tabloya yazar ve bir relay onu sonra yayınlar → en az bir kez teslimat, dolayısıyla
tüketiciler idempotent olmalı. [Transactional Outbox](microservice/transactional-outbox.md) sayfasına bakın.
</details>

**26. Bir circuit breaker'ın durumlarını açıklayın.**

<details markdown="1"><summary>Cevap</summary>

CLOSED (çağrılar geçer, hatalar sayılır) → eşikten sonra OPEN (çağrılar reddedilir/fallback) → beklemeden
sonra HALF_OPEN (deneme çağrıları) → başarıda CLOSED, hatada tekrar OPEN.
[Circuit Breaker](microservice/circuit-breaker.md) sayfasına bakın.
</details>

**27. CQRS, event sourcing ya da mikroservis gerektirir mi?**

<details markdown="1"><summary>Cevap</summary>

Hayır. CQRS sadece yazma ve okuma için ayrı modeller demektir. Sıklıkla event sourcing ve ayrı depolarla
birleştirilir ama tek bir veritabanı üzerinde iki ayrı sınıf grubu da olabilir. [CQRS](microservice/cqrs.md) sayfasına bakın.
</details>

## Spring

**28. Bir metot aynı sınıftaki başka bir metodu çağırınca `@Transactional` neden çalışmaz?**

<details markdown="1"><summary>Cevap</summary>

Transaction'ı bean'in etrafındaki bir proxy uygular. `this.method()` proxy'yi değil gerçek nesneyi çağırır.
Çalıştırılabilir örnek: [Spring Boot'ta kalıplar](spring.md#proxy-self-invocation).
</details>

**29. Singleton bean'ler thread-safe midir?**

<details markdown="1"><summary>Cevap</summary>

Otomatik olarak değil. Tek bir örnek bütün thread'lere hizmet eder; bu yüzden durumsuz olmalı (ya da
thread-safe durum kullanmalı). "Singleton" örnek sayısını anlatır, thread güvenliğini değil.
</details>

**30. Bir prototype bean'i singleton'a enjekte ederseniz ne olur?**

<details markdown="1"><summary>Cevap</summary>

Singleton oluşturulurken bir kez oluşturulur – yani fiilen o da singleton olur. Her seferinde yeni örnek almak
için `ObjectProvider<T>`, `@Lookup` ya da scoped proxy kullanın.
</details>

---

## Alıştırmalar

Her alıştırma mevcut kodu genişletir. Önce bir test yazın, geçirin, `./mvnw verify` çalıştırın.

| # | Kalıp | Görev |
|---|---|---|
| 1 | [Factory Method](creational/factory-method.md) | `C200` ve `G63` üreten bir `MercedesFactory` ekleyin. `CarFactory`'yi değiştirmeniz gerekti mi? |
| 2 | [Builder](creational/builder.md) | `Car.Builder`'a zorunlu bir `vin` (17 karakter) ekleyin ve `build()` içinde doğrulayın. |
| 3 | [Decorator](structural/decorator.md) | `OatMilk` ve fiyatı çarpan bir `Size` decorator'ı ekleyin (S ×1, M ×1.2, L ×1.4). Decorator'ların sırası artık önemli mi? |
| 4 | [Adapter](structural/adapter.md) | `BigDecimal` yerine kuruş (`long`) ile çalışan ikinci bir eski API ekleyin ve onu da uyarlayın. |
| 5 | [Proxy](structural/proxy.md) | `DocumentService` için tekrar eden okumalarda aynı içeriği döndüren bir cache proxy'si yazın. |
| 6 | [Strategy](behavioral/strategy.md) | "2000 TL üzeri ücretsiz kargo" ve iki stratejiyi birleştiren (en iyisi kazanır) bir strateji ekleyin. |
| 7 | [State](behavioral/state.md) | Sadece `DELIVERED`'dan 14 gün içinde ulaşılabilen bir `RETURNED` durumu ekleyin (`Clock` kullanın). |
| 8 | [Observer](behavioral/observer.md) | `PriceAlert`'in sadece bir kez tetiklenip sonra kendi aboneliğini iptal etmesini sağlayın. |
| 9 | [Chain of Responsibility](behavioral/chain-of-responsibility.md) | Director ile ret arasına bir `Cfo` ekleyin; hafta sonu gelen istekler her zaman Manager'a gitsin. |
| 10 | [Visitor](behavioral/visitor.md) | Bir `Hexagon` şekli ekleyin. Derleyici hangi sınıfları değiştirmeye zorladı? |
| 11 | [Specification](enterprise/specification.md) | `nameContains(String)` ekleyin ve "adında 'code' geçen, stokta olan kitapları" bulun. |
| 12 | [Circuit Breaker](microservice/circuit-breaker.md) | Art arda hatalar yerine son 10 çağrılık kayan pencerede (sliding window) hata sayın. |
| 13 | [Saga](microservice/saga.md) | Telafi edilemeyen bir "fatura gönder" adımı ekleyin. Listede nereye konmalı, neden? |
| 14 | [Transactional Outbox](microservice/transactional-outbox.md) | Daha önce işlediği olayları yok sayan idempotent bir tüketici (Inbox) yazın. |
| 15 | [Spring](spring.md) | `LoyaltyPointsListener`'ı `@Async` (+ `@EnableAsync`) ile asenkron yapın ve başka bir thread'de çalıştığını testle kanıtlayın. |
