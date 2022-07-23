package ru.nsu.fit.oop.lab4.goods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Factory implements Runnable{

    private final String goodName;
    private final int productionTimeSec;
    private final int consumptionTimeSec;
    private final int loadingTimeSec;
    private final int unloadingTimeSec;
    private final Storages storages;
    private final int numberOfIds = 10000;
    private final List<Integer> ids;

    public Factory(String goodName, int productionTimeSec, int consumptionTimeSec, int loadingTimeSec,
                   int unloadingTimeSec, Storages storages, int goodNumber) {
        this.storages = storages;
        this.goodName = goodName;
        this.productionTimeSec = productionTimeSec;
        this.consumptionTimeSec = consumptionTimeSec;
        this.loadingTimeSec = loadingTimeSec;
        this.unloadingTimeSec = unloadingTimeSec;
        ids = IntStream.range(numberOfIds * goodNumber + 1, numberOfIds * (goodNumber + 1) + 1).boxed()
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(ids);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(1000 * productionTimeSec);
                storages.addGood(new Good(goodName,consumptionTimeSec,loadingTimeSec,unloadingTimeSec,ids.remove(0)));
            }
        } catch (InterruptedException e) {}
    }
}
