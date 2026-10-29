package designpatterns.structural.decorator;

import java.math.BigDecimal;

/** Concrete decorator. */
public class ExtraShot extends CoffeeDecorator {

    public ExtraShot(Coffee coffee) {
        super(coffee);
    }

    @Override
    protected String extraName() {
        return "extra shot";
    }

    @Override
    protected BigDecimal extraCost() {
        return new BigDecimal("20.00");
    }
}
