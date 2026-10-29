package designpatterns.behavioral.command;

/** Receiver: the object that really knows how to do the work. */
public class TextEditor {

    private final StringBuilder text = new StringBuilder();

    public void insert(int position, String value) {
        text.insert(position, value);
    }

    public String delete(int start, int end) {
        String removed = text.substring(start, end);
        text.delete(start, end);
        return removed;
    }

    public int length() {
        return text.length();
    }

    public String text() {
        return text.toString();
    }
}
