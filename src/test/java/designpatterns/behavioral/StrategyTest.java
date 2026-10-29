package designpatterns.behavioral;

import designpatterns.behavioral.strategy.BuyThreePayTwo;
import designpatterns.behavioral.strategy.DiscountStrategy;
import designpatterns.behavioral.strategy.ShoppingCart;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StrategyTest {

    @Test
    void totalDependsOnTheSelectedStrategy() {
        ShoppingCart cart = new ShoppingCart().add("a", "100.00").add("b", "50.00").add("c", "10.00");
        assertEquals(new BigDecimal("160.00"), cart.total());

        cart.setDiscount(DiscountStrategy.percentage(25));
        assertEquals(new BigDecimal("120.00"), cart.total());

        cart.setDiscount(DiscountStrategy.fixedAmount(new BigDecimal("20"), new BigDecimal("200")));
        assertEquals(new BigDecimal("160.00"), cart.total());

        cart.setDiscount(new BuyThreePayTwo().forCart(cart));
        assertEquals(new BigDecimal("150.00"), cart.total());
    }
}
