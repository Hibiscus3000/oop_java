package ru.nsu.fit.oop.lab4;

import ru.nsu.fit.oop.lab4.goods.Storage;
import ru.nsu.fit.oop.lab4.goods.Storages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        Properties goodsConfig = new Properties();
        var stream = Main.class.getResourceAsStream("goods/goods.properties");
        try {
            goodsConfig.load(stream);
            int numberOfGoods = goodsConfig.size() / 9;
            List<Storages> departureStorages = new ArrayList<>(numberOfGoods);
            List<Storages> destinationStorages = new ArrayList<>(numberOfGoods);
            for (int i = 0; i < numberOfGoods; ++i) {
                int numberOfStoragesDeparture = Integer.parseInt(goodsConfig.getProperty(Integer.valueOf(10 * i + 1).toString()));
                List<Storage> storageList = createStoragesList(numberOfStoragesDeparture, i, 2, goodsConfig);
                int capacityAll = 0;
                for (Storage storage : storageList) {
                    capacityAll += storage.getCapacity();
                }
                departureStorages.add(new Storages(goodsConfig.getProperty(Integer.valueOf(10 * i).toString()),
                        storageList, capacityAll));
                int numberOfStoragesDestination = Integer.parseInt(goodsConfig.getProperty(Integer.valueOf(10 * i + 3).toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //arg number refers to either departure storages capacity or destination storages capacity
    private static List<Storage> createStoragesList(int numberOfStorages, int goodNumber,
                                                    int argNumber, Properties goodsConfig) {
        List<Storage> storageList = new ArrayList<>(numberOfStorages);
        int beginIndex = 0, endIndex;
        String capacities = goodsConfig.getProperty(String.valueOf(10 * goodNumber + argNumber));
        for (int i = 0; i < numberOfStorages; ++i) {
            endIndex = capacities.indexOf("", beginIndex);
            storageList.add(new Storage(Integer.parseInt(capacities.substring(beginIndex,
                    endIndex == -1 ? capacities.length() : endIndex))));
            beginIndex = endIndex + 1;
        }
        return storageList;
    }
}
