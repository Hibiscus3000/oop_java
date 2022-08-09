package ru.nsu.fit.oop.lab4;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface Logging {

    default public Logger getLogger(String loggerName)
            throws IOException {
        Logger logger = Logger.getLogger(loggerName);
        logger.setLevel(Level.ALL);
        FileHandler fileHandler = new FileHandler("logs/" + loggerName + "_log%g.txt",
                1000000, 1, false);
        fileHandler.setLevel(Level.ALL);
        logger.addHandler(fileHandler);
        return logger;
    }

    void logFinalInfo();

}
