package designpatterns.structural;

import designpatterns.structural.adapter.LegacyBankAdapter;
import designpatterns.structural.adapter.LegacyBankApi;
import designpatterns.structural.adapter.PaymentProcessor;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdapterTest {

    private final PaymentProcessor processor = new LegacyBankAdapter(new LegacyBankApi());

    @Test
    void translatesSuccessfulPayment() {
        var result = processor.pay("1", new BigDecimal("10000.00"));
        assertTrue(result.success());
        assertEquals("BANK-0042", result.transactionId());
    }

    @Test
    void translatesRejectedPayment() {
        var result = processor.pay("1", new BigDecimal("10000.01"));
        assertFalse(result.success());
        assertNull(result.transactionId());
    }
}
