package designpatterns.structural.flyweight;

import designpatterns.common.Console;

import java.util.random.RandomGenerator;

public class FlyweightDemo {

    public static void main(String[] args) {
        Console.title("Flyweight");

        String[][] types = {
                {"Oak", "dark green", "oak.png"},
                {"Pine", "green", "pine.png"},
                {"Cherry", "pink", "cherry.png"}
        };

        Forest forest = new Forest();
        RandomGenerator random = RandomGenerator.of("L64X128MixRandom");
        for (int i = 0; i < 100_000; i++) {
            String[] type = types[i % types.length];
            forest.plant(random.nextInt(1000), random.nextInt(1000), type[0], type[1], type[2]);
        }

        Console.print("trees planted", forest.trees().size());
        Console.print("TreeType objects in memory", TreeTypeFactory.cachedTypes());
        Console.print("first tree", forest.trees().getFirst().draw());

        Console.section("The JDK uses flyweights too");
        Console.print("Integer.valueOf(127) == valueOf(127)", Integer.valueOf(127) == Integer.valueOf(127));
    }
}
