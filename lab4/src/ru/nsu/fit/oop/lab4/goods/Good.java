package ru.nsu.fit.oop.lab4.goods;

public class Good {

    private String name;
    private final int consumptionTimeSec;
    private final int loadingTimeSec;
    private final int unloadingTimeSec;

    public Good(String name, int consumptionTimeSec, int loadingTimeSec, int unloadingTimeSec) {
        this.name = name;
        this.consumptionTimeSec = consumptionTimeSec;
        this.loadingTimeSec = loadingTimeSec;
        this.unloadingTimeSec = unloadingTimeSec;
    }

    public String getName() {
        return name;
    }

    public int getConsumptionTimeSec() {
        return consumptionTimeSec;
    }
}
