package ru.nsu.fit.oop.lab4.station.tracks;

import ru.nsu.fit.oop.lab4.goods.Good;
import ru.nsu.fit.oop.lab4.goods.Storage;

import java.util.Map;

public class UnloadingTrack extends Track {

    private final Map<String, Storage> storages;

    public UnloadingTrack(Map<String, Storage> destinationStorages, int id) {
        super(id);
        storages = destinationStorages;
    }

    public void unloadGood(Good good) throws InterruptedException {
        storages.get(good.getName()).addGood(good);
    }
}
