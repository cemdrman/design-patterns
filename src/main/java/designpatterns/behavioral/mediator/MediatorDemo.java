package designpatterns.behavioral.mediator;

import designpatterns.common.Console;

import java.util.List;

public class MediatorDemo {

    public static void main(String[] args) {
        Console.title("Mediator");

        ChatMediator room = new ChatRoom();
        User cem = new User("Cem", room);
        User ayse = new User("Ayşe", room);
        User mehmet = new User("Mehmet", room);

        cem.send("Hello everyone!");
        ayse.send("Hi Cem");
        mehmet.send("Buy cheap SPAM now");
        mehmet.sendPrivate("Cem", "Lunch at 12?");

        for (User user : List.of(cem, ayse, mehmet)) {
            Console.print(user.name() + "'s inbox", user.inbox());
        }
    }
}
