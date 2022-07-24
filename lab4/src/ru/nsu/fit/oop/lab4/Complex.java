package ru.nsu.fit.oop.lab4;

import ru.nsu.fit.oop.lab4.exception.InvalidConfigException;
import ru.nsu.fit.oop.lab4.goods.Factory;
import ru.nsu.fit.oop.lab4.goods.Storage;
import ru.nsu.fit.oop.lab4.station.Station;
import ru.nsu.fit.oop.lab4.trains.Depot;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Complex {

    private final List<Factory> factories = new ArrayList<>();
    private final Map<String, Storage> departureStorages = new HashMap<>();
    private final Map<String, Storage> destinationStorages = new HashMap<>();
    private Station station;
    private final Depot depot;
    private final List<Consumer> consumers = new ArrayList<>();

    public Complex() throws IOException, InvalidConfigException {
        Properties goodsConfig = new Properties();
        InputStream goodsStream = this.getClass().getResourceAsStream("goods/goods.properties");
        if (null == goodsStream) {
            throw new InvalidConfigException("Wasn't able to open goods config.");
        }
        goodsConfig.load(goodsStream);
        int numberOfGoodTypes = goodsConfig.size() / 9;
        createStoragesAndConsumers(numberOfGoodTypes, goodsConfig);
        createStation();
        createFactories(numberOfGoodTypes, goodsConfig);
        depot = new Depot(numberOfGoodTypes, station, goodsConfig);
    }

    private void createStoragesAndConsumers(int numberOfGoodTypes, Properties goodsConfig) {
        for (int i = 0; i < numberOfGoodTypes; ++i) {
            String goodName = goodsConfig.getProperty(Integer.valueOf(10 * i).toString());
            departureStorages.put(goodName, new Storage(goodName,
                    Integer.parseInt(goodsConfig.getProperty(String.valueOf(10 * i + 1)))));
            Storage storage;
            destinationStorages.put(goodName, storage = new Storage(goodName,
                    Integer.parseInt(goodsConfig.getProperty(String.valueOf(10 * i + 2)))));
            for (int j = 0; j < Integer.parseInt(goodsConfig.getProperty(String.valueOf(10 * i + 9))); ++j) {
                consumers.add(new Consumer(storage));
            }
        }
    }

    private void createStation() throws InvalidConfigException, IOException {
        InputStream stationStream = this.getClass().getResourceAsStream("station/station.properties");
        if (null == stationStream) {
            throw new InvalidConfigException("Wasn't able to open station config");
        }
        Properties stationConfig = new Properties();
        stationConfig.load(stationStream);
        station = new Station(Integer.parseInt(stationConfig.getProperty("distance")),
                Integer.parseInt(stationConfig.getProperty("numberOfLoadingTracks")),
                Integer.parseInt(stationConfig.getProperty("numberOfUnloadingTrack")),
                Integer.parseInt(stationConfig.getProperty("numberOfDepartureDestinationTracks")),
                Integer.parseInt(stationConfig.getProperty("numberOfDestinationDepartureTracks")),
                departureStorages, destinationStorages);
    }

    private void createFactories(int numberOfGoodTypes, Properties goodsConfig) {
        for (int i = 0; i < numberOfGoodTypes; ++i) {
            factories.add(new Factory(goodsConfig.getProperty(String.valueOf(10 * i)),
                    Integer.parseInt(goodsConfig.getProperty(String.valueOf(10 * i + 5))),
                    Integer.parseInt(goodsConfig.getProperty(String.valueOf(10 * i + 6))),
                    Integer.parseInt(goodsConfig.getProperty(String.valueOf(10 * i + 7))),
                    Integer.parseInt(goodsConfig.getProperty(String.valueOf(10 * i + 8))),
                    departureStorages.get(goodsConfig.getProperty(String.valueOf(10 * i))),
                    i));
        }
    }

    public void start() throws InterruptedException {
        for (Factory factory : factories) {
            Thread t = new Thread(factory);
            t.start();
        }
        depot.start();
        for (Consumer consumer : consumers) {
            Thread t = new Thread(consumer);
            t.start();
        }
    }
}
