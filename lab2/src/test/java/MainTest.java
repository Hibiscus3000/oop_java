package test.java;

import main.java.ru.nsu.fit.oop.calculator.Main;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @org.junit.jupiter.api.Test
    void mainTest() {
        var main = new Main();
        String[] args = new String[1];
        args[0] = "src\\test\\java\\mainTestFile";
        assertDoesNotThrow( () ->{
            main.main(args);
        });
    }
}