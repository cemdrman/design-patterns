package designpatterns.creational.abstractfactory;

/**
 * Abstract factory: creates a whole <b>family</b> of related products.
 * Every product created by the same factory is guaranteed to match each other
 * (e.g. a dark button is never mixed with a light checkbox).
 */
public interface UiFactory {

    Button createButton(String label);

    Checkbox createCheckbox(String label);
}
