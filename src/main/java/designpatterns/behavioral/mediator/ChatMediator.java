package designpatterns.behavioral.mediator;

/** Mediator: colleagues talk to the mediator instead of talking to each other directly. */
public interface ChatMediator {

    void join(User user);

    void send(String message, User sender);

    void sendPrivate(String message, User sender, String recipientName);
}
