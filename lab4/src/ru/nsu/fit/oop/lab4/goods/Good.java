package ru.nsu.fit.oop.lab4.goods;

public class Good {

    private String name;
    private int consumptionTimeSec;
    private int loadingTimeSec;
    private int unloadingTimeSec;

    public Good(String name, int consumptionTimeSec, int loadingTimeSec, int unloadingTimeSec) {
        this.name = name;
        this.consumptionTimeSec = consumptionTimeSec;
        this.loadingTimeSec = loadingTimeSec;
        this.unloadingTimeSec = unloadingTimeSec;
    }

}
