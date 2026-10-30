package designpatterns.microservice.outbox;

import designpatterns.common.Console;

import java.math.BigDecimal;

public class OutboxDemo {

    public static void main(String[] args) {
        Console.title("Transactional Outbox");

        Database database = new Database();
        MessageBroker broker = new MessageBroker();
        OrderService orders = new OrderService(database);
        OutboxRelay relay = new OutboxRelay(database, broker);

        Console.section("Broker is down while orders are placed");
        broker.setAvailable(false);
        orders.placeOrder("ORD-1", "cem", new BigDecimal("1250.00"));
        orders.placeOrder("ORD-2", "ayse", new BigDecimal("320.50"));
        Console.print("orders saved", database.orders().size());
        Console.print("relay published", relay.publishPending());
        Console.print("waiting in outbox", database.unpublishedMessages().size());

        Console.section("A failing transaction saves neither the order nor the event");
        try {
            orders.placeOrder("ORD-3", "mehmet", BigDecimal.ZERO);
        } catch (IllegalArgumentException e) {
            Console.print("rolled back", e.getMessage());
        }
        Console.print("orders / outbox rows", database.orders().size() + " / " + database.outbox().size());

        Console.section("Broker is back");
        broker.setAvailable(true);
        Console.print("relay published", relay.publishPending());
        broker.published().forEach(Console::line);
        Console.print("waiting in outbox", database.unpublishedMessages().size());
    }
}
