package designpatterns.behavioral.command;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Invoker: executes commands and keeps the history needed for undo / redo.
 * It knows nothing about the editor, only about the {@link Command} interface.
 */
public class CommandManager {

    private final Deque<Command> undoStack = new ArrayDeque<>();
    private final Deque<Command> redoStack = new ArrayDeque<>();

    public void execute(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    public boolean undo() {
        if (undoStack.isEmpty()) {
            return false;
        }
        Command command = undoStack.pop();
        command.undo();
        redoStack.push(command);
        return true;
    }

    public boolean redo() {
        if (redoStack.isEmpty()) {
            return false;
        }
        Command command = redoStack.pop();
        command.execute();
        undoStack.push(command);
        return true;
    }
}
