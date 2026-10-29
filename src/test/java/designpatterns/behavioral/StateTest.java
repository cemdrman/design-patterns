package designpatterns.behavioral;

import designpatterns.behavioral.state.Order;
import designpatterns.behavioral.state.OrderStates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StateTest {

    @Test
    void followsTheHappyPath() {
        Order order = new Order();
        order.pay();
        order.ship();
        order.deliver();
        assertEquals(OrderStates.DELIVERED, order.state());
        assertEquals(3, order.history().size());
    }

    @Test
    void rejectsInvalidTransitions() {
        Order order = new Order();
        assertThrows(IllegalStateException.class, order::ship);
        order.cancel();
        assertEquals(OrderStates.CANCELLED, order.state());
        assertThrows(IllegalStateException.class, order::pay);
    }
}
