package designpatterns.behavioral.mediator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Concrete mediator: holds the communication rules in one place
 * (who receives what, muted users, private messages...).
 */
public class ChatRoom implements ChatMediator {

    private final Map<String, User> users = new LinkedHashMap<>();
    private final Set<String> bannedWords = Set.of("spam");

    @Override
    public void join(User user) {
        users.put(user.name(), user);
    }

    @Override
    public void send(String message, User sender) {
        if (isBlocked(message)) {
            return;
        }
        users.values().stream()
                .filter(user -> user != sender)
                .forEach(user -> user.receive(message, sender));
    }

    @Override
    public void sendPrivate(String message, User sender, String recipientName) {
        User recipient = users.get(recipientName);
        if (recipient != null && !isBlocked(message)) {
            recipient.receive("(private) " + message, sender);
        }
    }

    private boolean isBlocked(String message) {
        return bannedWords.stream().anyMatch(message.toLowerCase()::contains);
    }
}
