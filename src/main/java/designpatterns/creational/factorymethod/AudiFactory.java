package designpatterns.creational.factorymethod;

/** Concrete creator for Audi cars. */
public class AudiFactory extends CarFactory {

    @Override
    protected Car createCar(String model) {
        return switch (model.toUpperCase()) {
            case "A4" -> new AudiA4();
            case "R8" -> new AudiR8();
            default -> throw new IllegalArgumentException("Audi does not produce " + model);
        };
    }
}
