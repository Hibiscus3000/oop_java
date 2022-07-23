package ru.nsu.fit.oop.lab4.trains;

import ru.nsu.fit.oop.lab4.exception.BadTrackException;
import ru.nsu.fit.oop.lab4.goods.Good;
import ru.nsu.fit.oop.lab4.station.Station;
import ru.nsu.fit.oop.lab4.station.tracks.LoadingTrack;
import ru.nsu.fit.oop.lab4.station.tracks.TrafficTrack;
import ru.nsu.fit.oop.lab4.station.tracks.UnloadingTrack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Train implements Runnable {

    private final Map<String, Integer> capacity;
    private List<Good> goods;
    private final int speed;
    private final Station station;
    private final int assemblyTimeSec;
    private final int depreciationTimeSec;

    public Train(Map<String, Integer> capacity, int speed, Station station, int assemblyTimeSec,
                 int depreciationTimeSec) {
        this.capacity = capacity;
        this.speed = speed;
        this.station = station;
        this.assemblyTimeSec = assemblyTimeSec;
        this.depreciationTimeSec = depreciationTimeSec;
        goods = new ArrayList<>();
    }

    public Train(Train train) throws InterruptedException {
        capacity = train.capacity;
        speed = train.speed;
        station = train.station;
        assemblyTimeSec = train.assemblyTimeSec;
        depreciationTimeSec = train.depreciationTimeSec;
        Thread.sleep(assemblyTimeSec);
    }

    @Override
    public void run() {
        try {
            while (true) {
                loadGoods();
                driveDepartureDestination();
                unloadGoods();
                driveDestinationDeparture();
            }
        } catch (InterruptedException e) {
            notifyAll();
        } catch (BadTrackException e) {
        }
        // DO SMT!!!
    }

    private void loadGoods() throws InterruptedException, BadTrackException {
        try {
            LoadingTrack track = (LoadingTrack) station.acquireLoadingTrack();
            for (Map.Entry<String, Integer> entry : capacity.entrySet()) {
                for (int i = 0; i < entry.getValue(); ++i) {
                    track.getGood(entry.getKey());
                }
            }
            station.releaseLoadingTrack(track);
        } catch (ClassCastException e) {
            throw new BadTrackException(e);
        }
    }

    private void driveDepartureDestination() throws InterruptedException, BadTrackException {
        try {
            TrafficTrack track = (TrafficTrack) station.acquireDepartureDestinationTrack();
            track.drive(speed);
            station.releaseDepartureDestinationTrack(track);
        } catch (ClassCastException e) {
            throw new BadTrackException(e);
        }
    }

    private void unloadGoods() throws InterruptedException, BadTrackException {
        try {
            UnloadingTrack track = (UnloadingTrack) station.acquireUnloadingTrack();
            for (Good good : goods) {
                track.unloadGood(good);
            }
        } catch (ClassCastException e) {
            throw new BadTrackException(e);
        }
    }

    private void driveDestinationDeparture() throws InterruptedException, BadTrackException {
        try {
            TrafficTrack track = (TrafficTrack) station.acquireDestinationDepartureTrack();
            track.drive(speed);
            station.releaseDepartureDestinationTrack(track);
        } catch (ClassCastException e) {
            throw new BadTrackException(e);
        }
    }

    public int getDepreciationTimeSec() {
        return depreciationTimeSec;
    }
}
