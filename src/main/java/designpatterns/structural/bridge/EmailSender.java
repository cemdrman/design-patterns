package designpatterns.structural.bridge;

/** Concrete implementor. */
public class EmailSender implements MessageSender {

    @Override
    public String send(String recipient, String text) {
        return "EMAIL to " + recipient + "@mail.com: " + text;
    }
}
