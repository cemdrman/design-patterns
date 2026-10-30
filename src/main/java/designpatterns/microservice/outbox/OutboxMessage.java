package designpatterns.microservice.outbox;

/** A row of the outbox table: an event waiting to be published. */
public record OutboxMessage(long id, String aggregateId, String type, String payload, boolean published) {

    public OutboxMessage markPublished() {
        return new OutboxMessage(id, aggregateId, type, payload, true);
    }
}
