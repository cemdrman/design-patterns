package designpatterns.creational.factorymethod;

import designpatterns.common.Console;

public class FactoryMethodDemo {

    public static void main(String[] args) {
        Console.title("Factory Method");

        // The client only knows the CarFactory abstraction.
        CarFactory audi = new AudiFactory();
        CarFactory bmw = new BmwFactory();

        Console.section("Ordering cars");
        Console.print("audi.order(\"A4\")", audi.order("A4").describe());
        Console.print("audi.order(\"R8\")", audi.order("R8").describe());
        Console.print("bmw.order(\"Z4\")", bmw.order("Z4").describe());
        Console.print("bmw.order(\"X5\")", bmw.order("X5").describe());

        Console.section("Produced cars");
        Console.print("Audi factory", audi.producedCars().size());
        Console.print("BMW factory", bmw.producedCars().size());

        Console.section("Unknown model");
        try {
            bmw.order("A4");
        } catch (IllegalArgumentException e) {
            Console.print("exception", e.getMessage());
        }
    }
}
