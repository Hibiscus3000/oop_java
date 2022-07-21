package ru.nsu.fit.oop.lab4;

import ru.nsu.fit.oop.lab4.goods.Good;
import ru.nsu.fit.oop.lab4.station.Station;
import ru.nsu.fit.oop.lab4.station.tracks.LoadingTrack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Train implements Runnable {

    private final Map<String, Integer> capacity;
    private List<Good> goods;
    private final int speed;
    private final Station station;

    public Train(Map<String, Integer> capacity, int speed, Station station) {
        this.capacity = capacity;
        this.speed = speed;
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
        LoadingTrack track = (LoadingTrack) station.acquireLoadingTrack();
        for (Map.Entry<String, Integer> entry : capacity.entrySet()) {
            for (int i = 0; i < entry.getValue(); ++i) {
                track.getGood(entry.getKey());
            }
        }
        station.releaseLoadingTrack(track);
    }
}
