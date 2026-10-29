package designpatterns.behavioral;

import designpatterns.behavioral.memento.Document;
import designpatterns.behavioral.memento.History;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MementoTest {

    @Test
    void restoresPreviousState() {
        Document document = new Document();
        History history = new History();

        document.write("abc");
        history.push(document.save("first"));
        document.moveCursor(1);
        document.write("XYZ");
        assertEquals("aXYZbc", document.content());

        document.restore(history.pop().orElseThrow());
        assertEquals("abc", document.content());
        assertEquals(3, document.cursor());
        assertTrue(history.pop().isEmpty());
    }
}
