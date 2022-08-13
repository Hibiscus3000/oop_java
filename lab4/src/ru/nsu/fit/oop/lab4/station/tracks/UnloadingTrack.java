package ru.nsu.fit.oop.lab4.station.tracks;

import ru.nsu.fit.oop.lab4.good.Good;
import ru.nsu.fit.oop.lab4.good.Storage;

import java.util.Map;

public class UnloadingTrack extends StationTrack {

    public UnloadingTrack(int id,Map<String, Storage> destinationStorages) {
        super(id,destinationStorages);
    }

    public void unloadGood(Good good) throws InterruptedException {
        storages.get(good.getName()).addGood(good);
    }
}
