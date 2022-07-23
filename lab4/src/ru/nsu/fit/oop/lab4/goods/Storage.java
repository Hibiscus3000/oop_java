package ru.nsu.fit.oop.lab4.goods;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Storage {

    List<Good> goods;
    private final int capacity;
    private boolean isFree = true;
    private final Lock loadLock = new ReentrantLock();

    public Storage(int capacity) {
        this.capacity = capacity;
        goods = new ArrayList<>();
    }

    public boolean isFree() {
        return isFree;
    }

    public boolean isEmpty() {
        return goods.isEmpty();
    }

    public void addGood(Good good) {
        goods.add(good);
    }

    public Good getGood() {
        return goods.remove(goods.size());
    }

    public int getCapacity() {
        return capacity;
    }
}
