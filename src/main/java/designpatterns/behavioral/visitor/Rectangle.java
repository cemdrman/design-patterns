package designpatterns.behavioral.visitor;

public record Rectangle(double width, double height) implements Shape {

    @Override
    public <R> R accept(ShapeVisitor<R> visitor) {
        return visitor.visitRectangle(this);
    }
}
