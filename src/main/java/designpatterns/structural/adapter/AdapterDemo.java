package designpatterns.structural.adapter;

import designpatterns.common.Console;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class AdapterDemo {

    public static void main(String[] args) {
        Console.title("Adapter");

        // The checkout code depends only on PaymentProcessor.
        Map<String, PaymentProcessor> processors = new LinkedHashMap<>();
        processors.put("card", new CardPaymentProcessor());
        processors.put("legacy bank", new LegacyBankAdapter(new LegacyBankApi()));

        processors.forEach((name, processor) -> {
            Console.section(name);
            Console.print("pay 249.90", processor.pay("42", new BigDecimal("249.90")).success());
            Console.print("pay 20000.00", processor.pay("42", new BigDecimal("20000.00")).success());
        });
    }
}
