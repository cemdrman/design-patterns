package designpatterns.behavioral.command;

/** Concrete command: appends text to the end. */
public class TypeCommand implements Command {

    private final TextEditor editor;
    private final String value;
    private int position;

    public TypeCommand(TextEditor editor, String value) {
        this.editor = editor;
        this.value = value;
    }

    @Override
    public void execute() {
        position = editor.length();
        editor.insert(position, value);
    }

    @Override
    public void undo() {
        editor.delete(position, position + value.length());
    }

    @Override
    public String name() {
        return "type \"" + value + "\"";
    }
}
