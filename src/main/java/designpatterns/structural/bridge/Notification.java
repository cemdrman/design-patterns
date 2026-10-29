package designpatterns.structural.bridge;

/**
 * Abstraction: the "what" side of the bridge. It holds a reference to a
 * {@link MessageSender} instead of extending it, so both hierarchies can grow independently.
 * Without the bridge we would need AlertEmail, AlertSms, ReminderEmail, ReminderSms...
 */
public abstract class Notification {

    protected final MessageSender sender;

    protected Notification(MessageSender sender) {
        this.sender = sender;
    }

    public abstract String notify(String recipient, String message);
}
