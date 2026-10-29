package designpatterns.behavioral.interpreter;

import designpatterns.common.Console;

import java.util.Map;

public class InterpreterDemo {

    public static void main(String[] args) {
        Console.title("Interpreter");

        Map<String, Integer> context = Map.of("price", 120, "quantity", 3, "discount", 50);

        for (String source : new String[]{"2 3 +", "price quantity *", "price quantity * discount -", "10 2 3 * +"}) {
            Expression expression = ExpressionParser.parse(source);
            Console.print(source, expression.interpret(context));
        }

        Console.section("Abstract syntax tree");
        Console.line(ExpressionParser.parse("price quantity * discount -"));
    }
}
