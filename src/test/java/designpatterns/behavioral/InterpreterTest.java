package designpatterns.behavioral;

import designpatterns.behavioral.interpreter.ExpressionParser;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InterpreterTest {

    @Test
    void evaluatesPostfixExpressions() {
        Map<String, Integer> context = Map.of("x", 4, "y", 3);
        assertEquals(5, ExpressionParser.parse("2 3 +").interpret(context));
        assertEquals(1, ExpressionParser.parse("x y -").interpret(context));
        assertEquals(28, ExpressionParser.parse("2 x y * + 2 *").interpret(context));
    }

    @Test
    void rejectsInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> ExpressionParser.parse("1 2"));
        assertThrows(IllegalArgumentException.class, () -> ExpressionParser.parse("z").interpret(Map.of()));
    }
}
