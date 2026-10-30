package designpatterns.spring;

import designpatterns.spring.chainofresponsibility.OrderRequest;
import designpatterns.spring.chainofresponsibility.OrderValidationService;
import designpatterns.spring.decorator.AuditingNotifier;
import designpatterns.spring.decorator.Notifier;
import designpatterns.spring.observer.ConfirmationEmailListener;
import designpatterns.spring.observer.LoyaltyPointsListener;
import designpatterns.spring.observer.OrderService;
import designpatterns.spring.proxy.ExchangeRateService;
import designpatterns.spring.scope.ReportBuilder;
import designpatterns.spring.scope.ReportService;
import designpatterns.spring.strategy.CheckoutService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/** Prints one small demo per pattern when the application starts. */
@Component
@ConditionalOnProperty(name = "demo.run", havingValue = "true", matchIfMissing = true)
class DemoRunner implements CommandLineRunner {

    private final ApplicationContext context;

    DemoRunner(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(String... args) {
        strategy();
        observer();
        proxy();
        decorator();
        chainOfResponsibility();
        singletonAndPrototype();
    }

    private void strategy() {
        title("Strategy: Map<String, PaymentMethod> injection");
        CheckoutService checkout = context.getBean(CheckoutService.class);
        print("available methods", checkout.availableMethods());
        for (String method : checkout.availableMethods()) {
            print(method, checkout.pay(method, new BigDecimal("1250.00")));
        }
    }

    private void observer() {
        title("Observer: ApplicationEventPublisher + @EventListener");
        OrderService orders = context.getBean(OrderService.class);
        orders.placeOrder("ORD-1", "cem@example.com", new BigDecimal("1250.00"));
        orders.placeOrder("ORD-2", "cem@example.com", new BigDecimal("380.00"));
        context.getBean(ConfirmationEmailListener.class).sent().forEach(System.out::println);
        print("loyalty points of cem", context.getBean(LoyaltyPointsListener.class).pointsOf("cem@example.com"));
    }

    private void proxy() {
        title("Proxy: @Cacheable");
        ExchangeRateService rates = context.getBean(ExchangeRateService.class);
        print("injected class", rates.getClass().getSimpleName());
        for (int i = 0; i < 3; i++) {
            rates.rate("USD");
        }
        print("rate(\"USD\") x3 -> remote calls", rates.remoteCalls());
        rates.toTry(BigDecimal.TEN, "USD");
        rates.toTry(BigDecimal.TEN, "USD");
        print("self-invocation x2 -> remote calls", rates.remoteCalls() + " (cache bypassed!)");
    }

    private void decorator() {
        title("Decorator: @Primary wrapper + @Qualifier");
        Notifier notifier = context.getBean(Notifier.class);
        print("injected Notifier", notifier.getClass().getSimpleName());
        print("send()", notifier.send("ayse@example.com", "Your order is on the way"));
        print("audit log", context.getBean(AuditingNotifier.class).auditLog());
    }

    private void chainOfResponsibility() {
        title("Chain of Responsibility: List<OrderValidator> sorted by @Order");
        OrderValidationService validation = context.getBean(OrderValidationService.class);
        print("chain", validation.chainOrder());
        print("valid order", validation.validate(new OrderRequest("cem", 2, "DP-10")).orElse("OK"));
        print("no customer, bad quantity", validation.validate(new OrderRequest("", 50, null)).orElse("OK"));
        print("bad coupon", validation.validate(new OrderRequest("cem", 1, "FREE")).orElse("OK"));
    }

    private void singletonAndPrototype() {
        title("Singleton & Prototype scopes, ObjectProvider as a factory");
        print("ReportService singleton?", context.getBean(ReportService.class) == context.getBean(ReportService.class));
        print("ReportBuilder singleton?", context.getBean(ReportBuilder.class) == context.getBean(ReportBuilder.class));
        ReportService reports = context.getBean(ReportService.class);
        print("report 1", reports.dailyReport("sales: 12", "returns: 1"));
        print("report 2", reports.dailyReport("sales: 7"));
    }

    private static void title(String text) {
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println(" " + text);
        System.out.println("=".repeat(60));
    }

    private static void print(String label, Object value) {
        System.out.printf("%-38s: %s%n", label, value);
    }
}
