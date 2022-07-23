package ru.nsu.fit.oop.lab4.trains;

import ru.nsu.fit.oop.lab4.exception.InvalidConfigException;
import ru.nsu.fit.oop.lab4.station.Station;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Depot {

    private final int numberOfWorkers = 4;
    private final ScheduledExecutorService workers = Executors.newScheduledThreadPool(numberOfWorkers);
    private final Properties trainsConfig;
    private final int numberOfTrains;
    private final List<Train> trains;
    private final Station station;
    private final Properties goodsConfig;

    public Depot(int numberOfGoodTypes, Station station, Properties goodsConfig) throws InvalidConfigException, IOException {
        trainsConfig = new Properties();
        var stream = this.getClass().getResourceAsStream("trains.properties");
        if (null == stream)
            throw new InvalidConfigException("Bad trains config.");
        trainsConfig.load(stream);
        numberOfTrains = trainsConfig.size() / 4;
        trains = new ArrayList<>(numberOfTrains);
        this.station = station;
        this.goodsConfig = goodsConfig;
    }

    private Map<String,Integer> parseCapacities(int trainNumber, int numberOfGoodTypes,
                                                Properties goodsConfig) {
        Map<String, Integer> capacity = new HashMap<>();
        String capacities = trainsConfig.getProperty(Integer.valueOf(10 * trainNumber).toString());
        int beginIndex = 0;
        int endIndex;
        for (int i = 0; i < numberOfGoodTypes; ++i) {
            Integer goodCapacity = Integer.parseInt(capacities.substring(beginIndex, (endIndex =
                    capacities.indexOf(" ",beginIndex)) != -1 ? endIndex : capacities.length()));
            beginIndex = endIndex + 1;
            capacity.put(goodsConfig.getProperty(Integer.valueOf(10 * i).toString()),goodCapacity);
        }
        return capacity;
    }

    public void start() throws InterruptedException {
        int numberOfGoodTypes = goodsConfig.size() / 4;
        for (int i = 0; i < numberOfTrains; ++i) {
            Train sample = new Train(parseCapacities(i, numberOfGoodTypes, goodsConfig),
                    Integer.parseInt(trainsConfig.getProperty(Integer.valueOf(10 * i + 1).toString())),
                    station, Integer.parseInt(trainsConfig.getProperty(Integer.valueOf(10 * i + 2).toString())),
                    Integer.parseInt(trainsConfig.getProperty(Integer.valueOf(10 * i + 3).toString())));
            trains.add(sample);
            createTrain(sample);
        }
    }

    private void createTrain(Train sample) throws InterruptedException {
        Train train = new Train(sample);
        Thread t = new Thread(train);
        t.start();
        workers.schedule(() -> {
            try {
                disposeTrain(train,t);
            } catch (InterruptedException e) {
                //DO SMT!!!!
            }
        },sample.getDepreciationTimeSec(),TimeUnit.SECONDS);
    }

    private void disposeTrain(Train train, Thread t) throws InterruptedException {
        t.interrupt();
        t.wait();
        createTrain(train);
    }
}
