package designpatterns.creational.factorymethod;

/** Concrete product. */
public record AudiA4(int horsePower) implements Car {

    public AudiA4() {
        this(150);
    }

    @Override
    public String brand() {
        return "Audi";
    }

    @Override
    public String model() {
        return "A4";
    }
}
