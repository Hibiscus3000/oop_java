package test.java;

import main.java.ru.nsu.fit.oop.calculator.exception.NotEnoughValuesInStack;
import main.java.ru.nsu.fit.oop.calculator.exception.OperationException;
import main.java.ru.nsu.fit.oop.calculator.exception.WrongNumberOfArgs;
import main.java.ru.nsu.fit.oop.calculator.operations.Average;
import main.java.ru.nsu.fit.oop.calculator.operations.Context;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AverageTest {

    @org.junit.jupiter.api.Test
    void averageOf5Values() {
        var context = new Context();
        var avg = new Average();
        var args = new ArrayList<String>();
        args.add("average");
        args.add("5");
        context.pushToStack(5.5);
        context.pushToStack(6.5);
        context.pushToStack(3);
        context.pushToStack(5);
        context.pushToStack(10);
        assertDoesNotThrow( () -> {
            avg.execute(context,args);
        });
        assertEquals(1,context.getNumberOfValuesInStack());
        assertDoesNotThrow(() -> {
            assertEquals(6,context.popFromStack());
        });
    }

    @org.junit.jupiter.api.Test
    void wrongNumberOfArgs() {
        var context = new Context();
        var avg = new Average();
        var args = new ArrayList<String>();
        args.add("average");
        assertThrows (WrongNumberOfArgs.class,() ->{
            avg.execute(context,args);
        });
    }

    @org.junit.jupiter.api.Test
    void AverageOfMinus2() {
        var context = new Context();
        var avg = new Average();
        var args = new ArrayList<String>();
        args.add("average");
        args.add("-2");
        assertThrows (OperationException.class,() ->{
            avg.execute(context,args);
        });
    }

    @org.junit.jupiter.api.Test
    void notEnoughValuesInStack() {
        var context = new Context();
        var avg = new Average();
        var args = new ArrayList<String>();
        args.add("average");
        args.add("1");
        assertThrows (NotEnoughValuesInStack.class,() ->{
            avg.execute(context,args);
        });
    }
}