package designpatterns.microservice.outbox;

import java.util.ArrayList;
import java.util.List;

/** Simulated Kafka / RabbitMQ. Can be taken down to show that no event gets lost. */
public class MessageBroker {

    private final List<String> published = new ArrayList<>();
    private boolean available = true;

    public void publish(String topic, String payload) {
        if (!available) {
            throw new IllegalStateException("broker unavailable");
        }
        published.add(topic + " " + payload);
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<String> published() {
        return List.copyOf(published);
    }
}
