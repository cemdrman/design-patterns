package designpatterns.refactoring.builder;

/**
 * BEFORE Builder – telescoping constructors.
 * <p>{@code new CarBefore("BMW", "Z4", "Red", 340, true, false)}: what is {@code true}? what is {@code false}?
 * Every new optional field doubles the constructor list and swapped booleans compile fine.
 *
 * @see designpatterns.creational.builder.Car the refactored version
 */
public class CarBefore {

    private final String brand;
    private final String model;
    private final String color;
    private final int horsePower;
    private final boolean sunroof;
    private final boolean navigation;

    public CarBefore(String brand, String model) {
        this(brand, model, "White");
    }

    public CarBefore(String brand, String model, String color) {
        this(brand, model, color, 100);
    }

    public CarBefore(String brand, String model, String color, int horsePower) {
        this(brand, model, color, horsePower, false, false);
    }

    public CarBefore(String brand, String model, String color, int horsePower, boolean sunroof, boolean navigation) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.horsePower = horsePower;
        this.sunroof = sunroof;
        this.navigation = navigation;
    }

    @Override
    public String toString() {
        return "%s %s, %s, %d hp, sunroof=%s, navigation=%s"
                .formatted(brand, model, color, horsePower, sunroof, navigation);
    }
}
