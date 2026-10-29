package designpatterns.behavioral.visitor;

/** Visitor: one method per concrete element type. */
public interface ShapeVisitor<R> {

    R visitCircle(Circle circle);

    R visitRectangle(Rectangle rectangle);

    R visitTriangle(Triangle triangle);
}
