package designpatterns.structural.decorator;

import java.math.BigDecimal;

/** Concrete decorator. */
public class WhippedCream extends CoffeeDecorator {

    public WhippedCream(Coffee coffee) {
        super(coffee);
    }

    @Override
    protected String extraName() {
        return "whipped cream";
    }

    @Override
    protected BigDecimal extraCost() {
        return new BigDecimal("10.00");
    }
}
