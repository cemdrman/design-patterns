package designpatterns.behavioral.memento;

import designpatterns.common.Console;

public class MementoDemo {

    public static void main(String[] args) {
        Console.title("Memento");

        Document document = new Document();
        History history = new History();

        document.write("Design patterns");
        history.push(document.save("v1"));
        Console.print("v1", document.content());

        document.write(" are useful");
        history.push(document.save("v2"));
        Console.print("v2", document.content());

        document.moveCursor(0);
        document.write("Oops! ");
        Console.print("after mistake", document.content());

        Console.section("Restoring");
        history.pop().ifPresent(snapshot -> {
            document.restore(snapshot);
            Console.print("restored " + snapshot.label(), document.content());
        });
        history.pop().ifPresent(snapshot -> {
            document.restore(snapshot);
            Console.print("restored " + snapshot.label(), document.content());
        });
        Console.print("cursor", document.cursor());
    }
}
