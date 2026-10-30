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
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class SpringPatternsTest {

    @Autowired
    ApplicationContext context;

    @Test
    void strategyBeansAreInjectedAsAMap() {
        CheckoutService checkout = context.getBean(CheckoutService.class);

        assertThat(checkout.availableMethods()).isEqualTo(Set.of("bankTransfer", "card", "wallet"));
        assertThat(checkout.pay("wallet", new BigDecimal("10"))).isEqualTo("Paid 10 TL from the wallet balance");
        assertThatThrownBy(() -> checkout.pay("crypto", BigDecimal.ONE)).hasMessageContaining("available");
    }

    @Test
    void eventsReachEveryListener() {
        context.getBean(OrderService.class).placeOrder("T-1", "test@example.com", new BigDecimal("550"));

        assertThat(context.getBean(ConfirmationEmailListener.class).sent())
                .contains("Order T-1 confirmed -> test@example.com");
        assertThat(context.getBean(LoyaltyPointsListener.class).pointsOf("test@example.com")).isEqualTo(5);
    }

    @Test
    void cacheableWorksThroughTheProxyButNotOnSelfInvocation() {
        ExchangeRateService rates = context.getBean(ExchangeRateService.class);
        assertThat(AopUtils.isCglibProxy(rates)).isTrue();

        int before = rates.remoteCalls();
        rates.rate("EUR");
        rates.rate("EUR");
        assertThat(rates.remoteCalls()).isEqualTo(before + 1);

        rates.toTry(BigDecimal.ONE, "EUR");
        assertThat(rates.remoteCalls()).isEqualTo(before + 2);
    }

    @Test
    void primaryDecoratorWrapsTheRealNotifier() {
        Notifier notifier = context.getBean(Notifier.class);

        assertThat(notifier).isInstanceOf(AuditingNotifier.class);
        assertThat(notifier.send("a@b.c", "hi")).isEqualTo("e-mail to a@b.c: hi [audited]");
        assertThat(((AuditingNotifier) notifier).auditLog()).contains("notification sent to a@b.c");
    }

    @Test
    void validatorsFormAChainSortedByOrder() {
        OrderValidationService validation = context.getBean(OrderValidationService.class);

        assertThat(validation.chainOrder()).isEqualTo(List.of("CustomerValidator", "QuantityValidator", "CouponValidator"));
        assertThat(validation.validate(new OrderRequest("cem", 1, null))).isEmpty();
        assertThat(validation.validate(new OrderRequest(" ", 99, "X"))).isEqualTo(Optional.of("customer is required"));
        assertThat(validation.validate(new OrderRequest("cem", 1, "X"))).isEqualTo(Optional.of("invalid coupon X"));
    }

    @Test
    void singletonAndPrototypeScopes() {
        assertThat(context.getBean(ReportService.class)).isSameAs(context.getBean(ReportService.class));
        assertThat(context.getBean(ReportBuilder.class)).isNotSameAs(context.getBean(ReportBuilder.class));

        ReportService reports = context.getBean(ReportService.class);
        assertThat(reports.dailyReport("a", "b")).isEqualTo("a | b");
        assertThat(reports.dailyReport("c")).isEqualTo("c");
    }

    @Test
    void demoRunnerIsDisabledInTests() {
        assertThat(context.getBeanNamesForType(DemoRunner.class)).isEmpty();
    }
}
