package main.java.ru.nsu.fit.oop.calculator;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        System.setProperty("java.util.logging.config.file","C:/Users/TS/git/oop_java/lab2/logging.properties");
        try {
            LogManager.getLogManager().readConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.setLevel(Level.FINEST);
        logger.entering(Main.class.getName(),"main");
        try {
            BufferedReader commandsReader;
            if (0 == args.length) {
                commandsReader = new BufferedReader(new InputStreamReader(System.in));
            }
            else {
                commandsReader = new BufferedReader(new FileReader(args[0]));
            }
            Calculator calculator = new Calculator();
            calculator.calculate(commandsReader);
            logger.exiting(Main.class.getName(),"main");
        }
        catch (FileNotFoundException exception) {
            logger.log(Level.SEVERE,"Catched exception while creating Buffer reader to read commands",exception);
        }
    }
}
