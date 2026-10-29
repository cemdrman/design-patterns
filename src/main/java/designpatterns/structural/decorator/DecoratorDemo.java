package designpatterns.structural.decorator;

import designpatterns.common.Console;

public class DecoratorDemo {

    public static void main(String[] args) {
        Console.title("Decorator");

        Coffee plain = new Espresso();
        Coffee latte = new Milk(new Espresso());
        Coffee special = new WhippedCream(new Caramel(new Milk(new ExtraShot(new FilterCoffee()))));

        for (Coffee coffee : new Coffee[]{plain, latte, special}) {
            Console.print(coffee.description(), coffee.cost() + " TL");
        }

        Console.section("The JDK does the same with I/O streams");
        Console.line("new BufferedReader(new InputStreamReader(new FileInputStream(file)))");
    }
}
