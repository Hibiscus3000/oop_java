package ru.nsu.fit.oop.lab4.goods;

import java.util.ArrayList;
import java.util.List;

public class Storage {

    private final String goodName;
    private List<Good> goods;
    private final int capacity;

    public Storage(String goodName, int capacity) {
        this.goodName = goodName;
        this.capacity = capacity;
        goods = new ArrayList<>();
    }

    public synchronized void addGood(Good good) throws InterruptedException {
        while (goods.size() == capacity)
            wait();
        goods.add(good);
        notifyAll();
    }

    public synchronized Good getGood() throws InterruptedException {
        while (goods.isEmpty())
            wait();
        Good good = goods.remove(goods.size());
        notifyAll();
        return good;
    }
}
