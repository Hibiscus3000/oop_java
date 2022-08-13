package ru.nsu.fit.oop.lab4.station.tracks;

import ru.nsu.fit.oop.lab4.good.Storage;

import java.util.Map;

public abstract class StationTrack extends Track{

    protected final Map<String, Storage> storages;

    public StationTrack(int id, Map<String, Storage> storages) {
        super(id);
        this.storages = storages;
    }

}
