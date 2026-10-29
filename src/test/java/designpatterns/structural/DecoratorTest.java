package designpatterns.structural;

import designpatterns.structural.decorator.Caramel;
import designpatterns.structural.decorator.Coffee;
import designpatterns.structural.decorator.Espresso;
import designpatterns.structural.decorator.Milk;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecoratorTest {

    @Test
    void decoratorsAddCostAndDescription() {
        Coffee coffee = new Caramel(new Milk(new Espresso()));
        assertEquals("Espresso + milk + caramel", coffee.description());
        assertEquals(new BigDecimal("87.50"), coffee.cost());
    }
}
