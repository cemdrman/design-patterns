package designpatterns.microservice.saga;

import designpatterns.common.Console;

import java.math.BigDecimal;

public class SagaDemo {

    public static void main(String[] args) {
        Console.title("Saga");

        InventoryService inventory = new InventoryService();
        PaymentService payment = new PaymentService();
        ShippingService shipping = new ShippingService();
        PlaceOrderSaga saga = new PlaceOrderSaga(inventory, payment, shipping);
        BigDecimal price = new BigDecimal("45000");

        run("Happy path", saga.place("ORD-1", "cem", "laptop", 1, price, "Istanbul"));
        run("Payment fails", saga.place("ORD-2", "ayse", "laptop", 1, price, "Ankara"));
        run("Shipping fails", saga.place("ORD-3", "cem", "laptop", 1, price, "Van"));

        Console.section("Final state is consistent");
        Console.print("laptop stock (5 - 1)", inventory.stockOf("laptop"));
        Console.print("cem balance (100000 - 45000)", payment.balanceOf("cem"));
        Console.print("ayse balance (unchanged)", payment.balanceOf("ayse"));
    }

    private static void run(String title, SagaResult result) {
        Console.section(title);
        result.log().forEach(Console::line);
        Console.print("completed", result.completed());
    }
}
