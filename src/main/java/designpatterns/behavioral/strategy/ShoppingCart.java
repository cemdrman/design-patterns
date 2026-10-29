package designpatterns.behavioral.strategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Context: uses a strategy but does not know how the discount is calculated. */
public class ShoppingCart {

    private final List<Item> items = new ArrayList<>();
    private DiscountStrategy discount = DiscountStrategy.none();

    public ShoppingCart add(String name, String price) {
        items.add(new Item(name, new BigDecimal(price)));
        return this;
    }

    /** The strategy can be changed at runtime. */
    public void setDiscount(DiscountStrategy discount) {
        this.discount = discount;
    }

    public BigDecimal subtotal() {
        return items.stream().map(Item::price).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal total() {
        return discount.apply(subtotal());
    }

    public List<Item> items() {
        return List.copyOf(items);
    }

    public record Item(String name, BigDecimal price) {
    }
}
