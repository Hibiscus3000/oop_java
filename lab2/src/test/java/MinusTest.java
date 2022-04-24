package test.java;

import main.java.ru.nsu.fit.oop.calculator.exception.NotEnoughValuesInStack;
import main.java.ru.nsu.fit.oop.calculator.operations.Context;
import main.java.ru.nsu.fit.oop.calculator.operations.Minus;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MinusTest {

        @org.junit.jupiter.api.Test
        void twoMinus3Minus100Point25EqualsMinus101Point25() {
            var context = new Context();
            var minus = new Minus();
            var args = new ArrayList<String>();
            context.pushToStack(2);
            context.pushToStack(3);
            assertDoesNotThrow( () -> {
                minus.execute(context,args);
            });
            context.pushToStack(100.25);
            assertDoesNotThrow( () -> {
                minus.execute(context,args);
            });
            assertEquals(-101.25,context.popFromStack());
        }

        @org.junit.jupiter.api.Test
        void notEnoughValuesInStack() {
            var context = new Context();
            var minus = new Minus();
            var args = new ArrayList<String>();
            assertThrows (NotEnoughValuesInStack.class,() ->{
                minus.execute(context,args);
            });
        }
}
