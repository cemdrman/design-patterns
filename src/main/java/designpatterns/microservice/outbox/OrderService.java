package designpatterns.microservice.outbox;

import java.math.BigDecimal;

/**
 * Writes the order AND the event in the same local transaction. It never talks to the message
 * broker directly, so "order saved but event lost" (or the opposite) cannot happen.
 */
public class OrderService {

    private final Database database;

    public OrderService(Database database) {
        this.database = database;
    }

    public void placeOrder(String orderId, String customer, BigDecimal amount) {
        database.inTransaction(tx -> {
            if (amount.signum() <= 0) {
                throw new IllegalArgumentException("amount must be positive");
            }
            tx.insertOrder(new Order(orderId, customer, amount));
            tx.insertOutboxMessage(orderId, "OrderPlaced",
                    "{\"orderId\":\"%s\",\"customer\":\"%s\",\"amount\":%s}".formatted(orderId, customer, amount));
        });
    }
}
