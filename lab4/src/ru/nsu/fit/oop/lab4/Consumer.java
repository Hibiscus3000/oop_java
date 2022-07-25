package ru.nsu.fit.oop.lab4;

import ru.nsu.fit.oop.lab4.goods.Storage;

public class Consumer implements Runnable {

    private final Storage storage;
    private final int id;

    public Consumer(Storage storage, int id) {
        this.storage = storage;
        this.id = id;
    }

    public String getGoodName() {
        return storage.getGoodName();
    }

    @Override
    public void run() {
        try {
            while (true) {
                storage.getGood().consumeGood();
            }
        } catch (InterruptedException e) {
            // DO SMT!!!
        }
    }
}
