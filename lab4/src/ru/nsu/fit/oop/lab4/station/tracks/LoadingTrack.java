package ru.nsu.fit.oop.lab4.station.tracks;

import ru.nsu.fit.oop.lab4.goods.Good;
import ru.nsu.fit.oop.lab4.goods.Storages;

import java.util.Map;

public class LoadingTrack extends Track {

    private final Map<String,Storages> storages;

    public LoadingTrack(Map<String,Storages> departureStorages, int id) {
        super(id);
        storages = departureStorages;
    }

    public Good getGood(String goodName) throws InterruptedException {
        return storages.get(goodName).getGood();
    }
}
