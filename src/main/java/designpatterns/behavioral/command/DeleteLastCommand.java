package designpatterns.behavioral.command;

/** Concrete command: deletes the last N characters and remembers them for undo. */
public class DeleteLastCommand implements Command {

    private final TextEditor editor;
    private final int count;
    private String removed = "";

    public DeleteLastCommand(TextEditor editor, int count) {
        this.editor = editor;
        this.count = count;
    }

    @Override
    public void execute() {
        int start = Math.max(0, editor.length() - count);
        removed = editor.delete(start, editor.length());
    }

    @Override
    public void undo() {
        editor.insert(editor.length(), removed);
    }

    @Override
    public String name() {
        return "delete last " + count;
    }
}
