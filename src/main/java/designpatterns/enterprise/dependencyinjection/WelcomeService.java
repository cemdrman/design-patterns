package designpatterns.enterprise.dependencyinjection;

import java.time.Clock;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Client of the dependencies. Everything it needs comes in through the constructor
 * (constructor injection), so the class never calls {@code new EmailNotifier()} or
 * {@code LocalTime.now()} itself and is easy to test.
 */
public class WelcomeService {

    private final Notifier notifier;
    private final Clock clock;

    public WelcomeService(Notifier notifier, Clock clock) {
        this.notifier = Objects.requireNonNull(notifier);
        this.clock = Objects.requireNonNull(clock);
    }

    public String welcome(String email, String name) {
        String message = greeting() + ", " + name + "!";
        notifier.send(email, message);
        return message;
    }

    private String greeting() {
        int hour = LocalTime.now(clock).getHour();
        if (hour < 12) {
            return "Good morning";
        }
        return hour < 18 ? "Good afternoon" : "Good evening";
    }
}
