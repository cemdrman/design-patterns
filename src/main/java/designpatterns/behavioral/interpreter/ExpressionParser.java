package designpatterns.behavioral.interpreter;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Builds the abstract syntax tree from a postfix (Reverse Polish Notation) string,
 * e.g. {@code "price quantity * discount -"}.
 */
public final class ExpressionParser {

    private ExpressionParser() {
    }

    public static Expression parse(String postfix) {
        Deque<Expression> stack = new ArrayDeque<>();
        for (String token : postfix.trim().split("\\s+")) {
            switch (token) {
                case "+" -> {
                    Expression right = stack.pop();
                    stack.push(new Expression.Add(stack.pop(), right));
                }
                case "-" -> {
                    Expression right = stack.pop();
                    stack.push(new Expression.Subtract(stack.pop(), right));
                }
                case "*" -> {
                    Expression right = stack.pop();
                    stack.push(new Expression.Multiply(stack.pop(), right));
                }
                default -> stack.push(token.matches("-?\\d+")
                        ? new Expression.Number(Integer.parseInt(token))
                        : new Expression.Variable(token));
            }
        }
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression: " + postfix);
        }
        return stack.pop();
    }
}
