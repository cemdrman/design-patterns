package designpatterns.behavioral.strategy;

import designpatterns.common.Console;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

public class StrategyDemo {

    public static void main(String[] args) {
        Console.title("Strategy");

        ShoppingCart cart = new ShoppingCart()
                .add("Keyboard", "1249.90")
                .add("Mouse", "450.00")
                .add("Mouse pad", "150.00");
        Console.print("subtotal", cart.subtotal());

        Map<String, DiscountStrategy> strategies = new LinkedHashMap<>();
        strategies.put("no discount", DiscountStrategy.none());
        strategies.put("10% off", DiscountStrategy.percentage(10));
        strategies.put("200 TL off over 1500 TL", DiscountStrategy.fixedAmount(new BigDecimal("200"), new BigDecimal("1500")));
        strategies.put("buy 3 pay 2", new BuyThreePayTwo().forCart(cart));
        strategies.put("lambda: round down to 100", total -> total.setScale(-2, RoundingMode.DOWN).setScale(2));

        Console.section("Same cart, different strategies");
        strategies.forEach((name, strategy) -> {
            cart.setDiscount(strategy);
            Console.print(name, cart.total());
        });
    }
}
