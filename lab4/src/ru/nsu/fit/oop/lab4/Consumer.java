package ru.nsu.fit.oop.lab4;

import ru.nsu.fit.oop.lab4.exception.ReusedGoodException;
import ru.nsu.fit.oop.lab4.goods.Good;
import ru.nsu.fit.oop.lab4.goods.Storage;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer implements Runnable, Logging {

    private final Storage storage;
    private final int id;
    private final Logger logger;
    private int numberOfGoodsConsumed = 0;

    public Consumer(Storage storage, int id) throws IOException {
        this.storage = storage;
        this.id = id;
        logger = Logger.getLogger(this.getClass().getSimpleName() + id);
        FileHandler fileHandler = new FileHandler(getGoodName() + "Consumer" + id + "_log%g.txt",
                1000000,1,false);
        fileHandler.setLevel(Level.ALL);
        logger.addHandler(fileHandler);
        logger.setLevel(Level.ALL);
        logger.config("Consumer #" + id + ", interested in " + getGoodName() + " created.");
    }

    public String getGoodName() {
        return storage.getGoodName();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Good good = storage.getGood();
                logger.config("Consumer #" + id + " got " + getGoodName() + " #" +
                        good.getId() + ", going to consume it...");
                good.consume();
                ++numberOfGoodsConsumed;
                logger.info("Consumer consumed " + getGoodName() + " #" + good.getId() + ".");
            }
        } catch (InterruptedException e) {
            logger.warning("Consumer interested in " + getGoodName() + " was interrupted.");
            logFinalInfo();
        } catch (ReusedGoodException e) {
            logger.log(Level.SEVERE,"Trying to reuse good",e);
        }
    }

    @Override
    public void logFinalInfo() {
        logger.info("Consumer #" + id + " used " + numberOfGoodsConsumed
        + " units of " + getGoodName() + ".");
    }
}


