package test.java;

import main.java.ru.nsu.fit.oop.calculator.exception.NotEnoughValuesInStack;
import main.java.ru.nsu.fit.oop.calculator.operations.Context;
import main.java.ru.nsu.fit.oop.calculator.operations.Divide;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DivideTest {

    @org.junit.jupiter.api.Test
    void OneDivide0Point25DivideMinus4EqualsMinus1() {
        var context = new Context();
        var divide = new Divide();
        var args = new ArrayList<String>();
        context.pushToStack(1);
        context.pushToStack(0.25);
        assertDoesNotThrow( () -> {
            divide.execute(context,args);
        });
        context.pushToStack(-4);
        assertDoesNotThrow( () -> {
            divide.execute(context,args);
        });
        assertEquals(-1,context.popFromStack());
    }

    @org.junit.jupiter.api.Test
    void notEnoughValuesInStack() {
        var context = new Context();
        var divide = new Divide();
        var args = new ArrayList<String>();
        assertThrows (NotEnoughValuesInStack.class,() ->{
            divide.execute(context,args);
        });
    }
}