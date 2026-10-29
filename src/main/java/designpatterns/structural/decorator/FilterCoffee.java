package designpatterns.structural.decorator;

import java.math.BigDecimal;

/** Concrete component. */
public class FilterCoffee implements Coffee {

    @Override
    public String description() {
        return "Filter coffee";
    }

    @Override
    public BigDecimal cost() {
        return new BigDecimal("50.00");
    }
}
