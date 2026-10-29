package designpatterns.behavioral.visitor;

import java.util.Locale;

/** Concrete visitor: a completely different operation on the same shapes. */
public class SvgExportVisitor implements ShapeVisitor<String> {

    @Override
    public String visitCircle(Circle circle) {
        return String.format(Locale.ROOT, "<circle r=\"%.1f\"/>", circle.radius());
    }

    @Override
    public String visitRectangle(Rectangle rectangle) {
        return String.format(Locale.ROOT, "<rect width=\"%.1f\" height=\"%.1f\"/>", rectangle.width(), rectangle.height());
    }

    @Override
    public String visitTriangle(Triangle triangle) {
        return String.format(Locale.ROOT, "<polygon points=\"0,0 %.1f,0 0,%.1f\"/>", triangle.base(), triangle.height());
    }
}
