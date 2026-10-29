package designpatterns.structural.adapter;

import java.math.BigDecimal;
import java.util.UUID;

/** An implementation that already fits the target interface. */
public class CardPaymentProcessor implements PaymentProcessor {

    @Override
    public PaymentResult pay(String customerId, BigDecimal amount) {
        return new PaymentResult(true, "CARD-" + UUID.randomUUID().toString().substring(0, 8));
    }
}
