package designpatterns.structural.decorator;

import java.math.BigDecimal;

/** Concrete decorator. */
public class Caramel extends CoffeeDecorator {

    public Caramel(Coffee coffee) {
        super(coffee);
    }

    @Override
    protected String extraName() {
        return "caramel";
    }

    @Override
    protected BigDecimal extraCost() {
        return new BigDecimal("15.00");
    }
}
