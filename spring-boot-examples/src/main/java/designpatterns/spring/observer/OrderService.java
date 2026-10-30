package designpatterns.spring.observer;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/** Subject. It publishes an event and doesn't know who listens. */
@Service
public class OrderService {

    private final ApplicationEventPublisher publisher;

    public OrderService(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void placeOrder(String orderId, String email, BigDecimal amount) {
        // ... save the order ...
        publisher.publishEvent(new OrderPlacedEvent(orderId, email, amount));
    }
}
