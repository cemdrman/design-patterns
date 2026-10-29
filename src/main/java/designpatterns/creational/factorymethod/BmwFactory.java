package designpatterns.creational.factorymethod;

/** Concrete creator for BMW cars. */
public class BmwFactory extends CarFactory {

    @Override
    protected Car createCar(String model) {
        return switch (model.toUpperCase()) {
            case "Z4" -> new BmwZ4();
            case "X5" -> new BmwX5();
            default -> throw new IllegalArgumentException("BMW does not produce " + model);
        };
    }
}
