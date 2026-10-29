package designpatterns.creational.factorymethod;

/** Concrete product. */
public record AudiR8(int horsePower) implements Car {

    public AudiR8() {
        this(620);
    }

    @Override
    public String brand() {
        return "Audi";
    }

    @Override
    public String model() {
        return "R8";
    }
}
