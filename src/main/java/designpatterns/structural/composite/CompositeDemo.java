package designpatterns.structural.composite;

import designpatterns.common.Console;

public class CompositeDemo {

    public static void main(String[] args) {
        Console.title("Composite");

        Folder project = new Folder("design-patterns")
                .add(new FileItem("README.md", 12))
                .add(new FileItem("pom.xml", 3))
                .add(new Folder("src")
                        .add(new Folder("main")
                                .add(new FileItem("Singleton.java", 2))
                                .add(new FileItem("Builder.java", 4)))
                        .add(new Folder("test")
                                .add(new FileItem("SingletonTest.java", 1))));

        StringBuilder tree = new StringBuilder();
        project.print("", tree);
        Console.line(tree.toString().stripTrailing());

        Console.section("Same call on a leaf and on a composite");
        FileSystemItem leaf = new FileItem("notes.txt", 7);
        Console.print("leaf.size()", leaf.size());
        Console.print("project.size()", project.size());
    }
}
