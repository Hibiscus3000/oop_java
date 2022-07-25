package ru.nsu.fit.oop.lab4;

import ru.nsu.fit.oop.lab4.exception.UnsuccessfulLoggerCreation;
import ru.nsu.fit.oop.lab4.goods.Good;
import ru.nsu.fit.oop.lab4.goods.Storage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Consumer implements Runnable {

    private final Storage storage;
    private final int id;
    private final Logger logger;

    public Consumer(Storage storage, int id) throws UnsuccessfulLoggerCreation {
        try {
            LogManager.getLogManager().readConfiguration(this.getClass().getResourceAsStream
                    ("consumer_log.properties"));
        } catch (IOException e) {
            Main.logger.severe("Wasn't able to create logger for train");
            throw new UnsuccessfulLoggerCreation("consumer", e);
        }
        logger = Logger.getLogger(this.getClass().getSimpleName());
        logger.setLevel(Level.ALL);
        this.storage = storage;
        this.id = id;
    }

    public String getGoodName() {
        return storage.getGoodName();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Good good = storage.getGood();
                logger.info("Consumer got " + getGoodName() + ", going to consume it...");
                good.consume();
                logger.info("Consumer consumed " + getGoodName() + ".");
            }
        } catch (InterruptedException e) {
            logger.info("Consumer interested in " + getGoodName() + " was interrupted.");
            // DO SMT!!!
        }
    }
}
