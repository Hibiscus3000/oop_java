package ru.nsu.fit.oop.lab4.goods;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Storage {

    private final String goodName;
    private List<Good> goods;
    private final int capacity;
    private final Logger logger;

    public Storage(String goodName, int capacity) {
        logger = Logger.getLogger(this.getClass().getSimpleName());
        this.goodName = goodName;
        this.capacity = capacity;
        goods = new ArrayList<>();
    }

    public String getGoodName() {
        return goodName;
    }

    public synchronized void addGood(Good good) throws InterruptedException {
        while (goods.size() == capacity) {
            logger.config("Train or factory is standing idle, because storage is full");
            wait();
        }
        goods.add(good);
        logger.info(goodName + " added to storage. Storage occupancy: " + goods.size() + "/" +
                capacity);
        notifyAll();
    }

    public synchronized Good getGood() throws InterruptedException {
        while (goods.isEmpty()) {
            logger.config("Train or consumer is standing idle, because storage is empty");
            wait();
        }
        Good good = goods.remove(0);
        notifyAll();
        logger.info(goodName + " removed from storage. Storage occupancy: " + goods.size() + "/" +
                capacity);
        return good;
    }
}
