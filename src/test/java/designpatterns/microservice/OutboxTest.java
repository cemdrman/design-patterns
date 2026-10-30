package designpatterns.microservice;

import designpatterns.microservice.outbox.Database;
import designpatterns.microservice.outbox.MessageBroker;
import designpatterns.microservice.outbox.OrderService;
import designpatterns.microservice.outbox.OutboxRelay;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OutboxTest {

    private final Database database = new Database();
    private final MessageBroker broker = new MessageBroker();
    private final OrderService orders = new OrderService(database);
    private final OutboxRelay relay = new OutboxRelay(database, broker);

    @Test
    void orderAndEventAreSavedAtomically() {
        orders.placeOrder("O1", "cem", new BigDecimal("10"));
        assertThrows(IllegalArgumentException.class, () -> orders.placeOrder("O2", "cem", BigDecimal.ZERO));

        assertEquals(1, database.orders().size());
        assertEquals(1, database.outbox().size());
    }

    @Test
    void eventsSurviveABrokerOutage() {
        broker.setAvailable(false);
        orders.placeOrder("O1", "cem", new BigDecimal("10"));
        orders.placeOrder("O2", "ayse", new BigDecimal("20"));
        assertEquals(0, relay.publishPending());

        broker.setAvailable(true);
        assertEquals(2, relay.publishPending());
        assertTrue(broker.published().getFirst().contains("\"orderId\":\"O1\""));
        assertTrue(database.unpublishedMessages().isEmpty());
        assertEquals(0, relay.publishPending());
    }
}
