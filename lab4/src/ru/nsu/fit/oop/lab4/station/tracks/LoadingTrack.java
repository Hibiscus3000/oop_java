package ru.nsu.fit.oop.lab4.station.tracks;

import ru.nsu.fit.oop.lab4.goods.Good;
import ru.nsu.fit.oop.lab4.goods.Storage;

import java.util.Map;

public class LoadingTrack extends Track {

    private final Map<String, Storage> storages;

    public LoadingTrack(Map<String,Storage> departureStorages, int id) {
        super(id);
        storages = departureStorages;
    }

    public Good getGood(String goodName) throws InterruptedException {
        return storages.get(goodName).getGood();
    }
}
