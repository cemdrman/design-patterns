package designpatterns.structural.decorator;

import java.math.BigDecimal;

/** Concrete component. */
public class Espresso implements Coffee {

    @Override
    public String description() {
        return "Espresso";
    }

    @Override
    public BigDecimal cost() {
        return new BigDecimal("60.00");
    }
}
