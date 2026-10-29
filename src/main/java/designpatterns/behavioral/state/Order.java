package designpatterns.behavioral.state;

import java.util.ArrayList;
import java.util.List;

/**
 * Context: delegates every action to its current state object
 * instead of using a big {@code if/switch} on a status field.
 */
public class Order {

    private OrderState state = OrderStates.NEW;
    private final List<String> history = new ArrayList<>();

    public void pay() {
        state = state.pay(this);
    }

    public void ship() {
        state = state.ship(this);
    }

    public void deliver() {
        state = state.deliver(this);
    }

    public void cancel() {
        state = state.cancel(this);
    }

    public OrderState state() {
        return state;
    }

    void log(String event) {
        history.add(event);
    }

    public List<String> history() {
        return List.copyOf(history);
    }
}
