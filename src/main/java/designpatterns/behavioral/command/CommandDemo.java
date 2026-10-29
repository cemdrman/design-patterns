package designpatterns.behavioral.command;

import designpatterns.common.Console;

public class CommandDemo {

    public static void main(String[] args) {
        Console.title("Command");

        TextEditor editor = new TextEditor();
        CommandManager manager = new CommandManager();

        for (Command command : new Command[]{
                new TypeCommand(editor, "Hello"),
                new TypeCommand(editor, " World"),
                new DeleteLastCommand(editor, 5),
                new TypeCommand(editor, "Java")}) {
            manager.execute(command);
            Console.print(command.name(), editor.text());
        }

        Console.section("Undo / Redo");
        manager.undo();
        Console.print("undo", editor.text());
        manager.undo();
        Console.print("undo", editor.text());
        manager.redo();
        Console.print("redo", editor.text());
    }
}
