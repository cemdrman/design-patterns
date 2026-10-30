package designpatterns.spring.strategy;

import java.math.BigDecimal;

/** Strategy interface. Every implementation is a Spring bean. */
public interface PaymentMethod {

    String pay(BigDecimal amount);
}
