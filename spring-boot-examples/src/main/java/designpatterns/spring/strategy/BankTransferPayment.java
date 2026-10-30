package designpatterns.spring.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("bankTransfer")
class BankTransferPayment implements PaymentMethod {

    @Override
    public String pay(BigDecimal amount) {
        return "Waiting for a bank transfer of " + amount + " TL";
    }
}
