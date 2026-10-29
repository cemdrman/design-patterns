package designpatterns.structural.composite;

/**
 * Component: common interface for both leaves (files) and composites (folders),
 * so the client can treat a single object and a whole tree in the same way.
 */
public sealed interface FileSystemItem permits FileItem, Folder {

    String name();

    long size();

    void print(String indent, StringBuilder out);
}
