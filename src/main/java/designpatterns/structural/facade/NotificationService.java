package designpatterns.structural.facade;

import java.util.ArrayList;
import java.util.List;

/** Subsystem class. */
public class NotificationService {

    private final List<String> sent = new ArrayList<>();

    public void sendEmail(String customerId, String message) {
        sent.add(customerId + ": " + message);
    }

    public List<String> sent() {
        return List.copyOf(sent);
    }
}
