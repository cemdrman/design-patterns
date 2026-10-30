package designpatterns.microservice.cqrs;

import java.math.BigDecimal;

/** Read model row: denormalized, shaped exactly like the screen needs it. */
public record ProductView(String sku, String name, BigDecimal price, boolean available, String label) {
}
