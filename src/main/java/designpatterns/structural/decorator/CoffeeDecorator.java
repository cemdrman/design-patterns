package designpatterns.structural.decorator;

import java.math.BigDecimal;

/**
 * Base decorator: <b>is a</b> Coffee and <b>has a</b> Coffee.
 * It forwards every call to the wrapped object and lets subclasses add behaviour.
 */
public abstract class CoffeeDecorator implements Coffee {

    private final Coffee wrapped;

    protected CoffeeDecorator(Coffee wrapped) {
        this.wrapped = wrapped;
    }

    protected abstract String extraName();

    protected abstract BigDecimal extraCost();

    @Override
    public String description() {
        return wrapped.description() + " + " + extraName();
    }

    @Override
    public BigDecimal cost() {
        return wrapped.cost().add(extraCost());
    }
}
