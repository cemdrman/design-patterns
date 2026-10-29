package designpatterns.behavioral.memento;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

/** Caretaker: stores snapshots but never looks inside them. */
public class History {

    private final Deque<Document.Snapshot> snapshots = new ArrayDeque<>();

    public void push(Document.Snapshot snapshot) {
        snapshots.push(snapshot);
    }

    public Optional<Document.Snapshot> pop() {
        return Optional.ofNullable(snapshots.poll());
    }

    public int size() {
        return snapshots.size();
    }
}
