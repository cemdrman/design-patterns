package designpatterns.behavioral.memento;

/**
 * Originator: creates snapshots of its own state and restores from them.
 * The snapshot class is nested so that only the document can read its content
 * (encapsulation is not broken).
 */
public class Document {

    private String content = "";
    private int cursor;

    public void write(String text) {
        content = content.substring(0, cursor) + text + content.substring(cursor);
        cursor += text.length();
    }

    public void moveCursor(int position) {
        cursor = Math.clamp(position, 0, content.length());
    }

    public String content() {
        return content;
    }

    public int cursor() {
        return cursor;
    }

    public Snapshot save(String label) {
        return new Snapshot(label, content, cursor);
    }

    public void restore(Snapshot snapshot) {
        this.content = snapshot.content;
        this.cursor = snapshot.cursor;
    }

    /** Memento: immutable and opaque to everyone except {@link Document}. */
    public static final class Snapshot {

        private final String label;
        private final String content;
        private final int cursor;

        private Snapshot(String label, String content, int cursor) {
            this.label = label;
            this.content = content;
            this.cursor = cursor;
        }

        public String label() {
            return label;
        }
    }
}
