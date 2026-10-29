package designpatterns.behavioral.state;

import designpatterns.common.Console;

public class StateDemo {

    public static void main(String[] args) {
        Console.title("State");

        Order order = new Order();
        Console.print("initial state", order.state().name());
        order.pay();
        Console.print("after pay()", order.state().name());
        order.ship();
        Console.print("after ship()", order.state().name());

        Console.section("Invalid transition");
        try {
            order.cancel();
        } catch (IllegalStateException e) {
            Console.print("cancel()", e.getMessage());
        }

        order.deliver();
        Console.print("after deliver()", order.state().name());
        Console.print("history", order.history());
    }
}
