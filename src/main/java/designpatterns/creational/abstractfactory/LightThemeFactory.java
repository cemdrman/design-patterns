package designpatterns.creational.abstractfactory;

/** Concrete factory producing the light theme family. */
public class LightThemeFactory implements UiFactory {

    @Override
    public Button createButton(String label) {
        return () -> "[ " + label + " ] (white background, black text)";
    }

    @Override
    public Checkbox createCheckbox(String label) {
        return checked -> (checked ? "[x] " : "[ ] ") + label + " (light)";
    }
}
