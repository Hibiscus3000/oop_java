package ru.nsu.fit.oop.lab4.goods;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Factory {

    private final String goodName;
    private final int productionTimeSec;
    private final int consumptionTimeSec;
    private final int loadingTimeSec;
    private final int unloadingTimeSec;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
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

    public void start() {
        executor.scheduleWithFixedDelay(() -> {
            if (!Thread.currentThread().isInterrupted())
                executor.shutdown();
            try {
                storages.addGood(new Good(goodName,consumptionTimeSec,loadingTimeSec,unloadingTimeSec));
            } catch (InterruptedException e) {
                executor.shutdown();
            }
        },productionTimeSec,productionTimeSec, TimeUnit.SECONDS);
    }

}
