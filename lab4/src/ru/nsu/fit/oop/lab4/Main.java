package ru.nsu.fit.oop.lab4;

import java.util.logging.*;

public class Main {

    public static Logger logger;

    public static void main(String[] args) {
        try {
            LogManager.getLogManager().readConfiguration(
                    Main.class.getResourceAsStream("log.properties"));
            logger = Logger.getLogger(Main.class.getSimpleName());
            logger.setLevel(Level.ALL);
            logger.info("Constructing complex...");
            Complex complex = new Complex();
            logger.info("Complex constructed, complex starts working...");
            complex.start();
            logger.info("Complex started working.");
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Wasn't able to create or start complex!",e);
        }
    }

}

