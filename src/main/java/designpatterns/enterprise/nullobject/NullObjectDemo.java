package designpatterns.enterprise.nullobject;

import designpatterns.common.Console;

import java.math.BigDecimal;

public class NullObjectDemo {

    public static void main(String[] args) {
        Console.title("Null Object");

        CheckoutService checkout = new CheckoutService(new CustomerDirectory());
        BigDecimal amount = new BigDecimal("500.00");

        Console.print("C1", checkout.receipt("C1", amount));
        Console.print("C2", checkout.receipt("C2", amount));
        Console.print("unknown id", checkout.receipt("C999", amount));
        Console.print("no id at all", checkout.receipt(null, amount));
    }
}
