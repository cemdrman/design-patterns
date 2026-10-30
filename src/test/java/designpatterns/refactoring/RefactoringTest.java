package designpatterns.refactoring;

import designpatterns.behavioral.chainofresponsibility.Approver;
import designpatterns.behavioral.chainofresponsibility.Director;
import designpatterns.behavioral.chainofresponsibility.ExpenseRequest;
import designpatterns.behavioral.chainofresponsibility.Manager;
import designpatterns.behavioral.chainofresponsibility.TeamLead;
import designpatterns.behavioral.observer.MobileApp;
import designpatterns.behavioral.observer.PriceAlert;
import designpatterns.behavioral.observer.StockMarket;
import designpatterns.behavioral.state.Order;
import designpatterns.behavioral.strategy.BuyThreePayTwo;
import designpatterns.behavioral.strategy.DiscountStrategy;
import designpatterns.behavioral.strategy.ShoppingCart;
import designpatterns.behavioral.templatemethod.CsvReportExporter;
import designpatterns.behavioral.templatemethod.JsonReportExporter;
import designpatterns.behavioral.templatemethod.ReportExporter.Sale;
import designpatterns.creational.builder.Car;
import designpatterns.creational.factorymethod.AudiFactory;
import designpatterns.creational.factorymethod.BmwFactory;
import designpatterns.enterprise.dependencyinjection.RecordingNotifier;
import designpatterns.enterprise.dependencyinjection.WelcomeService;
import designpatterns.enterprise.nullobject.CheckoutService;
import designpatterns.enterprise.nullobject.CustomerDirectory;
import designpatterns.enterprise.specification.Product;
import designpatterns.enterprise.specification.ProductCatalog;
import designpatterns.refactoring.builder.CarBefore;
import designpatterns.refactoring.chainofresponsibility.ExpenseApprovalBefore;
import designpatterns.refactoring.decorator.CoffeeBefore;
import designpatterns.refactoring.dependencyinjection.WelcomeServiceBefore;
import designpatterns.refactoring.factorymethod.CarDealerBefore;
import designpatterns.refactoring.nullobject.CheckoutBefore;
import designpatterns.refactoring.observer.StockMarketBefore;
import designpatterns.refactoring.specification.ProductFinderBefore;
import designpatterns.refactoring.state.OrderBefore;
import designpatterns.refactoring.strategy.ShoppingCartBefore;
import designpatterns.refactoring.templatemethod.ReportsBefore;
import designpatterns.structural.decorator.Caramel;
import designpatterns.structural.decorator.Coffee;
import designpatterns.structural.decorator.Espresso;
import designpatterns.structural.decorator.Milk;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

import static designpatterns.enterprise.specification.ProductSpecifications.cheaperThan;
import static designpatterns.enterprise.specification.ProductSpecifications.inCategory;
import static designpatterns.enterprise.specification.ProductSpecifications.inStock;
import static designpatterns.enterprise.specification.ProductSpecifications.ratedAtLeast;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Characterization tests: a refactoring must not change behaviour.
 * Every "before" class must produce exactly the same results as its pattern-based "after" version.
 */
class RefactoringTest {

    @Nested
    class BuilderRefactoring {
        @Test
        void sameCar() {
            String before = new CarBefore("BMW", "Z4", "Red", 340, true, false).toString();
            String after = Car.builder().brand("BMW").model("Z4").color("Red").horsePower(340).sunroof(true).build().toString();
            assertEquals(before, after);
            assertEquals(new CarBefore("Audi", "A4").toString(), Car.builder().brand("Audi").model("A4").build().toString());
        }
    }

    @Nested
    class FactoryMethodRefactoring {
        @Test
        void sameCars() {
            CarDealerBefore dealer = new CarDealerBefore();
            assertEquals(new AudiFactory().order("R8"), dealer.order("Audi", "R8"));
            assertEquals(new BmwFactory().order("X5"), dealer.order("BMW", "X5"));
        }
    }

    @Nested
    class DecoratorRefactoring {
        @Test
        void sameCoffee() {
            CoffeeBefore before = new CoffeeBefore.EspressoWithMilkAndCaramel();
            Coffee after = new Caramel(new Milk(new Espresso()));
            assertEquals(before.description(), after.description());
            assertEquals(before.cost(), after.cost());
        }
    }

    @Nested
    class ChainOfResponsibilityRefactoring {
        @Test
        void sameDecisions() {
            Approver chain = new TeamLead();
            chain.setNext(new Manager()).setNext(new Director());
            ExpenseApprovalBefore before = new ExpenseApprovalBefore();
            for (String amount : List.of("750", "1000", "8500", "45000", "250000")) {
                ExpenseRequest request = new ExpenseRequest("cem", "trip", new BigDecimal(amount));
                assertEquals(before.approve(request), chain.approve(request));
            }
        }
    }

