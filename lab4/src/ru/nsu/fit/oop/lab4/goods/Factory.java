package ru.nsu.fit.oop.lab4.goods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Factory implements Runnable{

    private final String goodName;
    private final int goodNumber;
    private final int productionTimeSec;
    private final int consumptionTimeSec;
    private final int loadingTimeSec;
    private final int unloadingTimeSec;
    private final Storage storage;
    private final int numberOfIds = 10000;
    private List<Integer> ids;

    public Factory(String goodName, int productionTimeSec, int consumptionTimeSec, int loadingTimeSec,
                   int unloadingTimeSec, Storage storage, int goodNumber) {
        this.goodNumber = goodNumber;
        this.goodName = goodName;
        this.productionTimeSec = productionTimeSec;
        this.consumptionTimeSec = consumptionTimeSec;
        this.loadingTimeSec = loadingTimeSec;
        this.unloadingTimeSec = unloadingTimeSec;
        this.storage = storage;
        makeIDs();
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
                Thread.sleep(1000 * productionTimeSec);
                storage.addGood(new Good(goodName,consumptionTimeSec,loadingTimeSec,unloadingTimeSec,ids.remove(0)));
                if (ids.isEmpty()) {
                    makeIDs();
                }
            }
        } catch (InterruptedException e) {}
    }
}
