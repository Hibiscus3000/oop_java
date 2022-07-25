package ru.nsu.fit.oop.lab4.trains;

import ru.nsu.fit.oop.lab4.Main;
import ru.nsu.fit.oop.lab4.exception.BadTrackException;
import ru.nsu.fit.oop.lab4.exception.UnsuccessfulLoggerCreation;
import ru.nsu.fit.oop.lab4.goods.Good;
import ru.nsu.fit.oop.lab4.station.Station;
import ru.nsu.fit.oop.lab4.station.tracks.LoadingTrack;
import ru.nsu.fit.oop.lab4.station.tracks.TrafficTrack;
import ru.nsu.fit.oop.lab4.station.tracks.UnloadingTrack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Train implements Runnable {

    private final Map<String, Integer> capacity;
    private List<Good> goods;
    private final int speed;
    private final Station station;
    private final int assemblyTimeSec;
    private final int depreciationTimeSec;
    private final int id;
    private boolean isDisposed = false;
    private final Logger logger;

    public Train(Map<String, Integer> capacity, int speed, Station station, int assemblyTimeSec,
                 int depreciationTimeSec, int id) throws UnsuccessfulLoggerCreation {
        try {
            LogManager.getLogManager().readConfiguration(this.getClass().getResourceAsStream
                    ("train_log.properties"));
        } catch (IOException e) {
            Main.logger.severe("Wasn't able to create logger for train");
            throw new UnsuccessfulLoggerCreation("train",e);
        }
        logger = Logger.getLogger(this.getClass().getSimpleName());
        logger.setLevel(Level.ALL);
        this.capacity = capacity;
        this.speed = speed;
        this.station = station;
        this.assemblyTimeSec = assemblyTimeSec;
        this.depreciationTimeSec = depreciationTimeSec;
        goods = new ArrayList<>();
        this.id = id;
    }

    public Train(Train train) throws InterruptedException {
        capacity = train.capacity;
        speed = train.speed;
        station = train.station;
        assemblyTimeSec = train.assemblyTimeSec;
        depreciationTimeSec = train.depreciationTimeSec;
        logger = train.logger;
        Thread.sleep(assemblyTimeSec);
        id = train.id;
    }

    public int getId() {
        return id;
    }

    public boolean isDisposed() {
        return isDisposed;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Main.logger.info("Train #" + id + " starting to load goods...");
                loadGoods();
                Main.logger.info("Train #" + id + " starting to drive from departure" +
                        "station to destination station...");
                driveDepartureDestination();
                Main.logger.info("Train #" + id + " starting to unload goods...");
                unloadGoods();
                Main.logger.info("Train #" + id + " starting to drive from destination" +
                        "station to departure station...");
                driveDestinationDeparture();
            }
        } catch (InterruptedException e) {
            logger.info("Train #" + id + " was interrupted.");
            isDisposed = true;
            notifyAll();
        } catch (BadTrackException e) {
        }
        // DO SMT!!!
    }

    private void loadGoods() throws InterruptedException, BadTrackException {
        try {
            LoadingTrack track = (LoadingTrack) station.acquireLoadingTrack();
            for (Map.Entry<String, Integer> entry : capacity.entrySet()) {
                Main.logger.config("");
                for (int i = 0; i < entry.getValue(); ++i) {
                    Good good = track.getGood(entry.getKey());
                    good.load();
                    goods.add(good);
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
                good.unload();
                track.unloadGood(good);
                station.releaseUnloadingTrack(track);
            }
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

    public int getDepreciationTimeSec() {
        return depreciationTimeSec;
    }
}
