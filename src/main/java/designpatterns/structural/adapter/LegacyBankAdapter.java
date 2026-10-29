package designpatterns.structural.adapter;

import java.math.BigDecimal;

/**
 * Adapter: implements the target interface and translates every call
 * into something the adaptee understands (object adapter, uses composition).
 */
public class LegacyBankAdapter implements PaymentProcessor {

    private final LegacyBankApi bankApi;

    public LegacyBankAdapter(LegacyBankApi bankApi) {
        this.bankApi = bankApi;
    }

    @Override
    public PaymentResult pay(String customerId, BigDecimal amount) {
        long cents = amount.movePointRight(2).longValueExact();
        String status = bankApi.transfer("TR-" + customerId, cents);
        boolean success = "00".equals(status);
        return new PaymentResult(success, success ? bankApi.lastReference() : null);
    }
}
