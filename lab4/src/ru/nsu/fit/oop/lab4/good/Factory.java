package ru.nsu.fit.oop.lab4.good;

import ru.nsu.fit.oop.lab4.ObservableLogging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Factory extends ObservableLogging implements Runnable{

    private final String goodName;
    private final int goodNumber;
    private final int productionTimeMillis;
    private final int consumptionTimeMillis;
    private final int loadingTimeMillis;
    private final int unloadingTimeMillis;
    private final Storage storage;
    private final int numberOfIds = 10000;
    private final int logUpdationFreq = 20;
    private int numberOfGoodsProduced = 0;
    private List<Integer> ids;

    public Factory(String goodName, int productionTimeMillis, int consumptionTimeMillis, int loadingTimeMillis,
                   int unloadingTimeMillis, Storage storage, int goodNumber) throws IOException {
        super(goodName + Factory.class.getSimpleName());
        this.goodNumber = goodNumber;
        this.goodName = goodName;
        this.productionTimeMillis = productionTimeMillis;
        this.consumptionTimeMillis = consumptionTimeMillis;
        this.loadingTimeMillis = loadingTimeMillis;
        this.unloadingTimeMillis = unloadingTimeMillis;
        this.storage = storage;
        makeIDs();
        logger.config(goodName + " factory created.");
    }

    public String getGoodName() {
        return goodName;
    }

    public int getNumberOfGoodsProduced() {
        return numberOfGoodsProduced;
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
                Good good = new Good(goodName, consumptionTimeMillis, loadingTimeMillis, unloadingTimeMillis, ids.remove(0));
                ++numberOfGoodsProduced;
                setChanged();
                notifyObservers();
                if (0 == (numberOfGoodsProduced % logUpdationFreq)) {
                    logger.info("Factory produced " + numberOfGoodsProduced + " " + goodName + " units.");
                } else {
                    logger.config("Factory produced " + goodName + " #" + good.getId() +  ".");
                }
                storage.addGood(good);
                if (ids.isEmpty()) {
                    makeIDs();
                }
            }
        } catch (InterruptedException e) {
            logger.info(goodName + " factory interrupted.");
            logFinalInfo();
        }
    }

    public Good getSample() {
        return new Good(goodName, consumptionTimeMillis, loadingTimeMillis, unloadingTimeMillis,-1);
    }

    @Override
    public void logFinalInfo() {
        logger.info("Factory produced " + numberOfGoodsProduced + " " + goodName + " units.");
    }
}
