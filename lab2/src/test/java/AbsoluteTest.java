package test.java;

import main.java.ru.nsu.fit.oop.calculator.exception.NotEnoughValuesInStack;
import main.java.ru.nsu.fit.oop.calculator.operations.Context;
import main.java.ru.nsu.fit.oop.calculator.operations.Absolute;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AbsoluteTest {

    @org.junit.jupiter.api.Test
    void absolute2AndMinus2() {
        var context = new Context();
        var abs = new Absolute();
        var args = new ArrayList<String>();
        context.pushToStack(2);
        assertDoesNotThrow( () -> {
            abs.execute(context,args);
        });
        assertDoesNotThrow(() -> {
            assertEquals(2,context.popFromStack());
        });
        context.pushToStack(-2);
        assertDoesNotThrow( () -> {
            abs.execute(context,args);
        });
        assertDoesNotThrow(() -> {
            assertEquals(2,context.popFromStack());
        });
    }

    @org.junit.jupiter.api.Test
    void notEnoughValuesInStack() {
        var context = new Context();
        var abs = new Absolute();
        var args = new ArrayList<String>();
        assertThrows (NotEnoughValuesInStack.class,() ->{
            abs.execute(context,args);
        });
    }
}