package designpatterns.structural.facade;

import java.math.BigDecimal;

/** Subsystem class. */
public class PaymentService {

    public boolean charge(String customerId, BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }
}
