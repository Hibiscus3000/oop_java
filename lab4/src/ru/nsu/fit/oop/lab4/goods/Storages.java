package ru.nsu.fit.oop.lab4.goods;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Storages {

    private final String goodName;
    private final List<Storage> storages;
    private final int capacityAll;
    private int goodsQuantityAll = 0;
    private final Lock addLock = new ReentrantLock();
    private final Condition addCondition = addLock.newCondition();
    private final Lock loadLock = new ReentrantLock();
    private final Condition loadCondition = loadLock.newCondition();

    public Storages(String goodName, List<Storage> storages, int capacityAll) {
        this.goodName = goodName;
        this.storages = storages;
        this.capacityAll = capacityAll;
    }

    public boolean isFree() {
        loadLock.lock();
        try {
            return (capacityAll == goodsQuantityAll);
        }
        finally {
            loadLock.unlock();
        }
    }

    public boolean isEmpty() {
        addLock.lock();
        try {
            return (0 == goodsQuantityAll);
        }
        finally {
            addLock.unlock();
        }
    }

    public void addGood(Good good) throws InterruptedException {
        addLock.lock();
        try {
            while (!isFree())
                addCondition.await();
            for (Storage storage : storages) {
                if (storage.isFree()) {
                    storage.addGood(good);
                    ++goodsQuantityAll;
                    loadLock.lock();
                    loadCondition.signalAll();
                    break;
                }
            }
        } finally {
            loadLock.unlock();
            addLock.unlock();
        }
    }

    public Good getGood() throws InterruptedException {
        loadLock.lock();
        try {
            while (isEmpty())
                loadCondition.await();
            for (Storage storage : storages) {
                if (!storage.isEmpty()) {
                    --goodsQuantityAll;
                    return storage.getGood();
                }
            }
            return null;
        } finally {
            addLock.lock();
            addCondition.signalAll();
            addLock.unlock();
            loadLock.unlock();
        }
    }
}
