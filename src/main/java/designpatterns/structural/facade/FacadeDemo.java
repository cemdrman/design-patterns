package designpatterns.structural.facade;

import designpatterns.common.Console;

import java.math.BigDecimal;

public class FacadeDemo {

    public static void main(String[] args) {
        Console.title("Facade");

        NotificationService notifications = new NotificationService();
        OrderFacade shop = new OrderFacade(new InventoryService(), new PaymentService(),
                new ShippingService(), notifications);

        Console.print("order a book", shop.placeOrder("cem", "BOOK-1", 2, new BigDecimal("150")));
        Console.print("order a laptop", shop.placeOrder("cem", "LAPTOP-1", 1, new BigDecimal("40000")));
        Console.print("e-mails sent", notifications.sent());
    }
}
