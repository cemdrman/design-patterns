package designpatterns.microservice.cqrs;

import java.math.BigDecimal;

/** Facts published by the write side. The read side builds its own model from them. */
public sealed interface ProductEvent {

    String sku();

    record ProductCreated(String sku, String name, BigDecimal price) implements ProductEvent {
    }

    record PriceChanged(String sku, BigDecimal newPrice) implements ProductEvent {
    }

    record StockChanged(String sku, int newStock) implements ProductEvent {
    }
}
