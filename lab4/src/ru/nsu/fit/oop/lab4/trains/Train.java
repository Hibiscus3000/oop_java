package ru.nsu.fit.oop.lab4.trains;

import ru.nsu.fit.oop.lab4.Logging;
import ru.nsu.fit.oop.lab4.exception.BadTrackException;
import ru.nsu.fit.oop.lab4.goods.Good;
import ru.nsu.fit.oop.lab4.station.Station;
import ru.nsu.fit.oop.lab4.station.tracks.LoadingTrack;
import ru.nsu.fit.oop.lab4.station.tracks.TrafficTrack;
import ru.nsu.fit.oop.lab4.station.tracks.UnloadingTrack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Train implements Runnable, Logging {

    private final Map<String, Integer> capacity;
    private List<Good> goods;
    private final int speed;
    private final Station station;
    private final int assemblyTimeMillis;
    private final int depreciationTimeMillis;
    private final int id;
    private final int capacityAll;
    private int goodsTransported = 0;
    private volatile boolean isMarked = false;
    private volatile boolean isDisposed = false;
    private final Logger logger;

    public Train(Map<String, Integer> capacity, int speed, Station station, int assemblyTimeMillis,
                 int depreciationTimeMillis, int id) throws IOException {
        logger = Logger.getLogger(this.getClass().getSimpleName() + id);
        logger.setLevel(Level.ALL);
        FileHandler fileHandler = new FileHandler("train" + id + "_log%g.txt",
                1000000,1,false);
        fileHandler.setLevel(Level.ALL);
        logger.addHandler(fileHandler);
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
        logger.config("Sample #" + id + " created.");
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
        logger.config("Train #" + id + " created.");
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
            logger.warning("Train #" + id + " was interrupted.");
            logFinalInfo();
            dispose();
        } catch (BadTrackException e) {
        }
        // DO SMT!!!
    }

    private void loadGoods() throws InterruptedException, BadTrackException {
        try {
            LoadingTrack track = (LoadingTrack) station.acquireLoadingTrack();
            for (Map.Entry<String, Integer> entry : capacity.entrySet()) {
                logger.info("Train #" + id + " loading " + entry.getKey() + ".");
                for (int i = 0; i < entry.getValue(); ++i) {
                    Good good = track.getGood(entry.getKey());
                    good.load();
                    goods.add(good);
                    logger.config("Train #" + id + " loaded " + entry.getKey() + "." +
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
                ++goodsTransported;
                logger.config("Train #" + id + " unloaded " + good.getName() + "." +
                        " Train occupancy: " + goods.size() + "/" + capacityAll + ".");
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

    @Override
    public void logFinalInfo() {
        logger.info("Train #" + id + " transported " + goodsTransported + " goods.\n" +
                "Then it was interrupted it had " + goods.size() + " in it.");
    }
}
