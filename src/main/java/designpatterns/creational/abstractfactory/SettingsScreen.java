package designpatterns.creational.abstractfactory;

import java.util.List;

/**
 * Client: builds a screen without knowing any concrete theme class.
 */
public class SettingsScreen {

    private final Button saveButton;
    private final Checkbox notifications;

    public SettingsScreen(UiFactory factory) {
        this.saveButton = factory.createButton("Save");
        this.notifications = factory.createCheckbox("Enable notifications");
    }

    public List<String> render() {
        return List.of(notifications.render(true), saveButton.render());
    }
}
