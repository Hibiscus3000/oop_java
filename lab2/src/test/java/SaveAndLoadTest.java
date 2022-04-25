package test.java;

import main.java.ru.nsu.fit.oop.calculator.operations.Context;
import main.java.ru.nsu.fit.oop.calculator.operations.Load;
import main.java.ru.nsu.fit.oop.calculator.operations.Save;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SaveAndLoadTest {

    @org.junit.jupiter.api.Test
    void saveAndLoad() {
        var context = new Context();
        var load = new Load();
        var save = new Save();
        var args = new ArrayList<String>();
        args.add("save");
        args.add("src\\test\\java\\saveAndLoadTestFile");
        context.pushToStack(-1);
        context.pushToStack(2);
        context.pushToStack(-3.3232);
        assertDoesNotThrow(() ->{
            save.execute(context,args);
        });
        assertEquals(0,context.getNumberOfValuesInStack());
        assertDoesNotThrow(() ->{
            load.execute(context,args);
        });
        assertEquals(3,context.getNumberOfValuesInStack());
        assertDoesNotThrow(() -> {
            assertEquals(-1,context.popFromStack());
        });
        assertDoesNotThrow(() -> {
            assertEquals(2,context.popFromStack());
        });
        assertDoesNotThrow(() -> {
            assertEquals(-3.3232,context.popFromStack());
        });
    }

    @org.junit.jupiter.api.Test
    void loadWithSpaces() {
        var context = new Context();
        var load = new Load();
        var args = new ArrayList<String>();
        args.add("load");
        args.add("src\\test\\java\\loadWithWhitespaces");
        assertDoesNotThrow(() ->{
            load.execute(context,args);
        });
        assertEquals(4,context.getNumberOfValuesInStack());
        assertDoesNotThrow(() -> {
            assertEquals(143.2222,context.popFromStack());
        });
        assertDoesNotThrow(() -> {
            assertEquals(13.45,context.popFromStack());
        });
        assertDoesNotThrow(() -> {
            assertEquals(-25.13, context.popFromStack());
        });
        assertDoesNotThrow(() -> {
            assertEquals(1.25, context.popFromStack());
        });
    }
}