package ru.nsu.fit.oop.lab4;

import ru.nsu.fit.oop.lab4.goods.Storage;

public class Consumer implements Runnable{

    private final Storage storage;

    public Consumer(Storage storage) {
        this.storage = storage;
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
