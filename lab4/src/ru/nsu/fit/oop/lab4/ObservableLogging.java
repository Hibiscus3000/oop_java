package ru.nsu.fit.oop.lab4;

import ru.nsu.fit.oop.lab4.view.WindowHandler;

import java.io.IOException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ObservableLogging extends Observable implements Logging {

    protected Logger logger;
    private WindowHandler windowHandler;
    public ObservableLogging(String loggerName) throws IOException {
        logger = getLogger(loggerName);
        windowHandler = new WindowHandler(loggerName + " log");
        windowHandler.setLevel(Level.ALL);
        logger.addHandler(windowHandler);
    }

    public ObservableLogging() {

    }

    public void setWindowHandlerVisible(boolean b) {
        windowHandler.setVisible(b);
    }
}
