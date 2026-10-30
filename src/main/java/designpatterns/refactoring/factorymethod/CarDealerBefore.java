package designpatterns.refactoring.factorymethod;

import designpatterns.creational.factorymethod.AudiA4;
import designpatterns.creational.factorymethod.AudiR8;
import designpatterns.creational.factorymethod.BmwX5;
import designpatterns.creational.factorymethod.BmwZ4;
import designpatterns.creational.factorymethod.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * BEFORE Factory Method – one class knows every brand and every model.
 * <p>Adding Mercedes means editing this class (and every other place with the same {@code switch}).
 *
 * @see designpatterns.creational.factorymethod.CarFactory the refactored version
 */
public class CarDealerBefore {

    private final List<Car> producedCars = new ArrayList<>();

    public Car order(String brand, String model) {
        Car car = switch (brand.toUpperCase()) {
            case "AUDI" -> switch (model.toUpperCase()) {
                case "A4" -> new AudiA4();
                case "R8" -> new AudiR8();
                default -> throw new IllegalArgumentException("Audi does not produce " + model);
            };
            case "BMW" -> switch (model.toUpperCase()) {
                case "Z4" -> new BmwZ4();
                case "X5" -> new BmwX5();
                default -> throw new IllegalArgumentException("BMW does not produce " + model);
            };
            default -> throw new IllegalArgumentException("Unknown brand " + brand);
        };
        if (car.horsePower() <= 0) {
            throw new IllegalStateException("Invalid car: " + car);
        }
        producedCars.add(car);
        return car;
    }
}
