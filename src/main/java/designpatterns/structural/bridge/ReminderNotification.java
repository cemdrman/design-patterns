package designpatterns.structural.bridge;

/** Refined abstraction. */
public class ReminderNotification extends Notification {

    public ReminderNotification(MessageSender sender) {
        super(sender);
    }

    @Override
    public String notify(String recipient, String message) {
        return sender.send(recipient, "Friendly reminder: " + message);
    }
}
