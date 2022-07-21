package ru.nsu.fit.oop.lab4.goods;

import java.util.concurrent.Executors;

public class Factory implements Runnable{

    private final String goodName;
    private final int productionTimeSec;
    private final int consumptionTimeSec;
    private final int loadingTimeSec;
    private final int unloadingTimeSec;
    private final Storages storages;

    public Factory(String goodName, int productionTimeSec, int consumptionTimeSec, int loadingTimeSec,
                   int unloadingTimeSec, Storages storages) {
        this.storages = storages;
        this.goodName = goodName;
        this.productionTimeSec = productionTimeSec;
        this.consumptionTimeSec = consumptionTimeSec;
        this.loadingTimeSec = loadingTimeSec;
        this.unloadingTimeSec = unloadingTimeSec;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(1000 * productionTimeSec);
                storages.addGood(new Good(goodName,consumptionTimeSec,loadingTimeSec,unloadingTimeSec));
            }
        } catch (InterruptedException e) {}
    }
}
