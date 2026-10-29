package designpatterns.creational;

import designpatterns.creational.abstractfactory.DarkThemeFactory;
import designpatterns.creational.abstractfactory.LightThemeFactory;
import designpatterns.creational.abstractfactory.SettingsScreen;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AbstractFactoryTest {

    @Test
    void everyProductComesFromTheSameFamily() {
        assertTrue(new SettingsScreen(new DarkThemeFactory()).render().stream()
                .allMatch(line -> line.contains("dark") || line.contains("black background")));
        assertTrue(new SettingsScreen(new LightThemeFactory()).render().stream()
                .allMatch(line -> line.contains("light") || line.contains("white background")));
    }
}
