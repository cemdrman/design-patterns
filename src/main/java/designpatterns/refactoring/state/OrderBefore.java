package designpatterns.refactoring.state;

import java.util.ArrayList;
import java.util.List;

/**
 * BEFORE State – a status field and a {@code switch} in every method.
 * <p>The rules of one state (e.g. SHIPPED) are spread over four methods. Adding RETURNED means
 * touching every method, and forgetting one case is easy.
 *
 * @see designpatterns.behavioral.state.Order the refactored version
 */
public class OrderBefore {

    public enum Status { NEW, PAID, SHIPPED, DELIVERED, CANCELLED }

    private Status status = Status.NEW;
    private final List<String> history = new ArrayList<>();

    public void pay() {
        switch (status) {
            case NEW -> {
                history.add("payment received");
                status = Status.PAID;
            }
            default -> throw invalid("pay");
        }
    }

    public void ship() {
        switch (status) {
            case PAID -> {
                history.add("handed over to cargo");
                status = Status.SHIPPED;
            }
            default -> throw invalid("ship");
        }
    }

    public void deliver() {
        switch (status) {
            case SHIPPED -> {
                history.add("delivered to customer");
                status = Status.DELIVERED;
            }
            default -> throw invalid("deliver");
        }
    }

    public void cancel() {
        switch (status) {
            case NEW -> {
                history.add("cancelled before payment");
                status = Status.CANCELLED;
            }
            case PAID -> {
                history.add("cancelled, money refunded");
                status = Status.CANCELLED;
            }
            default -> throw invalid("cancel");
        }
    }

    private IllegalStateException invalid(String action) {
        return new IllegalStateException("Cannot " + action + " an order in state " + status);
    }

    public Status status() {
        return status;
    }

    public List<String> history() {
        return List.copyOf(history);
    }
}
