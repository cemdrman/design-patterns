package designpatterns.structural.bridge;

import designpatterns.common.Console;

import java.util.List;

public class BridgeDemo {

    public static void main(String[] args) {
        Console.title("Bridge");

        // 2 abstractions x 2 implementors = 4 combinations with only 4 classes.
        for (MessageSender sender : List.of(new EmailSender(), new SmsSender())) {
            Console.section(sender.getClass().getSimpleName());
            Console.line(new AlertNotification(sender).notify("cem", "server is down"));
            Console.line(new ReminderNotification(sender).notify("cem", "your meeting starts in 10 minutes"));
        }
    }
}
