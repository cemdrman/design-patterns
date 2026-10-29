package designpatterns.structural.composite;

import java.util.ArrayList;
import java.util.List;

/** Composite: stores children and delegates the work to them. */
public final class Folder implements FileSystemItem {

    private final String name;
    private final List<FileSystemItem> children = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    public Folder add(FileSystemItem item) {
        children.add(item);
        return this;
    }

    public void remove(FileSystemItem item) {
        children.remove(item);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public long size() {
        // Recursion: the folder does not care whether a child is a file or another folder.
        return children.stream().mapToLong(FileSystemItem::size).sum();
    }

    @Override
    public void print(String indent, StringBuilder out) {
        out.append(indent).append("+ ").append(name).append("/ (").append(size()).append(" KB)\n");
        children.forEach(child -> child.print(indent + "    ", out));
    }
}
