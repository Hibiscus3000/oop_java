package test.java;

import main.java.ru.nsu.fit.oop.calculator.exception.NotEnoughValuesInStack;
import main.java.ru.nsu.fit.oop.calculator.operations.Context;
import main.java.ru.nsu.fit.oop.calculator.operations.Plus;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlusTest {

    @org.junit.jupiter.api.Test
    void twoPlus3Plus10Equals15() {
        var context = new Context();
        var plus = new Plus();
        var args = new ArrayList<String>();
        context.pushToStack(2);
        context.pushToStack(3);
        context.pushToStack(10);
        assertDoesNotThrow( () -> {
                plus.execute(context,args);
        });
        assertDoesNotThrow( () -> {
            plus.execute(context,args);
        });
        assertEquals(15,context.popFromStack());
    }

    @org.junit.jupiter.api.Test
    void notEnoughValuesInStack() {
        var context = new Context();
        var plus = new Plus();
        var args = new ArrayList<String>();
        assertThrows (NotEnoughValuesInStack.class,() ->{
            plus.execute(context,args);
        });
    }
}