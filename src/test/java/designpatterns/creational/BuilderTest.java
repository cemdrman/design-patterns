package designpatterns.creational;

import designpatterns.creational.builder.Car;
import designpatterns.creational.builder.CarDirector;
import designpatterns.creational.builder.CarManual;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BuilderTest {

    @Test
    void usesDefaultsForOptionalFields() {
        Car car = Car.builder().brand("Audi").model("A4").build();
        assertEquals("White", car.color());
        assertEquals(100, car.horsePower());
        assertFalse(car.hasSunroof());
    }

    @Test
    void validatesRequiredFields() {
        assertThrows(NullPointerException.class, () -> Car.builder().brand("Audi").build());
        assertThrows(IllegalStateException.class,
                () -> Car.builder().brand("Audi").model("A4").horsePower(0).build());
    }

    @Test
    void directorWorksWithDifferentBuilders() {
        CarDirector director = new CarDirector();

        Car.Builder carBuilder = Car.builder();
        director.constructFamilyCar(carBuilder);
        assertTrue(carBuilder.build().hasSunroof());

        CarManual.Builder manualBuilder = CarManual.builder();
        director.constructFamilyCar(manualBuilder);
        assertTrue(manualBuilder.build().pages().contains("How to open the sunroof"));
    }
}
