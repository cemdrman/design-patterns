package designpatterns.structural;

import designpatterns.structural.flyweight.TreeType;
import designpatterns.structural.flyweight.TreeTypeFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

class FlyweightTest {

    @Test
    void sharesIdenticalIntrinsicState() {
        TreeType first = TreeTypeFactory.get("Birch", "white", "birch.png");
        TreeType second = TreeTypeFactory.get("Birch", "white", "birch.png");
        assertSame(first, second);
        assertNotSame(first, TreeTypeFactory.get("Birch", "yellow", "birch.png"));
    }
}
