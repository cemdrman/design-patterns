package designpatterns.structural.bridge;

/** Concrete implementor. */
public class SmsSender implements MessageSender {

    @Override
    public String send(String recipient, String text) {
        String shortText = text.length() > 30 ? text.substring(0, 27) + "..." : text;
        return "SMS to " + recipient + ": " + shortText;
    }
}
