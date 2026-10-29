package designpatterns.creational.abstractfactory;

import designpatterns.common.Console;

public class AbstractFactoryDemo {

    public static void main(String[] args) {
        Console.title("Abstract Factory");

        for (String theme : new String[]{"light", "dark"}) {
            Console.section(theme + " theme");
            // The concrete factory is chosen only once, usually from configuration.
            UiFactory factory = switch (theme) {
                case "dark" -> new DarkThemeFactory();
                default -> new LightThemeFactory();
            };
            new SettingsScreen(factory).render().forEach(Console::line);
        }
    }
}