    @Nested
    class ObserverRefactoring {
        @Test
        void sameNotifications() {
            MobileApp appBefore = new MobileApp();
            PriceAlert alertBefore = new PriceAlert("THYAO", new BigDecimal("300"));
            StockMarketBefore before = new StockMarketBefore(appBefore, alertBefore);

            MobileApp appAfter = new MobileApp();
            PriceAlert alertAfter = new PriceAlert("THYAO", new BigDecimal("300"));
            StockMarket after = new StockMarket();
            after.subscribe(appAfter);
            after.subscribe(alertAfter);

            for (String price : List.of("290", "310", "310", "305")) {
                before.updatePrice("THYAO", new BigDecimal(price));
                after.updatePrice("THYAO", new BigDecimal(price));
            }
            assertEquals(appBefore.screen(), appAfter.screen());
            assertEquals(alertBefore.alerts(), alertAfter.alerts());
        }
    }

    @Nested
    class StateRefactoring {
        @Test
        void sameTransitionsAndErrors() {
            OrderBefore before = new OrderBefore();
            Order after = new Order();
            before.pay();
            after.pay();
            before.ship();
            after.ship();
            assertEquals(before.history(), after.history());
            assertEquals(before.status().name(), after.state().name());

            String beforeError = assertThrows(IllegalStateException.class, before::cancel).getMessage();
            String afterError = assertThrows(IllegalStateException.class, after::cancel).getMessage();
            assertEquals(beforeError, afterError);
        }
    }

    @Nested
    class StrategyRefactoring {
        @Test
        void sameTotals() {
            ShoppingCartBefore before = new ShoppingCartBefore().add("1249.90").add("450.00").add("150.00");
            ShoppingCart after = new ShoppingCart().add("a", "1249.90").add("b", "450.00").add("c", "150.00");

            assertEquals(before.total("NONE"), after.total());
            after.setDiscount(DiscountStrategy.percentage(10));
            assertEquals(before.total("TEN_PERCENT"), after.total());
            after.setDiscount(DiscountStrategy.fixedAmount(new BigDecimal("200"), new BigDecimal("1500")));
            assertEquals(before.total("200_OFF_OVER_1500"), after.total());
            after.setDiscount(new BuyThreePayTwo().forCart(after));
            assertEquals(before.total("BUY_3_PAY_2"), after.total());
        }
    }

    @Nested
    class TemplateMethodRefactoring {
        @Test
        void sameReports() {
            List<Sale> sales = List.of(new Sale("Laptop", 2, 45000), new Sale("Refund", 0, 100), new Sale("Mouse", 3, 450));
            ReportsBefore before = new ReportsBefore();
            assertEquals(before.toCsv(sales), new CsvReportExporter().export(sales));
            assertEquals(before.toJson(sales), new JsonReportExporter().export(sales));
        }
    }

    @Nested
    class NullObjectRefactoring {
        @Test
        void sameReceipts() {
            CheckoutBefore before = new CheckoutBefore();
            CheckoutService after = new CheckoutService(new CustomerDirectory());
            for (String id : new String[]{"C1", "C2", "C404", null}) {
                assertEquals(before.receipt(id, new BigDecimal("250")), after.receipt(id, new BigDecimal("250")));
            }
        }
    }

    @Nested
    class SpecificationRefactoring {
        @Test
        void sameResults() {
            List<Product> products = List.of(
                    new Product("Keyboard", "electronics", new BigDecimal("1249.90"), 12, 4.7),
                    new Product("Mouse", "electronics", new BigDecimal("450.00"), 0, 4.4),
                    new Product("Clean Code", "books", new BigDecimal("390.00"), 5, 4.8),
                    new Product("Design Patterns", "books", new BigDecimal("650.00"), 0, 4.6));
            ProductFinderBefore before = new ProductFinderBefore(products);
            ProductCatalog after = new ProductCatalog(products);

            assertEquals(before.findByCategoryAndPriceLessThan("electronics", "500"),
                    after.findAll(inCategory("electronics").and(cheaperThan("500"))));
            assertEquals(before.findInStockWithRatingAtLeast(4.5), after.findAll(inStock().and(ratedAtLeast(4.5))));
            assertEquals(before.findByCategoryAndOutOfStock("books"), after.findAll(inCategory("books").and(inStock().not())));
        }
    }

    @Nested
    class DependencyInjectionRefactoring {
        @Test
        void onlyTheRefactoredVersionCanBeTestedDeterministically() {
            // Before: the greeting depends on the time the test runs, and a "real" e-mail goes out.
            assertTrue(new WelcomeServiceBefore().welcome("cem@example.com", "Cem").endsWith(", Cem!"));

            // After: we choose the time and capture the message.
            RecordingNotifier notifier = new RecordingNotifier();
            WelcomeService after = new WelcomeService(notifier, Clock.fixed(Instant.parse("2026-10-30T21:00:00Z"), ZoneOffset.UTC));
            assertEquals("Good evening, Cem!", after.welcome("cem@example.com", "Cem"));
            assertEquals(1, notifier.sent().size());
        }
    }
}
