package designpatterns.microservice.outbox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A tiny simulated relational database with two tables: {@code orders} and {@code outbox}.
 * {@link #inTransaction} is all-or-nothing, like a real local ACID transaction.
 */
public class Database {

    private final List<Order> orders = new ArrayList<>();
    private final List<OutboxMessage> outbox = new ArrayList<>();
    private long outboxSequence;

    public synchronized void inTransaction(Consumer<Transaction> work) {
        Transaction tx = new Transaction();
        work.accept(tx); // an exception here means nothing is committed
        orders.addAll(tx.orders);
        for (OutboxMessage message : tx.messages) {
            outbox.add(new OutboxMessage(++outboxSequence, message.aggregateId(), message.type(), message.payload(), false));
        }
    }

    public synchronized List<OutboxMessage> unpublishedMessages() {
        return outbox.stream().filter(m -> !m.published()).toList();
    }

    public synchronized void markPublished(long messageId) {
        outbox.replaceAll(m -> m.id() == messageId ? m.markPublished() : m);
    }

    public synchronized List<Order> orders() {
        return List.copyOf(orders);
    }

    public synchronized List<OutboxMessage> outbox() {
        return List.copyOf(outbox);
    }

    /** Changes staged inside one transaction. */
    public static final class Transaction {

        private final List<Order> orders = new ArrayList<>();
        private final List<OutboxMessage> messages = new ArrayList<>();

        public void insertOrder(Order order) {
            orders.add(order);
        }

        public void insertOutboxMessage(String aggregateId, String type, String payload) {
            messages.add(new OutboxMessage(0, aggregateId, type, payload, false));
        }
    }
}
