package designpatterns.behavioral.visitor;

public record Circle(double radius) implements Shape {

    @Override
    public <R> R accept(ShapeVisitor<R> visitor) {
        return visitor.visitCircle(this);
    }
}
