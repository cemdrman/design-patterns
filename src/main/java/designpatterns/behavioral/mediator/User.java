package designpatterns.behavioral.mediator;

import java.util.ArrayList;
import java.util.List;

/** Colleague: knows only the mediator, not the other users. */
public class User {

    private final String name;
    private final ChatMediator mediator;
    private final List<String> inbox = new ArrayList<>();

    public User(String name, ChatMediator mediator) {
        this.name = name;
        this.mediator = mediator;
        mediator.join(this);
    }

    public String name() {
        return name;
    }

    public void send(String message) {
        mediator.send(message, this);
    }

    public void sendPrivate(String recipient, String message) {
        mediator.sendPrivate(message, this, recipient);
    }

    void receive(String message, User from) {
        inbox.add(from.name + ": " + message);
    }

    public List<String> inbox() {
        return List.copyOf(inbox);
    }
}
