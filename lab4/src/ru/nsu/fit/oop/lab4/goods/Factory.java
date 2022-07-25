package ru.nsu.fit.oop.lab4.goods;

import ru.nsu.fit.oop.lab4.Main;
import ru.nsu.fit.oop.lab4.exception.UnsuccessfulLoggerCreation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Factory implements Runnable {

    private final String goodName;
    private final int goodNumber;
    private final int productionTimeSec;
    private final int consumptionTimeSec;
    private final int loadingTimeSec;
    private final int unloadingTimeSec;
    private final Storage storage;
    private final int numberOfIds = 10000;
    private List<Integer> ids;
    private final Logger logger;

    public Factory(String goodName, int productionTimeSec, int consumptionTimeSec, int loadingTimeSec,
                   int unloadingTimeSec, Storage storage, int goodNumber) throws UnsuccessfulLoggerCreation {
        try {
            LogManager.getLogManager().readConfiguration(this.getClass().getResourceAsStream
                    ("factory_log.properties"));
        } catch (IOException e) {
            Main.logger.severe("Wasn't able to create logger for " + goodName + " factory");
            throw new UnsuccessfulLoggerCreation("factory",e);
        }
        logger = Logger.getLogger(this.getClass().getSimpleName());
        logger.setLevel(Level.ALL);
        this.goodNumber = goodNumber;
        this.goodName = goodName;
        this.productionTimeSec = productionTimeSec;
        this.consumptionTimeSec = consumptionTimeSec;
        this.loadingTimeSec = loadingTimeSec;
        this.unloadingTimeSec = unloadingTimeSec;
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
                logger.config("Producing " + goodName + "...");
                Thread.sleep(1000 * productionTimeSec);
                logger.info("Produced " + goodName + ".");
                storage.addGood(new Good(goodName, consumptionTimeSec, loadingTimeSec, unloadingTimeSec, ids.remove(0)));
                logger.config("Factory added " + goodName + " to storage.");
                if (ids.isEmpty()) {
                    makeIDs();
                }
            }
        } catch (InterruptedException e) {
            logger.info("Factory interrupted");
        }
    }
}
