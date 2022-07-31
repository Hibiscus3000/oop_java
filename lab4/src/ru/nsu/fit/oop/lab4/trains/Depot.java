package ru.nsu.fit.oop.lab4.trains;

import ru.nsu.fit.oop.lab4.Logging;
import ru.nsu.fit.oop.lab4.Main;
import ru.nsu.fit.oop.lab4.exception.InvalidConfigException;
import ru.nsu.fit.oop.lab4.station.Station;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Depot implements Logging {

    private final int numberOfWorkers = 4;
    private final ScheduledExecutorService workers = Executors.newScheduledThreadPool(numberOfWorkers);
    private final Properties trainsConfig;
    private final int numberOfTrains;
    private final List<Train> trains;
    private final Station station;
    private final Properties goodsConfig;
    private final Logger logger;

    public Depot(Station station, Properties goodsConfig) throws InvalidConfigException,
            IOException {
        logger = Logger.getLogger(this.getClass().getSimpleName());
        logger.setLevel(Level.ALL);
        FileHandler fileHandler = new FileHandler("depot_log%g.txt",
                1000000, 1, false);
        fileHandler.setLevel(Level.ALL);
        logger.addHandler(fileHandler);
        trainsConfig = new Properties();
        var stream = this.getClass().getResourceAsStream("trains.properties");
        if (null == stream)
            throw new InvalidConfigException("Wasn't able to open trains config.");
        trainsConfig.load(stream);
        Main.logger.config("Loaded trains config.");
        numberOfTrains = trainsConfig.size() / 4;
        trains = new ArrayList<>(numberOfTrains);
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
            trains.add(sample);
            createTrain(sample);
        }
    }

    private void createTrain(Train sample) throws InterruptedException {
        Train train = new Train(sample);
        logger.info("Created train #" + train.getId() + ".");
        Thread t = new Thread(train);
        t.start();
        logger.info("Started train #" + train.getId() + ".");
        workers.schedule(() -> {
            try {
                disposeTrain(train, t);
            } catch (InterruptedException e) {
                workers.shutdownNow();
                logFinalInfo();
            }
        }, sample.getDepreciationTimeMillis(), TimeUnit.MILLISECONDS);
    }

    private void disposeTrain(Train train, Thread t) throws InterruptedException {
        train.mark();
        synchronized (train) {
            while (!train.isDisposed()) {
                train.wait();
            }
        }
        logger.info("Depot disposed of the train #" + train.getId() + ".");
        createTrain(train);
    }

    @Override
    public void logFinalInfo() {
        logger.info("Depot workers were interrupted and shutdown.");
    }
}
