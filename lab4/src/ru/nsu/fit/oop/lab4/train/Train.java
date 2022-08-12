package ru.nsu.fit.oop.lab4.train;

import ru.nsu.fit.oop.lab4.ObservableLogging;
import ru.nsu.fit.oop.lab4.exception.BadTrackException;
import ru.nsu.fit.oop.lab4.exception.UnknownGoodName;
import ru.nsu.fit.oop.lab4.good.Good;
import ru.nsu.fit.oop.lab4.station.Station;
import ru.nsu.fit.oop.lab4.station.tracks.LoadingTrack;
import ru.nsu.fit.oop.lab4.station.tracks.TrafficTrack;
import ru.nsu.fit.oop.lab4.station.tracks.UnloadingTrack;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class Train extends ObservableLogging implements Runnable {

    private final Map<String, Integer> capacity;
    private final Map<String, List<Good>> goods;
    private final int speed;
    private final Station station;
    private final int assemblyTimeMillis;
    private final int depreciationTimeMillis;
    private final int id;
    private final int capacityAll;
    private int goodsTransported = 0;
    private volatile boolean mark = false;
    private volatile boolean isDisposed = false;
    private TrainState state;

    public Train(Map<String, Integer> capacity, int speed, Station station, int assemblyTimeMillis,
                 int depreciationTimeMillis, int id) throws IOException {
        super(Train.class.getName() + id,Train.class.getSimpleName() + id);
        this.capacity = capacity;
        goods = new HashMap<>();
        for (Map.Entry<String, Integer> entry : capacity.entrySet()) {
             goods.put(entry.getKey(),new ArrayList<>(entry.getValue()));
        }
        int totalCapacity = 0;
        for (Map.Entry<String, Integer> goodCapacity : capacity.entrySet()) {
            totalCapacity += goodCapacity.getValue();
        }
        capacityAll = totalCapacity;
        this.speed = speed;
        this.station = station;
        this.assemblyTimeMillis = assemblyTimeMillis;
        this.depreciationTimeMillis = depreciationTimeMillis;
        this.id = id;
        logger.config("Sample #" + id + " created.");
    }

    public Train(Train train, Observer observer) throws InterruptedException {
        super(train);
        capacity = train.capacity;
        speed = train.speed;
        station = train.station;
        assemblyTimeMillis = train.assemblyTimeMillis;
        depreciationTimeMillis = train.depreciationTimeMillis;
        id = train.id;
        capacityAll = train.capacityAll;
        logger = train.logger;
        goods = train.goods;
        logger.config("Train #" + id + " created.");
        state = TrainState.ASSEMBLING;
        addObserver(observer);
    }

    public int getId() {
        return id;
    }

    public boolean isDisposed() {
        return isDisposed;
    }

    public void mark() {
        mark = true;
        logger.info("Train #" + id + " mark for recycling.");
    }

    @Override
    public void run() {
        try {
            while (true) {
                setChanged();
                notifyObservers();
                Thread.sleep(assemblyTimeMillis);
                logger.info("Train #" + id + " loading goods...");
                state = TrainState.LOADING;
                setChanged();
                notifyObservers();
                loadGoods();
                logger.info("Train #" + id + " finished loading goods, driving from departure " +
                        "station to destination station...");
                state = TrainState.DRIVING_DEPARTURE_DESTINATION;
                setChanged();
                notifyObservers();
                driveDepartureDestination();
                logger.info("Train #" + id + " unloading goods...");
                state = TrainState.UNLOADING;
                setChanged();
                notifyObservers();
                unloadGoods();
                logger.info("Train #" + id + " finished unloading goods, driving from destination " +
                        "station to departure station...");
                state = TrainState.DRIVING_DESTINATION_DEPARTURE;
                setChanged();
                notifyObservers();
                driveDestinationDeparture();
                if (mark) {
                    dispose();
                    break;
                }
            }
        } catch (InterruptedException e) {
            logger.warning("Train #" + id + " was interrupted.");
            logFinalInfo();
            dispose();
        } catch (BadTrackException e) {
            logger.log(Level.SEVERE,"Train #" + id + " threw a bad track exception.",e);
        } catch (UnknownGoodName e) {
            logger.log(Level.SEVERE,"Train #" + id + " threw a unknown good name exception.",e);
        }
    }

    private void loadGoods() throws InterruptedException, BadTrackException, UnknownGoodName {
        try {
            LoadingTrack track = (LoadingTrack) station.acquireLoadingTrack();
            for (Map.Entry<String, Integer> entry : capacity.entrySet()) {
                String goodName = entry.getKey();
                logger.info("Train #" + id + " loading " + goodName + ".");
                for (int i = 0; i < entry.getValue(); ++i) {
                    Good good = track.getGood(goodName);
                    good.load();
                    List currentGoods = goods.get(goodName);
                    if (null == currentGoods) {
                        throw new UnknownGoodName(goodName);
                    }
                    currentGoods.add(good);
                    setChanged();
                    notifyObservers();
                    logger.config("Train #" + id + " loaded " + goodName + " #" + good.getId() + "." +
                            " Train " + goodName +  " occupancy: " + (i + 1) + "/" + entry.getValue() + ".");
                }
                logger.info("Train #" + id + " finished loading " + goodName + ".");
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
            for (Map.Entry<String,List<Good>> entry : goods.entrySet()) {
                for (Good good : entry.getValue()) {
                    goods.remove(good);
                    setChanged();
                    notifyObservers();
                    good.unload();
                    track.unloadGood(good);
                    ++goodsTransported;
                    logger.config("Train #" + id + " unloaded " + good.getName() + "." +
                            "Train " + entry.getKey() + " occupancy: " + entry.getValue().size()
                            + "/" + capacity.get(entry.getKey()) + ".");
                }
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
        logger.info("Train #" + id + " transported " + goodsTransported + " good units.\n" +
                "Then it was interrupted, it had " + getAllGoodsQuantity() + " in it.");
    }

    public int getGoodCapacity(String goodName) throws UnknownGoodName {
        Integer goodCapacity = capacity.get(goodName);
        if (null == goodCapacity) {
            UnknownGoodName unknownGoodName = new UnknownGoodName(goodName);
            logger.throwing(this.getClass().getSimpleName(), "getGoodCapacity", unknownGoodName);
            throw unknownGoodName;
        } else {
            return goodCapacity;
        }
    }

    public int getGoodQuantity(String goodName) throws UnknownGoodName {
        List<Good> goodList = goods.get(goodName);
        if (null == goodList) {
            UnknownGoodName unknownGoodName = new UnknownGoodName(goodName);
            logger.throwing(this.getClass().getSimpleName(), "getGoodQuantity", unknownGoodName);
            throw unknownGoodName;
        } else {
            return goodList.size();
        }
    }

    public int getAllGoodsQuantity() {
        int allGoodsQuantity = 0;
        for (Map.Entry<String, List<Good>> goodType : goods.entrySet()) {
            allGoodsQuantity += goodType.getValue().size();
        }
        return allGoodsQuantity;
    }

    public String getState() {
        return state.getStringState();
    }

    public boolean isMarked() {
        return mark;
    }
}
