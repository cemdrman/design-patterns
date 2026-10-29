package designpatterns.creational.factorymethod;

/**
 * Product: every car created by a factory implements this interface.
 */
public interface Car {

    String brand();

    String model();

    int horsePower();

    default String describe() {
        return brand() + " " + model() + " (" + horsePower() + " hp)";
    }
}
