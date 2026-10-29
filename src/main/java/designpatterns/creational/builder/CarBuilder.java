package designpatterns.creational.builder;

/**
 * Builder interface: declares the construction steps shared by every builder.
 * Each method returns the builder itself so the calls can be chained.
 */
public interface CarBuilder {

    CarBuilder brand(String brand);

    CarBuilder model(String model);

    CarBuilder color(String color);

    CarBuilder horsePower(int horsePower);

    CarBuilder sunroof(boolean sunroof);

    CarBuilder navigation(boolean navigation);
}
