package designpatterns.refactoring.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * BEFORE Strategy – the cart knows every campaign.
 * <p>Each new campaign adds a {@code case}, the cart is re-tested and re-deployed for marketing
 * changes, and campaigns can't be unit tested in isolation. A typo in the campaign name is a runtime error.
 *
 * @see designpatterns.behavioral.strategy.ShoppingCart the refactored version
 */
public class ShoppingCartBefore {

    private final List<BigDecimal> prices = new ArrayList<>();

    public ShoppingCartBefore add(String price) {
        prices.add(new BigDecimal(price));
        return this;
    }

    public BigDecimal total(String campaign) {
        BigDecimal subtotal = prices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        switch (campaign) {
            case "NONE":
                return subtotal;
            case "TEN_PERCENT":
                return subtotal.multiply(new BigDecimal("0.90")).setScale(2, RoundingMode.HALF_UP);
            case "200_OFF_OVER_1500":
                if (subtotal.compareTo(new BigDecimal("1500")) >= 0) {
                    return subtotal.subtract(new BigDecimal("200"));
                }
                return subtotal;
            case "BUY_3_PAY_2":
                BigDecimal free = prices.stream().sorted().limit(prices.size() / 3)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return subtotal.subtract(free);
            default:
                throw new IllegalArgumentException("Unknown campaign: " + campaign);
        }
    }
}
