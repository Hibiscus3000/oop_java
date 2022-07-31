package ru.nsu.fit.oop.lab4;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    public static Logger logger;

    public static void main(String[] args) {
        try {
            LogManager.getLogManager().readConfiguration(
                    Main.class.getResourceAsStream("log.properties"));
            logger = Logger.getLogger("");
            logger.setLevel(Level.ALL);
            FileHandler fileHandler = new FileHandler("log_all%g.txt", 1000000, 1, false);
            fileHandler.setLevel(Level.ALL);
            logger.addHandler(fileHandler);
            logger.info("Constructing complex...");
            Complex complex = new Complex();
            logger.info("Complex constructed, complex starts working...");
            complex.start();
            logger.info("Complex started working.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Wasn't able to create or start complex!", e);
        }
    }

}

