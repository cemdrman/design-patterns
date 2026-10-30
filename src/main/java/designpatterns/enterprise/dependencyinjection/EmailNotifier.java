package designpatterns.enterprise.dependencyinjection;

import designpatterns.common.Console;

/** Production implementation (simulated). */
public class EmailNotifier implements Notifier {

    @Override
    public void send(String to, String message) {
        Console.print("e-mail to " + to, message);
    }
}
