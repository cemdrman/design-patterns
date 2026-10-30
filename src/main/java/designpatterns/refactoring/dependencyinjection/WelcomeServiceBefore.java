package designpatterns.refactoring.dependencyinjection;

import designpatterns.enterprise.dependencyinjection.EmailNotifier;

import java.time.LocalTime;

/**
 * BEFORE Dependency Injection – the service creates its own dependencies.
 * <p>Tests send real e-mails, the greeting depends on when the test runs, and switching to
 * SMS means editing this class.
 *
 * @see designpatterns.enterprise.dependencyinjection.WelcomeService the refactored version
 */
public class WelcomeServiceBefore {

    private final EmailNotifier notifier = new EmailNotifier();

    public String welcome(String email, String name) {
        int hour = LocalTime.now().getHour();
        String greeting = hour < 12 ? "Good morning" : hour < 18 ? "Good afternoon" : "Good evening";
        String message = greeting + ", " + name + "!";
        notifier.send(email, message);
        return message;
    }
}
