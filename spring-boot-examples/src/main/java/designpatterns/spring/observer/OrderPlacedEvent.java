package designpatterns.spring.observer;

import java.math.BigDecimal;

/** The event. Any object can be published; a record is ideal because events are immutable facts. */
public record OrderPlacedEvent(String orderId, String email, BigDecimal amount) {
}
