package ru.nsu.fit.oop.lab4.trains;

import ru.nsu.fit.oop.lab4.exception.InvalidConfigException;
import ru.nsu.fit.oop.lab4.station.Station;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Depot {

    private final int numberOfWorkers = 4;
    private final ScheduledExecutorService workers = Executors.newScheduledThreadPool(numberOfWorkers);
    private final Properties trainConfig;
    private final int numberOfTrains;
    private final List<Train> trains;
    private final Station station;
    private final Properties goodsConfig;

    public Depot(int numberOfGoodTypes, Station station, Properties goodsConfig) throws InvalidConfigException {
        trainConfig = new Properties();
        var stream = this.getClass().getResourceAsStream("trains.properties");
        if (null == stream)
            throw new InvalidConfigException("Bad trains Config.");
        numberOfTrains = trainConfig.size() / 4;
        trains = new ArrayList<>(numberOfTrains);
        this.station = station;
        this.goodsConfig = goodsConfig;
    }

    private Map<String,Integer> parseCapacities(int trainNumber, int numberOfGoodTypes,
                                                Properties goodsConfig) {
        Map<String, Integer> capacity = new HashMap<>();
        String capacities = trainConfig.getProperty(Integer.valueOf(10 * trainNumber).toString());
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

    public void start() {
        int numberOfGoodTypes = goodsConfig.size() / 4;
        for (int i = 0; i < numberOfTrains; ++i) {
            trains.add(new Train(parseCapacities(i,numberOfGoodTypes, goodsConfig),
                    Integer.parseInt(trainConfig.getProperty(Integer.valueOf(10 * i + 1).toString())),
                    station,Integer.parseInt(trainConfig.getProperty(Integer.valueOf(10 * i + 2).toString())),
                    Integer.parseInt(trainConfig.getProperty(Integer.valueOf(10 * i + 3).toString()))));
        }
    }
}
