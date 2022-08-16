package ru.nsu.fit.oop.lab4;

import ru.nsu.fit.oop.lab4.exception.ReusedGoodException;
import ru.nsu.fit.oop.lab4.good.Good;
import ru.nsu.fit.oop.lab4.good.Storage;
import ru.nsu.fit.oop.lab4.logging.ObservableLogging;

import java.io.IOException;
import java.util.logging.Level;

import static ru.nsu.fit.oop.lab4.Util.*;

public class Consumer extends ObservableLogging implements Runnable {

    private final Storage storage;
    private final int id;
    private int numberOfGoodsConsumed = 0;
    private final int logUpdationFreq = 20;

    public Consumer(Storage storage, int id) throws IOException {
        super(Consumer.class.getPackageName() + '.' +
                eraseWhitespaces(toUpperFirstChar(storage.getGoodName())) + Consumer.class.getSimpleName() + id,
                toUpperFirstChar(eraseWhitespaces(storage.getGoodName())) + Consumer.class.getSimpleName() + id,
                storage.getGoodName() + " consumer #" + id + " log");
        this.storage = storage;
        this.id = id;
        logger.config("Consumer #" + id + ", interested in " + getGoodName() + ", created.");
    }

    public String getGoodName() {
        return storage.getGoodName();
    }

    public int getId() {
        return id;
    }

    public int getNumberOfGoodsConsumed() {
        return numberOfGoodsConsumed;
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
                setChanged();
                notifyObservers();
                logger.config("Consumer #" + id + " consumed " + getGoodName() + " #" + good.getId() + ".");
                if (0 == (numberOfGoodsConsumed % logUpdationFreq)) {
                    logger.info("Consumer #" + id + " consumed " + numberOfGoodsConsumed + " " + getGoodName() + ".");
                }
            }
        } catch (InterruptedException e) {
            logger.warning("Consumer interested in " + getGoodName() + " was interrupted.");
            logFinalInfo();
        } catch (ReusedGoodException e) {
            logger.log(Level.SEVERE, "Trying to reuse " + getGoodName(), e);
        }
    }

    @Override
    public void logFinalInfo() {
        logger.info("Consumer #" + id + " used " + numberOfGoodsConsumed
                + " units of " + getGoodName() + ".");
    }
}


