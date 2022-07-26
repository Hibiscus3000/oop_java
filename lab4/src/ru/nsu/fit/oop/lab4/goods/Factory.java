package ru.nsu.fit.oop.lab4.goods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Factory implements Runnable {

    private final String goodName;
    private final int goodNumber;
    private final int productionTimeMillis;
    private final int consumptionTimeMillis;
    private final int loadingTimeMillis;
    private final int unloadingTimeMillis;
    private final Storage storage;
    private final int numberOfIds = 10000;
    private List<Integer> ids;
    private final Logger logger;

    public Factory(String goodName, int productionTimeMillis, int consumptionTimeMillis, int loadingTimeMillis,
                   int unloadingTimeMillis, Storage storage, int goodNumber) {
        logger = Logger.getLogger(this.getClass().getSimpleName());
        logger.setLevel(Level.ALL);
        this.goodNumber = goodNumber;
        this.goodName = goodName;
        this.productionTimeMillis = productionTimeMillis;
        this.consumptionTimeMillis = consumptionTimeMillis;
        this.loadingTimeMillis = loadingTimeMillis;
        this.unloadingTimeMillis = unloadingTimeMillis;
        this.storage = storage;
        makeIDs();
        logger.info(goodName + " factory created.");
    }

    public String getGoodName() {
        return goodName;
    }

    private void makeIDs() {
        ids = IntStream.range(numberOfIds * goodNumber + 1, numberOfIds * (goodNumber + 1) + 1).boxed()
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(ids);
    }

    @Override
    public void run() {
        try {
            while (true) {
                logger.config("Factory producing " + goodName + "...");
                Thread.sleep(productionTimeMillis);
                logger.config("Factory produced " + goodName + ".");
                storage.addGood(new Good(goodName, consumptionTimeMillis, loadingTimeMillis, unloadingTimeMillis, ids.remove(0)));
                if (ids.isEmpty()) {
                    makeIDs();
                }
            }
        } catch (InterruptedException e) {
            logger.info(goodName + " factory interrupted.");
        }
    }
}
