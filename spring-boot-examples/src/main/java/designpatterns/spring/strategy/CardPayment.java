package designpatterns.spring.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/** The bean name ("card") becomes the key in the injected map. */
@Component("card")
class CardPayment implements PaymentMethod {

    @Override
    public String pay(BigDecimal amount) {
        return "Charged " + amount + " TL to the credit card";
    }
}
