package ru.nsu.fit.oop.lab4;

import ru.nsu.fit.oop.lab4.goods.Storages;

public class Consumer implements Runnable{

    private Storages storages;

    public Consumer(Storages storages) {
        this.storages = storages;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(1000 * storages.getGood().getConsumptionTimeSec());
            }
        } catch (InterruptedException e) {
            // DO SMT!!!
        }
    }
}
