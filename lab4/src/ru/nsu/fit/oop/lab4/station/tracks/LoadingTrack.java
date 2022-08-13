package ru.nsu.fit.oop.lab4.station.tracks;

import ru.nsu.fit.oop.lab4.good.Good;
import ru.nsu.fit.oop.lab4.good.Storage;

import java.util.Map;

public class LoadingTrack extends StationTrack {

    public LoadingTrack(int id, Map<String,Storage> departureStorages) {
        super(id,departureStorages);
    }

    public Good getGood(String goodName) throws InterruptedException {
        return storages.get(goodName).getGood();
    }
}
