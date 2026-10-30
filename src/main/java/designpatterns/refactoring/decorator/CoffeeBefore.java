package designpatterns.refactoring.decorator;

import java.math.BigDecimal;

/**
 * BEFORE Decorator – a subclass for every combination.
 * <p>2 coffees × 4 extras = 2 × 2⁴ = <b>32 classes</b>, and "double milk" is impossible.
 * Only a few of them are written here; you get the idea.
 *
 * @see designpatterns.structural.decorator.CoffeeDecorator the refactored version
 */
public class CoffeeBefore {

    public String description() {
        return "Espresso";
    }

    public BigDecimal cost() {
        return new BigDecimal("60.00");
    }

    public static class EspressoWithMilk extends CoffeeBefore {
        @Override
        public String description() {
            return "Espresso + milk";
        }

        @Override
        public BigDecimal cost() {
            return new BigDecimal("72.50");
        }
    }

    public static class EspressoWithCaramel extends CoffeeBefore {
        @Override
        public String description() {
            return "Espresso + caramel";
        }

        @Override
        public BigDecimal cost() {
            return new BigDecimal("75.00");
        }
    }

    public static class EspressoWithMilkAndCaramel extends EspressoWithMilk {
        @Override
        public String description() {
            return "Espresso + milk + caramel";
        }

        @Override
        public BigDecimal cost() {
            return new BigDecimal("87.50"); // a price change in milk must be repeated in many classes
        }
    }

    // FilterCoffeeWithMilk, FilterCoffeeWithMilkAndCaramel, EspressoWithMilkAndCaramelAndWhippedCream ...
}
