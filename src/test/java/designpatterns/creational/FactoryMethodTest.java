package designpatterns.creational;

import designpatterns.creational.factorymethod.AudiFactory;
import designpatterns.creational.factorymethod.AudiR8;
import designpatterns.creational.factorymethod.BmwFactory;
import designpatterns.creational.factorymethod.CarFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FactoryMethodTest {

    @Test
    void subclassDecidesWhichCarIsCreated() {
        CarFactory audi = new AudiFactory();
        assertInstanceOf(AudiR8.class, audi.order("r8"));
        assertEquals("BMW", new BmwFactory().order("Z4").brand());
        assertEquals(1, audi.producedCars().size());
    }

    @Test
    void unknownModelIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new BmwFactory().order("A4"));
    }
}
