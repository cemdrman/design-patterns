package designpatterns.creational.factorymethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator: contains the business logic that works with cars, but leaves the decision
 * of <i>which</i> car to create to its subclasses through the {@link #createCar(String)}
 * factory method.
 */
public abstract class CarFactory {

    private final List<Car> producedCars = new ArrayList<>();

    /** The factory method. Subclasses decide which concrete {@link Car} is created. */
    protected abstract Car createCar(String model);

    /** Template-like business logic that only knows the {@link Car} abstraction. */
    public final Car order(String model) {
        Car car = createCar(model);
        qualityCheck(car);
        producedCars.add(car);
        return car;
    }

    public List<Car> producedCars() {
        return List.copyOf(producedCars);
    }

    private void qualityCheck(Car car) {
        if (car.horsePower() <= 0) {
            throw new IllegalStateException("Invalid car: " + car);
        }
    }
}
