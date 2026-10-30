package designpatterns.enterprise.specification;

import java.math.BigDecimal;

public record Product(String name, String category, BigDecimal price, int stock, double rating) {
}
