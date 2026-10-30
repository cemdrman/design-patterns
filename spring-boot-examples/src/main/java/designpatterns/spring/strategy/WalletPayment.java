package designpatterns.spring.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("wallet")
class WalletPayment implements PaymentMethod {

    @Override
    public String pay(BigDecimal amount) {
        return "Paid " + amount + " TL from the wallet balance";
    }
}
