package designpatterns.microservice.outbox;

import java.math.BigDecimal;

public record Order(String id, String customer, BigDecimal amount) {
}
