package ru.nsu.fit.oop.lab4;

import ru.nsu.fit.oop.lab4.goods.Good;
import ru.nsu.fit.oop.lab4.goods.Storages;
import ru.nsu.fit.oop.lab4.station.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Train implements Runnable {

    private final Map<String, Integer> capacity;
    private final Map<String, Storages> departureStorages;
    private final Map<String, Storages> destinationStorages;
    private List<Good> goods;
    private final int speed;
    private final Station station;

    public Train(Map<String, Integer> capacity, int speed, Map<String, Storages> departureStorages,
                 Map<String, Storages> destinationStorages, Station station) {
        this.capacity = capacity;
        this.speed = speed;
        this.departureStorages = departureStorages;
        this.destinationStorages = destinationStorages;
        this.station = station;
        goods = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            while (true) {
                loadGoods();
            }
        } catch (InterruptedException e) {

        }
    }

    private void loadGoods() throws InterruptedException {
        for (Map.Entry<String, Integer> entry : capacity.entrySet()) {
            for (int i = 0; i < entry.getValue(); ++i) {
                goods.add(departureStorages.get(entry.getKey()).getGood());
            }
        }
    }
}
