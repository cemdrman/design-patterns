package designpatterns.spring.strategy;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Context. Spring injects <b>every</b> {@link PaymentMethod} bean as a map keyed by bean name,
 * so adding a new payment method is just adding a new {@code @Component} – no {@code switch}.
 */
@Service
public class CheckoutService {

    private final Map<String, PaymentMethod> methods;

    public CheckoutService(Map<String, PaymentMethod> methods) {
        this.methods = methods;
    }

    public String pay(String method, BigDecimal amount) {
        PaymentMethod paymentMethod = methods.get(method);
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Unknown payment method '" + method + "', available: " + availableMethods());
        }
        return paymentMethod.pay(amount);
    }

    public Set<String> availableMethods() {
        return new TreeSet<>(methods.keySet());
    }
}
