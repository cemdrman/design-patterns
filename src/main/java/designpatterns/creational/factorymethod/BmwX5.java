package designpatterns.creational.factorymethod;

/** Concrete product. */
public record BmwX5(int horsePower) implements Car {

    public BmwX5() {
        this(380);
    }

    @Override
    public String brand() {
        return "BMW";
    }

    @Override
    public String model() {
        return "X5";
    }
}
