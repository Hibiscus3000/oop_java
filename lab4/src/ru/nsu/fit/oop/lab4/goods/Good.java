package ru.nsu.fit.oop.lab4.goods;

public class Good {

    private final String name;
    private final int consumptionTimeMillis;
    private final int loadingTimeMillis;
    private final int unloadingTimeMillis;
    private final int id;

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

    public void consume() throws InterruptedException {
        Thread.sleep(consumptionTimeMillis);
    }

    public void load() throws InterruptedException {
        Thread.sleep(loadingTimeMillis);
    }

    public void unload() throws InterruptedException {
        Thread.sleep(unloadingTimeMillis);
    }

}
