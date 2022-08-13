package ru.nsu.fit.oop.lab4.good;

import ru.nsu.fit.oop.lab4.ObservableLogging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.nsu.fit.oop.lab4.Util.*;

public class Storage extends ObservableLogging {

    private final String goodName;
    private List<Good> goods;
    private final int capacity;
    private final String place;
    private final String takerName;
    private final String placerName;

    public Storage(String goodName, int capacity, String place) throws IOException {
        super(Storage.class.getPackageName() + eraseWhitespaces(toUpperFirstChar(goodName)) +
                place + Storage.class.getSimpleName(),toUpperFirstChar(eraseWhitespaces(goodName)) + place +
                Storage.class.getSimpleName(), goodName + " " + toLowerFirstChar(place) +
                " storage log");
        if (place == "Departure") {
            takerName = "Train";
            placerName = "Factory";
        } else {
            takerName = "Consumer";
            placerName = "Train";
        }
        this.goodName = goodName;
        this.capacity = capacity;
        this.place = place;
        goods = new ArrayList<>();
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
        setChanged();
        notifyObservers();
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
        setChanged();
        notifyObservers();
        notifyAll();
        logger.config(goodName + " removed from storage. Storage occupancy: " + goods.size() + "/" +
                capacity);
        return good;
    }

    public String getPlace() {
        return place;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getGoodsQuantity() {
        return goods.size();
    }

    @Override
    public void logFinalInfo() {

    }
}
