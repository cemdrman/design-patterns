# **Java ile Tasarım Kalıpları**

[![CI](https://github.com/cemdrman/design-patterns/actions/workflows/ci.yml/badge.svg)](https://github.com/cemdrman/design-patterns/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)

> 🇬🇧 For English: [README.md](README.md)

## **Gang of Four'un 23 tasarım kalıbının tamamı ve 9 kurumsal / mikroservis kalıbı; Türkçe ve İngilizce anlatımlarla, Java 25 ile.**

Bu proje Java için Türkçe kaynak oluşturmak amacıyla oluşturulmuştur. Her kalıp için:

- gerçek hayattan bir senaryoya dayanan **çalıştırılabilir bir örnek** (arabalar, ödemeler, siparişler, sohbet odaları...),
- beklenen davranışı gösteren **JUnit 5 testleri**,
- [Türkçe](docs/tr/00-introduction.md) ve [İngilizce](docs/en/00-introduction.md) **detaylı anlatım**:
  amaç, problem, çözüm, Mermaid sınıf diyagramı, artılar/eksiler, JDK / Spring'deki kullanımlar,
  ilişkili kalıplar ve gerektiğinde mülakat soruları

bulunur. Kodlar, sınıf isimleri ve kod içi açıklamalar İngilizcedir; anlatımlar iki dilde mevcuttur.

## Gereksinimler

- **JDK 25** veya üzeri ([Adoptium Temurin](https://adoptium.net), [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) vb.)
- Maven kurmanıza gerek yoktur; proje Maven Wrapper (`mvnw`) ile gelir.

## Çalıştırma

```bash
git clone https://github.com/cemdrman/design-patterns.git
cd design-patterns
```

Derleme ve testler:

```bash
./mvnw verify
```

32 kalıbın örneklerini art arda çalıştırmak:

```bash
./mvnw -q compile exec:java
```

Tek bir örneği çalıştırmak (Windows'ta `mvnw.cmd`):

```bash
./mvnw -q compile exec:java -Dexec.mainClass=designpatterns.behavioral.strategy.StrategyDemo
```

Örnekleri IntelliJ IDEA, Eclipse, VS Code veya NetBeans'te projeyi **Maven projesi olarak açıp**
ilgili `*Demo` sınıfının `main` metodunu çalıştırarak da deneyebilirsiniz.

## İçindekiler

Önce giriş sayfasıyla başlayın: [TR](docs/tr/00-introduction.md) · [EN](docs/en/00-introduction.md) —
kategoriler, SOLID, diyagramların okunması, *"hangi kalıbı kullanmalıyım?"* ve sık karıştırılan kalıplar.

| Rehber | |
|---|---|
| **Anti-pattern'ler** | God Object, Singleton istismarı, Service Locator, dağıtık monolit... ve çıkış yolları – [TR](docs/tr/anti-patterns.md) · [EN](docs/en/anti-patterns.md) |
| **Kalıplara doğru refactoring** | 11 "önce / sonra" örneği: kötü kokan kod, onu düzelten kalıp ve ikisinin aynı davrandığını kanıtlayan testler – [TR](docs/tr/refactoring.md) · [EN](docs/en/refactoring.md) |

### Oluşturucu (Creational)

| # | Kalıp | Örnek | Anlatım | Kod |
|---|---|---|---|---|
| 1 | **Singleton** (Tekil Nesne) | Bağlantı havuzu, uygulama ayarları, ID üretici | [TR](docs/tr/creational/singleton.md) · [EN](docs/en/creational/singleton.md) | [`singleton`](src/main/java/designpatterns/creational/singleton) |
| 2 | **Factory Method** (Fabrika Metodu) | Audi / BMW araba fabrikaları | [TR](docs/tr/creational/factory-method.md) · [EN](docs/en/creational/factory-method.md) | [`factorymethod`](src/main/java/designpatterns/creational/factorymethod) |
| 3 | **Abstract Factory** (Soyut Fabrika) | Açık / koyu arayüz temaları | [TR](docs/tr/creational/abstract-factory.md) · [EN](docs/en/creational/abstract-factory.md) | [`abstractfactory`](src/main/java/designpatterns/creational/abstractfactory) |
| 4 | **Builder** (İnşa Edici) | Araba siparişi + kullanım kılavuzu | [TR](docs/tr/creational/builder.md) · [EN](docs/en/creational/builder.md) | [`builder`](src/main/java/designpatterns/creational/builder) |
| 5 | **Prototype** (Prototip) | Şahıs / firma adresleri | [TR](docs/tr/creational/prototype.md) · [EN](docs/en/creational/prototype.md) | [`prototype`](src/main/java/designpatterns/creational/prototype) |

### Yapısal (Structural)

| # | Kalıp | Örnek | Anlatım | Kod |
|---|---|---|---|---|
| 6 | **Adapter** (Adaptör) | Eski banka ödeme API'si | [TR](docs/tr/structural/adapter.md) · [EN](docs/en/structural/adapter.md) | [`adapter`](src/main/java/designpatterns/structural/adapter) |
| 7 | **Bridge** (Köprü) | Bildirimler × e-posta / SMS | [TR](docs/tr/structural/bridge.md) · [EN](docs/en/structural/bridge.md) | [`bridge`](src/main/java/designpatterns/structural/bridge) |
| 8 | **Composite** (Bileşik) | Dosya sistemi ağacı | [TR](docs/tr/structural/composite.md) · [EN](docs/en/structural/composite.md) | [`composite`](src/main/java/designpatterns/structural/composite) |
| 9 | **Decorator** (Dekoratör) | Eklentili kahve | [TR](docs/tr/structural/decorator.md) · [EN](docs/en/structural/decorator.md) | [`decorator`](src/main/java/designpatterns/structural/decorator) |
| 10 | **Facade** (Cephe) | Sipariş verme | [TR](docs/tr/structural/facade.md) · [EN](docs/en/structural/facade.md) | [`facade`](src/main/java/designpatterns/structural/facade) |
| 11 | **Flyweight** (Sinek Siklet) | Ormanda 100 000 ağaç | [TR](docs/tr/structural/flyweight.md) · [EN](docs/en/structural/flyweight.md) | [`flyweight`](src/main/java/designpatterns/structural/flyweight) |
| 12 | **Proxy** (Vekil) | Lazy resimler, korumalı dokümanlar | [TR](docs/tr/structural/proxy.md) · [EN](docs/en/structural/proxy.md) | [`proxy`](src/main/java/designpatterns/structural/proxy) |

### Davranışsal (Behavioral)

| # | Kalıp | Örnek | Anlatım | Kod |
|---|---|---|---|---|
| 13 | **Chain of Responsibility** (Sorumluluk Zinciri) | Masraf onayı | [TR](docs/tr/behavioral/chain-of-responsibility.md) · [EN](docs/en/behavioral/chain-of-responsibility.md) | [`chainofresponsibility`](src/main/java/designpatterns/behavioral/chainofresponsibility) |
| 14 | **Command** (Komut) | Undo/redo destekli metin editörü | [TR](docs/tr/behavioral/command.md) · [EN](docs/en/behavioral/command.md) | [`command`](src/main/java/designpatterns/behavioral/command) |
| 15 | **Interpreter** (Yorumlayıcı) | Postfix aritmetik dili | [TR](docs/tr/behavioral/interpreter.md) · [EN](docs/en/behavioral/interpreter.md) | [`interpreter`](src/main/java/designpatterns/behavioral/interpreter) |
| 16 | **Iterator** (Yineleyici) | Kitap rafı | [TR](docs/tr/behavioral/iterator.md) · [EN](docs/en/behavioral/iterator.md) | [`iterator`](src/main/java/designpatterns/behavioral/iterator) |
| 17 | **Mediator** (Arabulucu) | Sohbet odası | [TR](docs/tr/behavioral/mediator.md) · [EN](docs/en/behavioral/mediator.md) | [`mediator`](src/main/java/designpatterns/behavioral/mediator) |
| 18 | **Memento** (Hatıra) | Doküman sürümleri | [TR](docs/tr/behavioral/memento.md) · [EN](docs/en/behavioral/memento.md) | [`memento`](src/main/java/designpatterns/behavioral/memento) |
| 19 | **Observer** (Gözlemci) | Borsa | [TR](docs/tr/behavioral/observer.md) · [EN](docs/en/behavioral/observer.md) | [`observer`](src/main/java/designpatterns/behavioral/observer) |
| 20 | **State** (Durum) | Sipariş yaşam döngüsü | [TR](docs/tr/behavioral/state.md) · [EN](docs/en/behavioral/state.md) | [`state`](src/main/java/designpatterns/behavioral/state) |
| 21 | **Strategy** (Strateji) | Sepet indirimleri | [TR](docs/tr/behavioral/strategy.md) · [EN](docs/en/behavioral/strategy.md) | [`strategy`](src/main/java/designpatterns/behavioral/strategy) |
| 22 | **Template Method** (Şablon Metot) | CSV / JSON rapor çıktısı | [TR](docs/tr/behavioral/template-method.md) · [EN](docs/en/behavioral/template-method.md) | [`templatemethod`](src/main/java/designpatterns/behavioral/templatemethod) |
| 23 | **Visitor** (Ziyaretçi) | Şekiller: alan ve SVG çıktısı | [TR](docs/tr/behavioral/visitor.md) · [EN](docs/en/behavioral/visitor.md) | [`visitor`](src/main/java/designpatterns/behavioral/visitor) |

### GoF'un ötesi: kurumsal kalıplar

| # | Kalıp | Örnek | Anlatım | Kod |
|---|---|---|---|---|
| 24 | **Dependency Injection** (Bağımlılık Enjeksiyonu) | Karşılama servisi + küçük bir IoC container | [TR](docs/tr/enterprise/dependency-injection.md) · [EN](docs/en/enterprise/dependency-injection.md) | [`dependencyinjection`](src/main/java/designpatterns/enterprise/dependencyinjection) |
| 25 | **Repository** (Depo) | Müşteri repository'si | [TR](docs/tr/enterprise/repository.md) · [EN](docs/en/enterprise/repository.md) | [`repository`](src/main/java/designpatterns/enterprise/repository) |
| 26 | **Null Object** (Boş Nesne) | `null` yerine misafir müşteri | [TR](docs/tr/enterprise/null-object.md) · [EN](docs/en/enterprise/null-object.md) | [`nullobject`](src/main/java/designpatterns/enterprise/nullobject) |
| 27 | **Specification** (Şartname) | Birleştirilebilir ürün filtreleri | [TR](docs/tr/enterprise/specification.md) · [EN](docs/en/enterprise/specification.md) | [`specification`](src/main/java/designpatterns/enterprise/specification) |
| 28 | **Object Pool** (Nesne Havuzu) | 3 PDF renderer'ı paylaşan 50 virtual thread | [TR](docs/tr/enterprise/object-pool.md) · [EN](docs/en/enterprise/object-pool.md) | [`objectpool`](src/main/java/designpatterns/enterprise/objectpool) |

### GoF'un ötesi: mikroservis kalıpları

| # | Kalıp | Örnek | Anlatım | Kod |
|---|---|---|---|---|
| 29 | **Circuit Breaker** (Devre Kesici) | Fallback'li döviz kuru servisi | [TR](docs/tr/microservice/circuit-breaker.md) · [EN](docs/en/microservice/circuit-breaker.md) | [`circuitbreaker`](src/main/java/designpatterns/microservice/circuitbreaker) |
| 30 | **Saga** | Sipariş: stok → ödeme → kargo, telafilerle | [TR](docs/tr/microservice/saga.md) · [EN](docs/en/microservice/saga.md) | [`saga`](src/main/java/designpatterns/microservice/saga) |
| 31 | **Transactional Outbox** | Broker çökse de kaybolmayan sipariş olayları | [TR](docs/tr/microservice/transactional-outbox.md) · [EN](docs/en/microservice/transactional-outbox.md) | [`outbox`](src/main/java/designpatterns/microservice/outbox) |
| 32 | **CQRS** | Ürün yazma modeli + okuma projeksiyonu | [TR](docs/tr/microservice/cqrs.md) · [EN](docs/en/microservice/cqrs.md) | [`cqrs`](src/main/java/designpatterns/microservice/cqrs) |

## Proje yapısı

```text
design-patterns
├── docs/
│   ├── en/                      İngilizce anlatımlar
│   └── tr/                      Türkçe anlatımlar
├── src/main/java/designpatterns
│   ├── creational/              Singleton, Factory Method, Abstract Factory, Builder, Prototype
│   ├── structural/              Adapter, Bridge, Composite, Decorator, Facade, Flyweight, Proxy
│   ├── behavioral/              Chain of Responsibility, Command, Interpreter, Iterator, Mediator,
│   │                            Memento, Observer, State, Strategy, Template Method, Visitor
│   ├── enterprise/              Dependency Injection, Repository, Null Object, Specification, Object Pool
│   ├── microservice/            Circuit Breaker, Saga, Transactional Outbox, CQRS
│   ├── refactoring/             Refactoring rehberindeki "önce" kodları
│   ├── common/                  Console (örnek çıktıları), ManualClock (örnek/testlerde zaman yolculuğu)
│   └── RunAllDemos.java         Bütün örnekleri çalıştırır
├── src/test/java/designpatterns Her kalıp için bir JUnit 5 test sınıfı
├── pom.xml
└── mvnw / mvnw.cmd              Maven Wrapper
```

Her kalıp kendi paketindedir: kalıbın katılımcıları (her rol için bir sınıf) ve `main` metodu olan
bir `*Demo` sınıfı.

## Örnek çıktı

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

## Kullanılan modern Java özellikleri

Record'lar, sealed interface'ler, `switch` için pattern matching, switch ifadeleri, lambda'lar ve
functional interface'ler, virtual thread'ler (`Executors.newVirtualThreadPerTaskExecutor`),
`Math.clamp`, `List.getFirst()` (sequenced collections)... Anlatımlarda bu özelliklerin klasik bir
kalıbı nerede sadeleştirdiği (örneğin lambda'larla Strategy, sealed tiplerle Visitor) belirtilmiştir.

## Tarihçe

Bu repo 2016'da Türkçe yazılmış beş oluşturucu kalıpla başladı. 2026'da baştan yazıldı: 23 GoF
kalıbının tamamı, Maven, Java 25, testler ve iki dilli dokümantasyon. İlk örnekler (araba fabrikası,
araba siparişi builder'ı, adres prototipi, bağlantı havuzu singleton'ı) korunarak modernleştirildi.

### **Projeye katkı sağlamak isteyenler iletişime geçebilir.**
