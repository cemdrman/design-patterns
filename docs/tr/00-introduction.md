# Tasarım Kalıplarına Giriş

> 🇬🇧 [English](../en/00-introduction.md)

## Tasarım kalıbı nedir?

Tasarım kalıbı (design pattern; *tasarım deseni* veya *tasarım şablonu* olarak da geçer), yazılım
tasarımında **sık karşılaşılan bir probleme, kendini kanıtlamış ve yeniden kullanılabilir bir
çözümdür**. Kopyalayıp yapıştırılacak hazır bir kod değildir; problemi, çözümü ve sonuçlarını
anlatan ve kendi kodunuza uyarladığınız bir şablondur.

Kavram; Erich Gamma, Richard Helm, Ralph Johnson ve John Vlissides'in 1994'te yayımladığı
*Design Patterns: Elements of Reusable Object-Oriented Software* kitabıyla yaygınlaştı. Yazarlara
**Gang of Four (GoF – Dörtlü Çete)** denir. Kitap **23 kalıbı** üç kategoride anlatır.

Kalıplar aynı zamanda geliştiricilere **ortak bir dil** kazandırır: "repository'nin etrafına bir
decorator koyalım" cümlesi bütün bir tasarımı birkaç kelimeyle anlatır.

## Üç kategori

| Kategori | Cevapladığı soru | Kalıplar |
|---|---|---|
| **Oluşturucu (Creational)** | *Nesneler nasıl oluşturulur?* | [Singleton](creational/singleton.md), [Factory Method](creational/factory-method.md), [Abstract Factory](creational/abstract-factory.md), [Builder](creational/builder.md), [Prototype](creational/prototype.md) |
| **Yapısal (Structural)** | *Nesneler daha büyük yapılar halinde nasıl birleştirilir?* | [Adapter](structural/adapter.md), [Bridge](structural/bridge.md), [Composite](structural/composite.md), [Decorator](structural/decorator.md), [Facade](structural/facade.md), [Flyweight](structural/flyweight.md), [Proxy](structural/proxy.md) |
| **Davranışsal (Behavioral)** | *Nesneler nasıl iletişim kurar ve sorumlulukları nasıl paylaşır?* | [Chain of Responsibility](behavioral/chain-of-responsibility.md), [Command](behavioral/command.md), [Interpreter](behavioral/interpreter.md), [Iterator](behavioral/iterator.md), [Mediator](behavioral/mediator.md), [Memento](behavioral/memento.md), [Observer](behavioral/observer.md), [State](behavioral/state.md), [Strategy](behavioral/strategy.md), [Template Method](behavioral/template-method.md), [Visitor](behavioral/visitor.md) |

## Kalıpların arkasındaki ilkeler

Neredeyse her kalıp birkaç temel ilkeyi uygular:

- **Gerçekleştirime değil, arayüze göre programla.** `LegacyBankApi`'ye değil `PaymentProcessor`'a bağımlı ol.
- **Kalıtım yerine kompozisyonu tercih et.** Decorator, Strategy, Bridge, State... hepsi kalıtımı
  "sahip olma" (has-a) ilişkileriyle değiştirir.
- **Değişeni kapsülle.** Değişen kısmı bul ve bir soyutlamanın arkasına koy.
- **SOLID**
  - **S**ingle Responsibility – bir sınıfın değişmek için tek bir nedeni olmalı.
  - **O**pen/Closed – genişlemeye açık, değişikliğe kapalı.
  - **L**iskov Substitution – alt tipler, üst tipin beklendiği her yerde kullanılabilmeli.
  - **I**nterface Segregation – çok sayıda küçük arayüz, tek büyük arayüzden iyidir.
  - **D**ependency Inversion – somut sınıflara değil soyutlamalara bağımlı ol.

## Diyagramları okumak

Her kalıp sayfasında GitHub'ın doğrudan çizdiği bir [Mermaid](https://mermaid.js.org) sınıf
diyagramı bulunur.

| Gösterim | Anlamı |
|---|---|
| `A <\|-- B` | B, A'yı **extend eder** (kalıtım) |
| `A <\|.. B` | B, A'yı **implement eder** |
| `A --> B` | A, B'yi **kullanır / referans tutar** (ilişki) |
| `A o--> B` | A, B'ye **sahiptir** (aggregation, B, A olmadan da yaşayabilir) |
| `A ..> B` | A, B'ye **bağımlıdır / B'yi oluşturur** |
| `+` `-` `#` | public, private, protected |
| `method()*` | soyut metot |
| `method()$` | static metot |

