package ru.nsu.fit.oop.lab4.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface Logging {

    default public Logger getLogger(String loggerName, String pattern)
            throws IOException {
        Logger logger = Logger.getLogger(loggerName);
        logger.setLevel(Level.ALL);
        FileHandler fileHandler = new FileHandler("logs/" + pattern + "_log%g.txt",
                1000000, 1, false);
        fileHandler.setLevel(Level.ALL);
        logger.addHandler(fileHandler);
        return logger;
    }

    void logFinalInfo();

}
