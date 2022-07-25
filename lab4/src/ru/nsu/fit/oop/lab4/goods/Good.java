package ru.nsu.fit.oop.lab4.goods;

public class Good {

    private final String name;
    private final int consumptionTimeSec;
    private final int loadingTimeSec;
    private final int unloadingTimeSec;
    private final int id;

    public Good(String name, int consumptionTimeSec, int loadingTimeSec, int unloadingTimeSec, int id) {
        this.name = name;
        this.consumptionTimeSec = consumptionTimeSec;
        this.loadingTimeSec = loadingTimeSec;
        this.unloadingTimeSec = unloadingTimeSec;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void consume() throws InterruptedException {
        Thread.sleep(1000 * consumptionTimeSec);
    }

    public void load() throws InterruptedException {
        Thread.sleep(1000 * loadingTimeSec);
    }

    public void unload() throws InterruptedException {
        Thread.sleep(1000 * unloadingTimeSec);
    }

}
