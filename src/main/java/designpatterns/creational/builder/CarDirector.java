package designpatterns.creational.builder;

/**
 * Director: knows <i>in which order</i> the steps must be executed to produce
 * well known configurations. It works with any {@link CarBuilder}.
 */
public class CarDirector {

    public void constructSportsCar(CarBuilder builder) {
        builder.brand("Audi")
                .model("R8")
                .color("Red")
                .horsePower(620)
                .sunroof(false)
                .navigation(true);
    }

    public void constructFamilyCar(CarBuilder builder) {
        builder.brand("Ford")
                .model("Focus")
                .color("Grey")
                .horsePower(125)
                .sunroof(true)
                .navigation(true);
    }
}
