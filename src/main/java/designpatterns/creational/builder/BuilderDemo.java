package designpatterns.creational.builder;

import designpatterns.common.Console;

public class BuilderDemo {

    public static void main(String[] args) {
        Console.title("Builder");

        Console.section("Fluent builder (no telescoping constructors)");
        Car custom = Car.builder()
                .brand("BMW")
                .model("Z4")
                .color("Blue")
                .horsePower(340)
                .build();
        Console.print("custom", custom);

        Console.section("Director + different builders");
        CarDirector director = new CarDirector();

        Car.Builder carBuilder = Car.builder();
        director.constructSportsCar(carBuilder);
        Console.print("sports car", carBuilder.build());

        CarManual.Builder manualBuilder = CarManual.builder();
        director.constructSportsCar(manualBuilder);
        manualBuilder.build().pages().forEach(page -> Console.print("manual page", page));

        Car.Builder familyBuilder = Car.builder();
        director.constructFamilyCar(familyBuilder);
        Console.print("family car", familyBuilder.build());

        Console.section("Validation");
        try {
            Car.builder().model("A4").build();
        } catch (NullPointerException e) {
            Console.print("exception", e.getMessage());
        }
    }
}
