package designpatterns.creational.abstractfactory;

/** Concrete factory producing the dark theme family. */
public class DarkThemeFactory implements UiFactory {

    @Override
    public Button createButton(String label) {
        return () -> "< " + label + " > (black background, white text)";
    }

    @Override
    public Checkbox createCheckbox(String label) {
        return checked -> (checked ? "(*) " : "( ) ") + label + " (dark)";
    }
}
