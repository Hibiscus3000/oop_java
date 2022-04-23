package ru.nsu.fit.oop.calculator;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader commandsReader;
            if (1 == args.length) {
                commandsReader = new BufferedReader(new InputStreamReader(System.in));
            }
            else {
                commandsReader = new BufferedReader(new FileReader(args[0]));
            }
            Calculator calculator = new Calculator();
            calculator.calculate(commandsReader);
        }
        catch (FileNotFoundException exception) {
            exception.printStackTrace(System.err);
        }
    }
}
