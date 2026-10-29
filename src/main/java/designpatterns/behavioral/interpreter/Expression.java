package designpatterns.behavioral.interpreter;

import java.util.Map;

/**
 * Abstract expression of a tiny arithmetic language. Every grammar rule is a class:
 * <pre>
 * expression ::= number | variable | expression expression operator
 * operator   ::= '+' | '-' | '*'
 * </pre>
 * Terminal expressions: {@link Number}, {@link Variable}.
 * Non-terminal expressions: {@link Add}, {@link Subtract}, {@link Multiply}.
 */
public sealed interface Expression {

    int interpret(Map<String, Integer> context);

    record Number(int value) implements Expression {
        @Override
        public int interpret(Map<String, Integer> context) {
            return value;
        }
    }

    record Variable(String name) implements Expression {
        @Override
        public int interpret(Map<String, Integer> context) {
            Integer value = context.get(name);
            if (value == null) {
                throw new IllegalArgumentException("Unknown variable: " + name);
            }
            return value;
        }
    }

    record Add(Expression left, Expression right) implements Expression {
        @Override
        public int interpret(Map<String, Integer> context) {
            return left.interpret(context) + right.interpret(context);
        }
    }

    record Subtract(Expression left, Expression right) implements Expression {
        @Override
        public int interpret(Map<String, Integer> context) {
            return left.interpret(context) - right.interpret(context);
        }
    }

    record Multiply(Expression left, Expression right) implements Expression {
        @Override
        public int interpret(Map<String, Integer> context) {
            return left.interpret(context) * right.interpret(context);
        }
    }
}
