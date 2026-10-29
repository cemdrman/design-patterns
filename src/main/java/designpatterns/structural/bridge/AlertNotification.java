package designpatterns.structural.bridge;

/** Refined abstraction. */
public class AlertNotification extends Notification {

    public AlertNotification(MessageSender sender) {
        super(sender);
    }

    @Override
    public String notify(String recipient, String message) {
        return sender.send(recipient, "[ALERT] " + message.toUpperCase());
    }
}
