package designpatterns.behavioral.visitor;

/** Concrete visitor: calculates the area. */
public class AreaVisitor implements ShapeVisitor<Double> {

    @Override
    public Double visitCircle(Circle circle) {
        return Math.PI * circle.radius() * circle.radius();
    }

    @Override
    public Double visitRectangle(Rectangle rectangle) {
        return rectangle.width() * rectangle.height();
    }

    @Override
    public Double visitTriangle(Triangle triangle) {
        return triangle.base() * triangle.height() / 2;
    }
}
