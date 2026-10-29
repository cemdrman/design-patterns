package designpatterns.behavioral;

import designpatterns.behavioral.visitor.AreaVisitor;
import designpatterns.behavioral.visitor.Circle;
import designpatterns.behavioral.visitor.Rectangle;
import designpatterns.behavioral.visitor.SvgExportVisitor;
import designpatterns.behavioral.visitor.Triangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VisitorTest {

    @Test
    void visitorsAddOperationsWithoutChangingShapes() {
        AreaVisitor area = new AreaVisitor();
        assertEquals(Math.PI, new Circle(1).accept(area), 1e-9);
        assertEquals(12, new Rectangle(3, 4).accept(area), 1e-9);
        assertEquals(6, new Triangle(6, 2).accept(area), 1e-9);
        assertEquals("<rect width=\"3.0\" height=\"4.0\"/>", new Rectangle(3, 4).accept(new SvgExportVisitor()));
    }
}
