package ru.nsu.fit.oop.lab4.goods;

import ru.nsu.fit.oop.lab4.exception.ReusedGoodException;

public class Good {

    private final String name;
    private final int consumptionTimeMillis;
    private final int loadingTimeMillis;
    private final int unloadingTimeMillis;
    private final int id;
    private boolean consumed = false;

    public Good(String name, int consumptionTimeMillis, int loadingTimeMillis, int unloadingTimeMillis, int id) {
        this.name = name;
        this.consumptionTimeMillis = consumptionTimeMillis;
        this.loadingTimeMillis = loadingTimeMillis;
        this.unloadingTimeMillis = unloadingTimeMillis;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void consume() throws InterruptedException, ReusedGoodException {
        if (consumed)
            throw new ReusedGoodException(this);
        Thread.sleep(consumptionTimeMillis);
        consumed = true;
    }

    public void load() throws InterruptedException {
        Thread.sleep(loadingTimeMillis);
    }

    public void unload() throws InterruptedException {
        Thread.sleep(unloadingTimeMillis);
    }

}
