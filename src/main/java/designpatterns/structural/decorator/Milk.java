package designpatterns.structural.decorator;

import java.math.BigDecimal;

/** Concrete decorator. */
public class Milk extends CoffeeDecorator {

    public Milk(Coffee coffee) {
        super(coffee);
    }

    @Override
    protected String extraName() {
        return "milk";
    }

    @Override
    protected BigDecimal extraCost() {
        return new BigDecimal("12.50");
    }
}
