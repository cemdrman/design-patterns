package designpatterns.structural;

import designpatterns.structural.composite.FileItem;
import designpatterns.structural.composite.Folder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompositeTest {

    @Test
    void sizeIsCalculatedRecursively() {
        FileItem big = new FileItem("big.bin", 100);
        Folder root = new Folder("root")
                .add(new FileItem("a.txt", 1))
                .add(new Folder("sub").add(new FileItem("b.txt", 2)).add(big));
        assertEquals(103, root.size());

        root.add(new Folder("empty"));
        assertEquals(103, root.size());
    }
}
