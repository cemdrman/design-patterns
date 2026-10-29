package designpatterns.structural.bridge;

/** Implementor: the "how" side of the bridge. */
public interface MessageSender {

    String send(String recipient, String text);
}
