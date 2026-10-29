package designpatterns.behavioral.state;

/**
 * State interface. Each state decides which transitions are allowed.
 * By default every action is invalid; states override only what they support.
 */
public interface OrderState {

    default OrderState pay(Order order) {
        throw invalid("pay");
    }

    default OrderState ship(Order order) {
        throw invalid("ship");
    }

    default OrderState deliver(Order order) {
        throw invalid("deliver");
    }

    default OrderState cancel(Order order) {
        throw invalid("cancel");
    }

    String name();

    private IllegalStateException invalid(String action) {
        return new IllegalStateException("Cannot " + action + " an order in state " + name());
    }
}
