package ru.nsu.fit.oop.lab4;

import ru.nsu.fit.oop.lab4.exception.InvalidConfigException;
import ru.nsu.fit.oop.lab4.goods.Factory;
import ru.nsu.fit.oop.lab4.goods.Storage;
import ru.nsu.fit.oop.lab4.goods.Storages;
import ru.nsu.fit.oop.lab4.station.Station;
import ru.nsu.fit.oop.lab4.trains.Depot;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Complex {

    private final List<Factory> factories = new ArrayList<>();
    private final Map<String,Storages> departureStorages = new HashMap<>();
    private final Map<String,Storages> destinationStorages = new HashMap<>();
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
        createStoragesAndConsumers(numberOfGoodTypes,goodsConfig);
        createStation();
        createFactories(numberOfGoodTypes,goodsConfig);
        depot = new Depot(numberOfGoodTypes,station,goodsConfig);
    }

    //arg number refers to either departure storages or destination storages
    private List<Storage> createStoragesList(int goodNumber, int argNumber, Properties goodsConfig) {
        int numberOfStorages = Integer.parseInt(goodsConfig.getProperty(Integer.valueOf(10 * goodNumber + argNumber).toString()));
        List<Storage> storageList = new ArrayList<>(numberOfStorages);
        int beginIndex = 0, endIndex;
        String capacities = goodsConfig.getProperty(String.valueOf(10 * goodNumber + argNumber + 1));
        for (int i = 0; i < numberOfStorages; ++i) {
            endIndex = capacities.indexOf(" ", beginIndex);
            storageList.add(new Storage(Integer.parseInt(capacities.substring(beginIndex,
                    endIndex == -1 ? capacities.length() : endIndex)),i));
            beginIndex = endIndex + 1;
        }
        return storageList;
    }

    private void createStoragesAndConsumers(int numberOfGoodTypes, Properties goodsConfig) {
        for (int i = 0; i < numberOfGoodTypes; ++i) {
            String goodName = goodsConfig.getProperty(Integer.valueOf(10 * i).toString());
            departureStorages.put(goodName, new Storages(goodName,
                    createStoragesList(i, 1, goodsConfig)));
            Storages storages;
            destinationStorages.put(goodName, storages = new Storages(goodName,
                    createStoragesList(i, 3, goodsConfig)));
            for (int j = 0; j < Integer.parseInt(goodsConfig.getProperty(String.valueOf(10 * i + 9))); ++j) {
                consumers.add(new Consumer(storages));
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
                departureStorages,destinationStorages);
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
