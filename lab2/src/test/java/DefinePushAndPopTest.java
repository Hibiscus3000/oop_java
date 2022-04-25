package test.java;

import main.java.ru.nsu.fit.oop.calculator.exception.*;
import main.java.ru.nsu.fit.oop.calculator.operations.Context;
import main.java.ru.nsu.fit.oop.calculator.operations.Define;
import main.java.ru.nsu.fit.oop.calculator.operations.Pop;
import main.java.ru.nsu.fit.oop.calculator.operations.Push;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DefinePushAndPopTest {

    @org.junit.jupiter.api.Test
    void pushThreePopThree() {
        var context = new Context();
        var args = new ArrayList<String>();
        var push = new Push();
        var pop = new Pop();
        assertEquals(0,context.getNumberOfValuesInStack());
        args.add("push");
        args.add("-2.5");
        assertDoesNotThrow(() -> {
            push.execute(context,args);
                });
        assertEquals(1,context.getNumberOfValuesInStack());
        assertDoesNotThrow( () -> {
            assertEquals(-2.5, context.getFromStack());
        });
        assertDoesNotThrow(() -> {
            pop.execute(context,args);
        });
        args.set(1,"7.25");
        assertDoesNotThrow(() -> {
            push.execute(context,args);
        });
        assertEquals(1,context.getNumberOfValuesInStack());
        assertDoesNotThrow( () -> {
            assertEquals(7.25, context.getFromStack());
        });
        args.set(1,"6");
        assertDoesNotThrow(() -> {
            push.execute(context,args);
        });
        assertEquals(2,context.getNumberOfValuesInStack());
        assertDoesNotThrow( () -> {
            assertEquals(6, context.getFromStack());
        });
        assertDoesNotThrow(() -> {
            pop.execute(context,args);
        });
        assertEquals(1,context.getNumberOfValuesInStack());
        assertDoesNotThrow( () -> {
            assertEquals(7.25, context.getFromStack());
        });
        assertDoesNotThrow(() -> {
            pop.execute(context,args);
        });
        assertEquals(0,context.getNumberOfValuesInStack());
    }

    @org.junit.jupiter.api.Test
    void wrongNumberOfArgs() {
        var context = new Context();
        var args = new ArrayList<String>();
        var push = new Push();
        args.add("push");
        assertThrows(WrongNumberOfArgs.class, () -> {
            push.execute(context,args);
        });
    }

    @org.junit.jupiter.api.Test
    void notEnoughValuesInStack() {
        var context = new Context();
        var args = new ArrayList<String>();
        var pop = new Pop();
        assertThrows(NotEnoughValuesInStack.class, () -> {
            pop.execute(context,args);
        });
    }

    @org.junit.jupiter.api.Test
    void inappropriateArgForPush() {
        var context = new Context();
        var args = new ArrayList<String>();
        var push = new Push();
        args.add("push");
        args.add("3f");
        assertThrows(InappropriateArgException.class, () -> {
            push.execute(context,args);
        });
    }

    @org.junit.jupiter.api.Test
    void noDefinedParameterWithGivenName() {
        var context = new Context();
        var args = new ArrayList<String>();
        var push = new Push();
        args.add("push");
        args.add("f3");
        assertThrows(NoDefinedParamWithGivenName.class, () ->{
            push.execute(context,args);
        });
    }

    @org.junit.jupiter.api.Test
    void defineAndPushNamedParam() {
        var context = new Context();
        var args = new ArrayList<String>();
        var push = new Push();
        var define = new Define();
        args.add("define");
        args.add("f3-....");
        args.add("2.5");
        assertDoesNotThrow(() -> {
            define.execute(context,args);
        });
        args.remove(2);
        assertDoesNotThrow(() -> {
            push.execute(context,args);
        });
        assertDoesNotThrow( () -> {
            assertEquals(2.5,context.getFromStack());
        });

    }

    @org.junit.jupiter.api.Test
    void inappropriateParameterName() {
        var context = new Context();
        var args = new ArrayList<String>();
        var define = new Define();
        args.add("define");
        args.add("3f");
        args.add("2.5");
        assertThrows(InappropriateParameterName.class, () -> {
           define.execute(context,args);
        });
        args.set(1,"-f");
        assertThrows(InappropriateParameterName.class, () -> {
            define.execute(context,args);
        });

    }

    @org.junit.jupiter.api.Test
    void inappropriateArgForDefine() {
        var context = new Context();
        var args = new ArrayList<String>();
        var define = new Define();
        args.add("define");
        args.add("f");
        args.add("2.5.");
        assertThrows(InappropriateArgException.class, () -> {
            define.execute(context,args);
        });
    }
}
