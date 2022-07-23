package ru.nsu.fit.oop.lab4;

import ru.nsu.fit.oop.lab4.goods.Storages;

public class Consumer implements Runnable{

    private final Storages storages;

    public Consumer(Storages storages) {
        this.storages = storages;
    }

    @Override
    public void run() {
        try {
            while (true) {
                storages.getGood().consumeGood();
            }
        } catch (InterruptedException e) {
            // DO SMT!!!
        }
    }
}
