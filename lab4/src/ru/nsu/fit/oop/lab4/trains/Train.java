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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Train implements Runnable {

    private final Map<String, Integer> capacity;
    private List<Good> goods;
    private final int speed;
    private final Station station;
    private final int assemblyTimeMillis;
    private final int depreciationTimeMillis;
    private final int id;
    private final int capacityAll;
    private volatile boolean isMarked = false;
    private volatile boolean isDisposed = false;
    private final Logger logger;

    public Train(Map<String, Integer> capacity, int speed, Station station, int assemblyTimeMillis,
                 int depreciationTimeMillis, int id) {
        logger = Logger.getLogger(this.getClass().getSimpleName());
        logger.setLevel(Level.ALL);
        this.capacity = capacity;
        int totalCapacity = 0;
        for (Map.Entry<String, Integer> goodCapacity : capacity.entrySet()) {
            totalCapacity += goodCapacity.getValue();
        }
        capacityAll = totalCapacity;
        this.speed = speed;
        this.station = station;
        this.assemblyTimeMillis = assemblyTimeMillis;
        this.depreciationTimeMillis = depreciationTimeMillis;
        goods = new ArrayList<>();
        this.id = id;
    }

    public Train(Train train) throws InterruptedException {
        capacity = train.capacity;
        speed = train.speed;
        station = train.station;
        assemblyTimeMillis = train.assemblyTimeMillis;
        depreciationTimeMillis = train.depreciationTimeMillis;
        goods = new ArrayList<>();
        id = train.id;
        capacityAll = train.capacityAll;
        logger = train.logger;
        Thread.sleep(assemblyTimeMillis);
    }

    public int getId() {
        return id;
    }

    public boolean isDisposed() {
        return isDisposed;
    }

    public void mark() {
        isMarked = true;
        logger.info("Train #" + id + " marked for recycling.");
    }

    @Override
    public void run() {
        try {
            while (true) {
                logger.info("Train #" + id + " loading goods...");
                loadGoods();
                logger.info("Train #" + id + " finished loading goods, driving from departure " +
                        "station to destination station...");
                driveDepartureDestination();
                logger.info("Train #" + id + " unloading goods...");
                unloadGoods();
                logger.info("Train #" + id + " finished unloading goods, driving from destination " +
                        "station to departure station...");
                driveDestinationDeparture();
                if (isMarked) {
                    dispose();
                    break;
                }
            }
        } catch (InterruptedException e) {
            logger.info("Train #" + id + " was interrupted.");
            dispose();
        } catch (BadTrackException e) {
        }
        // DO SMT!!!
    }

    private void loadGoods() throws InterruptedException, BadTrackException {
        try {
            LoadingTrack track = (LoadingTrack) station.acquireLoadingTrack();
            for (Map.Entry<String, Integer> entry : capacity.entrySet()) {
                for (int i = 0; i < entry.getValue(); ++i) {
                    Good good = track.getGood(entry.getKey());
                    good.load();
                    goods.add(good);
                    logger.info("Train #" + id + " loaded " + entry.getKey() + "." +
                            " Train occupancy: " + goods.size() + "/" + capacityAll + ".");
                }
                logger.info("Train #" + id + " finished loading " + entry.getKey() + ".");
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
                good.unload();
                track.unloadGood(good);
            }
            station.releaseUnloadingTrack(track);
        } catch (ClassCastException e) {
            throw new BadTrackException(e);
        }
    }

    private void driveDestinationDeparture() throws InterruptedException, BadTrackException {
        try {
            TrafficTrack track = (TrafficTrack) station.acquireDestinationDepartureTrack();
            track.drive(speed);
            station.releaseDestinationDepartureTrack(track);
        } catch (ClassCastException e) {
            throw new BadTrackException(e);
        }
    }

    private void dispose() {
        isDisposed = true;
        synchronized (this) {
            notifyAll();
        }
        logger.info("Train #" + id + " was scrapped.");
    }

    public int getDepreciationTimeMillis() {
        return depreciationTimeMillis;
    }
}