## Hangi kalıbı kullanmalıyım?

| İhtiyacınız... | Değerlendirin |
|---|---|
| tam olarak bir nesne olması | Singleton (veya bunu bir DI container'a bırakın) |
| hangi nesnenin oluşturulacağına alt sınıfların karar vermesi | Factory Method |
| birbiriyle uyumlu olması gereken nesne aileleri oluşturmak | Abstract Factory |
| karmaşık bir nesneyi adım adım oluşturmak / devasa constructor'lardan kaçınmak | Builder |
| var olan nesneleri kopyalamak | Prototype |
| uyumsuz bir arayüzü uydurmak | Adapter |
| iki boyutu birbirinden bağımsız değiştirmek | Bridge |
| ağaçlara ve tekil nesnelere aynı şekilde davranmak | Composite |
| alt sınıf yazmadan çalışma zamanında davranış eklemek | Decorator |
| karmaşık bir alt sisteme basit bir giriş noktası vermek | Facade |
| binlerce benzer nesne arasında veri paylaşmak | Flyweight |
| erişimi kontrol etmek, lazy load, önbellek veya loglamayı şeffafça eklemek | Proxy |
| bir isteği birkaç nesnenin sırayla denemesini sağlamak | Chain of Responsibility |
| işlemleri geri almak/yinelemek, kuyruğa almak veya loglamak | Command |
| küçük bir dili / kural kümesini yorumlamak | Interpreter |
| bir koleksiyonu iç yapısını açmadan dolaşmak | Iterator |
| nesneler arasındaki karmaşık bağımlılıkları azaltmak | Mediator |
| durumu kaydedip geri yüklemek (snapshot) | Memento |
| bir şey değiştiğinde birçok nesneyi haberdar etmek | Observer |
| davranışı duruma göre değiştirmek, `switch (status)`'tan kurtulmak | State |
| algoritmalar arasında çalışma zamanında geçiş yapmak | Strategy |
| bir algoritma iskeletini yeniden kullanıp bazı adımları değiştirmek | Template Method |
| sabit bir sınıf hiyerarşisine yeni işlemler eklemek | Visitor |

## Sık karıştırılan kalıplar

| Kalıplar | Fark |
|---|---|
| **Adapter / Decorator / Proxy / Facade** | Adapter arayüzü *değiştirir*, Decorator arayüzü *korur* ve davranış ekler, Proxy arayüzü *korur* ve erişimi kontrol eder, Facade birçok sınıf için *yeni ve daha basit* bir arayüz oluşturur. |
| **Factory Method / Abstract Factory** | Kalıtımla tek ürün vs. kompozisyonla bir ürün ailesi. |
| **Strategy / State** | Aynı yapı. Strategy'leri istemci seçer ve birbirlerinden bağımsızdırlar; state'ler kendilerini değiştirir ve birbirlerini bilirler. |
| **Strategy / Template Method** | Kompozisyon (bütün algoritmayı değiştir) vs. kalıtım (bazı adımları override et). |
| **Composite / Decorator** | İkisi de özyinelemelidir. Composite'in çok sayıda çocuğu vardır ve onları toplar; decorator'ın tek çocuğu vardır ve onu zenginleştirir. |
| **Mediator / Observer** | Mediator iletişimi merkezileştirir; Observer abonelikler üzerinden dağıtır. Sıklıkla birlikte kullanılırlar. |
| **Command / Strategy** | Command *yapılacak bir iştir* (çoğu zaman undo ile); Strategy bir işi *yapmanın bir yoludur*. |

## Uyarı: aşırı mühendislikten kaçının

Kalıplar amaç değil araçtır. Basit bir `if`'in yettiği yere kalıp eklemek kodu okumayı zorlaştırır.
Bir kalıbı, çözdüğü **acıyı hissettiğinizde** (tekrarlanan koşullar, sınıf patlamaları, katı
bağımlılıklar) ya da o esnekliğe ihtiyaç duyulacağından emin olduğunuzda uygulayın.

Pek çok kalıp modern Java'da daha basittir: lambda'lar küçük Strategy/Command sınıflarının yerini
alır, record'lar değişmez değer nesnelerini kolaylaştırır, sealed interface'ler ve pattern matching
ise Visitor'a bir alternatif sunar. Bu repo, mantıklı olduğu yerlerde hem klasik formu hem de modern
alternatifi gösterir.
