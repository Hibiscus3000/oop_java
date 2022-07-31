package ru.nsu.fit.oop.lab4.goods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Storage {

    private final String goodName;
    private List<Good> goods;
    private final int capacity;
    private final String place;
    private final String takerName;
    private final String placerName;
    private final Logger logger;

    public Storage(String goodName, int capacity, String place) throws IOException {
        if (place == "Departure") {
            takerName = "Train";
            placerName = "Factory";
        } else {
            takerName = "Consumer";
            placerName = "Train";
        }
        logger = Logger.getLogger(goodName + this.getClass().getSimpleName());
        this.goodName = goodName;
        this.capacity = capacity;
        this.place = place;
        goods = new ArrayList<>();
        FileHandler fileHandler = new FileHandler(getGoodName() + place + "Storage_log%g.txt",
                1000000, 1, false);
        fileHandler.setLevel(Level.ALL);
        logger.addHandler(fileHandler);
        logger.setLevel(Level.ALL);
        logger.config(goodName + " storage created.");
    }

    public String getGoodName() {
        return goodName;
    }

    public synchronized void addGood(Good good) throws InterruptedException {
        while (goods.size() == capacity) {
            logger.config(placerName + " is standing idle, because storage is full");
            wait();
        }
        goods.add(good);
        logger.config(goodName + " added to storage. Storage occupancy: " + goods.size() + "/" +
                capacity);
        notifyAll();
    }

    public synchronized Good getGood() throws InterruptedException {
        while (goods.isEmpty()) {
            logger.config(takerName + " is standing idle, because storage is empty");
            wait();
        }
        Good good = goods.remove(0);
        notifyAll();
        logger.config(goodName + " removed from storage. Storage occupancy: " + goods.size() + "/" +
                capacity);
        return good;
    }
}
