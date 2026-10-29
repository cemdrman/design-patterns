package designpatterns.behavioral.visitor;

public record Triangle(double base, double height) implements Shape {

    @Override
    public <R> R accept(ShapeVisitor<R> visitor) {
        return visitor.visitTriangle(this);
    }
}
