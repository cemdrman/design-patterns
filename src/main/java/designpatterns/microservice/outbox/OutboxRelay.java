package designpatterns.microservice.outbox;

/**
 * Message relay (in production: a scheduled poller or Debezium CDC). Reads unpublished rows,
 * publishes them and marks them as published. If the broker is down it simply tries again on the
 * next run, so delivery is <b>at-least-once</b>: consumers must be idempotent.
 */
public class OutboxRelay {

    private final Database database;
    private final MessageBroker broker;

    public OutboxRelay(Database database, MessageBroker broker) {
        this.database = database;
        this.broker = broker;
    }

    /** @return how many messages were published in this run */
    public int publishPending() {
        int count = 0;
        for (OutboxMessage message : database.unpublishedMessages()) {
            try {
                broker.publish(message.type(), message.payload());
            } catch (RuntimeException e) {
                break; // keep order; retry on the next run
            }
            database.markPublished(message.id());
            count++;
        }
        return count;
    }
}
