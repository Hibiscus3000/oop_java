package ru.nsu.fit.oop.lab4;

import ru.nsu.fit.oop.lab4.view.ComplexFrame;

import java.awt.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    public static Logger logger;

    public static void main(String[] args) {
        try {
            Util.clearLogsDirectory();
            LogManager.getLogManager().readConfiguration(
                    Main.class.getResourceAsStream("log.properties"));
            logger = Logger.getLogger("ru.nsu.fit.oop.lab4");
            logger.setLevel(Level.ALL);
            FileHandler fileHandler = new FileHandler("logs/Log_all%g.txt", 1000000, 1, false);
            fileHandler.setLevel(Level.ALL);
            logger.addHandler(fileHandler);
            EventQueue.invokeLater(() -> {
                ComplexFrame complexFrame = new ComplexFrame(logger);
                complexFrame.setVisible(true);
            });
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Wasn't able to create main logger!", e);
        }
    }

}

