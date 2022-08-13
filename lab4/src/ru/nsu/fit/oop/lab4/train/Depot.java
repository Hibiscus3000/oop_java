package ru.nsu.fit.oop.lab4.train;

import ru.nsu.fit.oop.lab4.ObservableLogging;
import ru.nsu.fit.oop.lab4.exception.InvalidConfigException;
import ru.nsu.fit.oop.lab4.station.Station;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Depot extends ObservableLogging {

    private final int numberOfWorkers = 4;
    private final ScheduledExecutorService workers = Executors.newScheduledThreadPool(numberOfWorkers);
    private final Properties trainsConfig;
    private final int numberOfTrains;
    private volatile List<Train> trains;
    private volatile List<Thread> threads;
    private final Station station;
    private final Properties goodsConfig;
    private volatile boolean stop = false;
    private Observer observer;

    public Depot(Station station, Properties goodsConfig) throws InvalidConfigException,
            IOException {
        super(Depot.class.getName(),Depot.class.getSimpleName(), "depot log");
        trainsConfig = new Properties();
        var stream = this.getClass().getResourceAsStream("trains.properties");
        if (null == stream)
            throw new InvalidConfigException("Wasn't able to open trains config.");
        trainsConfig.load(stream);
        logger.config("Loaded trains config.");
        numberOfTrains = trainsConfig.size() / 4;
        trains = new ArrayList<>(numberOfTrains);
        threads = new ArrayList<>(numberOfTrains);
        this.station = station;
        this.goodsConfig = goodsConfig;
    }

    private Map<String, Integer> parseCapacities(int trainNumber, int numberOfGoodTypes,
                                                 Properties goodsConfig) {
        Map<String, Integer> capacity = new HashMap<>();
        String capacities = trainsConfig.getProperty(Integer.valueOf(10 * trainNumber).toString());
        int beginIndex = 0;
        int endIndex;
        for (int i = 0; i < numberOfGoodTypes; ++i) {
            Integer goodCapacity = Integer.parseInt(capacities.substring(beginIndex, (endIndex =
                    capacities.indexOf(" ", beginIndex)) != -1 ? endIndex : capacities.length()));
            beginIndex = endIndex + 1;
            capacity.put(goodsConfig.getProperty(Integer.valueOf(10 * i).toString()), goodCapacity);
        }
        return capacity;
    }

    public void start() throws InterruptedException, IOException {
        int numberOfGoodTypes = goodsConfig.size() / 7;
        for (int i = 0; i < numberOfTrains; ++i) {
            Train sample = new Train(parseCapacities(i, numberOfGoodTypes, goodsConfig),
                    Integer.parseInt(trainsConfig.getProperty(Integer.valueOf(10 * i + 1).toString())),
                    station, Integer.parseInt(trainsConfig.getProperty(Integer.valueOf(10 * i + 2).toString())),
                    Integer.parseInt(trainsConfig.getProperty(Integer.valueOf(10 * i + 3).toString())), i);
            createTrain(sample);
        }
    }

    private void createTrain(Train sample) throws InterruptedException {
        Train train = new Train(sample,observer);
        trains.add(train);
        logger.info("Created train #" + train.getId() + ".");
        Thread thread = new Thread(train);
        threads.add(thread);
        thread.start();
        logger.info("Started train #" + train.getId() + ".");
        workers.schedule(() -> {
            try {
                disposeTrain(train, thread);
            } catch (InterruptedException e) {
                workers.shutdownNow();
                logFinalInfo();
            }
        }, train.getDepreciationTimeMillis(), TimeUnit.MILLISECONDS);
    }

    private void disposeTrain(Train train, Thread thread) throws InterruptedException {
        train.mark();
        synchronized (train) {
            while (!train.isDisposed()) {
                train.wait();
            }
        }
        logger.info("Depot disposed of the train #" + train.getId() + ".");
        trains.remove(train);
        threads.remove(thread);
        createTrain(train);
    }

    @Override
    public void logFinalInfo() {
        logger.info("Depot workers were interrupted and shutdown.");
    }

    public void stop() {
        stop = true;
    }

    public void stopUrgently() {
        workers.shutdownNow();
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public List<Train> getTrains() {
        return trains;
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }

}
