package designpatterns.enterprise.dependencyinjection;

import java.util.ArrayList;
import java.util.List;

/** Test double: remembers the messages instead of sending them. */
public class RecordingNotifier implements Notifier {

    private final List<String> sent = new ArrayList<>();

    @Override
    public void send(String to, String message) {
        sent.add(to + ": " + message);
    }

    public List<String> sent() {
        return List.copyOf(sent);
    }
}
