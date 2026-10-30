package designpatterns.spring.observer;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/** Observer #1. Spring finds every {@code @EventListener} method and subscribes it. */
@Component
public class ConfirmationEmailListener {

    private final List<String> sent = new CopyOnWriteArrayList<>();

    @EventListener
    void on(OrderPlacedEvent event) {
        sent.add("Order " + event.orderId() + " confirmed -> " + event.email());
    }

    public List<String> sent() {
        return List.copyOf(sent);
    }
}
