package designpatterns.structural.adapter;

import java.math.BigDecimal;

/** Target: the interface our application expects. */
public interface PaymentProcessor {

    PaymentResult pay(String customerId, BigDecimal amount);

    record PaymentResult(boolean success, String transactionId) {
    }
}
