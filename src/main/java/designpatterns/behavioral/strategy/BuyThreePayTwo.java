package designpatterns.behavioral.strategy;

import java.math.BigDecimal;

/**
 * A strategy that needs more than the total: it works on the cart items,
 * so it is written as a separate class.
 */
public class BuyThreePayTwo {

    public BigDecimal cheapestFreeDiscount(ShoppingCart cart) {
        return cart.items().stream()
                .map(ShoppingCart.Item::price)
                .sorted()
                .limit(cart.items().size() / 3)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public DiscountStrategy forCart(ShoppingCart cart) {
        BigDecimal discount = cheapestFreeDiscount(cart);
        return total -> total.subtract(discount);
    }
}
