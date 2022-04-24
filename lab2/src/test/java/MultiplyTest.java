package test.java;

import main.java.ru.nsu.fit.oop.calculator.exception.NotEnoughValuesInStack;
import main.java.ru.nsu.fit.oop.calculator.operations.Context;
import main.java.ru.nsu.fit.oop.calculator.operations.Multiply;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MultiplyTest {

    @org.junit.jupiter.api.Test
    void twoMulMinus3Mul10Point5EqualsMinus63() {
        var context = new Context();
        var multiply = new Multiply();
        var args = new ArrayList<String>();
        context.pushToStack(2);
        context.pushToStack(-3);
        context.pushToStack(10.5);
        assertDoesNotThrow( () -> {
            multiply.execute(context,args);
        });
        assertDoesNotThrow( () -> {
            multiply.execute(context,args);
        });
        assertEquals(-63,context.popFromStack());
    }

    @org.junit.jupiter.api.Test
    void notEnoughValuesInStack() {
        var context = new Context();
        var multiply = new Multiply();
        var args = new ArrayList<String>();
        assertThrows (NotEnoughValuesInStack.class,() ->{
            multiply.execute(context,args);
        });
    }
}