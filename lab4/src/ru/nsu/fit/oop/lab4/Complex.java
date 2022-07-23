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

    private final List<Factory> factories;
    private final Depot depot;
    private final List<Consumer> consumers;

    public Complex() throws IOException, InvalidConfigException {
        Properties goodsConfig = new Properties();
        InputStream goodsStream = this.getClass().getResourceAsStream("goods/goods.properties");
        if (null == goodsStream) {
            throw new InvalidConfigException("Wasn't able to open goods config.");
        }
        goodsConfig.load(goodsStream);
        int numberOfGoods = goodsConfig.size() / 9;
        Map<String, Storages> departureStorages = new HashMap<>(numberOfGoods);
        Map<String, Storages> destinationStorages = new HashMap<>(numberOfGoods);
        for (int i = 0; i < numberOfGoods; ++i) {
            String goodName = goodsConfig.getProperty(Integer.valueOf(10 * i).toString());
            departureStorages.put(goodName, new Storages(goodName,
                    createStoragesList(i, 1, goodsConfig)));
            departureStorages.put(goodName, new Storages(goodName,
                    createStoragesList(i, 3, goodsConfig)));
        }
        InputStream stationStream = this.getClass().getResourceAsStream("station/station.properties");
        if (null == stationStream) {
            throw new InvalidConfigException("Wasn't able to open station config");
        }
        Properties stationConfig = new Properties();
        stationConfig.load(stationStream);
        Station station = new Station(Integer.parseInt(stationConfig.getProperty("distance")),
                stationConfig.getProperty("numberOfLoadingTracks"),
                stationConfig.getProperty("numberOfUnloadingTrack"),stationConfig.getProperty("numberOfDepartureDestinationTracks"),
                stationConfig.getProperty("numberOfDestinationDepartureTracks"),departureStorages,destinationStorages);
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
                    endIndex == -1 ? capacities.length() : endIndex))));
            beginIndex = endIndex + 1;
        }
        return storageList;
    }

    public void start() {

    }
}
