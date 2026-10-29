package designpatterns.behavioral.visitor;

import designpatterns.common.Console;

import java.util.List;

public class VisitorDemo {

    public static void main(String[] args) {
        Console.title("Visitor");

        List<Shape> shapes = List.of(new Circle(1), new Rectangle(3, 4), new Triangle(6, 2));
        AreaVisitor area = new AreaVisitor();
        SvgExportVisitor svg = new SvgExportVisitor();

        Console.section("Classic visitor (double dispatch)");
        for (Shape shape : shapes) {
            Console.print(shape.accept(svg), "area = %.2f".formatted(shape.accept(area)));
        }

        Console.section("Modern Java alternative: pattern matching switch");
        for (Shape shape : shapes) {
            Console.print(shape.toString(), perimeter(shape));
        }
    }

    /** Sealed interfaces + exhaustive switch give the same safety without accept() methods. */
    static String perimeter(Shape shape) {
        double value = switch (shape) {
            case Circle c -> 2 * Math.PI * c.radius();
            case Rectangle r -> 2 * (r.width() + r.height());
            case Triangle t -> t.base() + t.height() + Math.hypot(t.base(), t.height());
        };
        return "perimeter = %.2f".formatted(value);
    }
}
