package designpatterns.structural.decorator;

import java.math.BigDecimal;

/** Component interface shared by the real object and all decorators. */
public interface Coffee {

    String description();

    BigDecimal cost();
}
