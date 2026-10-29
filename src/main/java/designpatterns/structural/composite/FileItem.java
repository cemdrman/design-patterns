package designpatterns.structural.composite;

/** Leaf: has no children. */
public record FileItem(String name, long size) implements FileSystemItem {

    @Override
    public void print(String indent, StringBuilder out) {
        out.append(indent).append("- ").append(name).append(" (").append(size).append(" KB)\n");
    }
}
