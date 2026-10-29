package designpatterns.behavioral.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Strategy interface. Being a functional interface, a strategy can also be a lambda.
 * The static factory methods offer ready made strategies.
 */
@FunctionalInterface
public interface DiscountStrategy {

    BigDecimal apply(BigDecimal total);

    static DiscountStrategy none() {
        return total -> total;
    }

    static DiscountStrategy percentage(int percent) {
        BigDecimal rate = BigDecimal.valueOf(100 - percent).movePointLeft(2);
        return total -> total.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }

    static DiscountStrategy fixedAmount(BigDecimal amount, BigDecimal minimumTotal) {
        return total -> total.compareTo(minimumTotal) >= 0 ? total.subtract(amount) : total;
    }
}
