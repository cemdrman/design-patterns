package designpatterns.behavioral;

import designpatterns.behavioral.command.CommandManager;
import designpatterns.behavioral.command.DeleteLastCommand;
import designpatterns.behavioral.command.TextEditor;
import designpatterns.behavioral.command.TypeCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CommandTest {

    @Test
    void undoAndRedoRestoreTheText() {
        TextEditor editor = new TextEditor();
        CommandManager manager = new CommandManager();
        manager.execute(new TypeCommand(editor, "abc"));
        manager.execute(new DeleteLastCommand(editor, 2));
        assertEquals("a", editor.text());

        manager.undo();
        assertEquals("abc", editor.text());
        manager.undo();
        assertEquals("", editor.text());
        assertFalse(manager.undo());

        manager.redo();
        assertEquals("abc", editor.text());
    }
}
