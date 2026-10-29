package designpatterns.creational.factorymethod;

/** Concrete product. */
public record BmwZ4(int horsePower) implements Car {

    public BmwZ4() {
        this(340);
    }

    @Override
    public String brand() {
        return "BMW";
    }

    @Override
    public String model() {
        return "Z4";
    }
}
