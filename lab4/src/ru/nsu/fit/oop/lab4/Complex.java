package ru.nsu.fit.oop.lab4;

import ru.nsu.fit.oop.lab4.exception.BadNumberOfTracks;
import ru.nsu.fit.oop.lab4.exception.InvalidConfigException;
import ru.nsu.fit.oop.lab4.good.Factory;
import ru.nsu.fit.oop.lab4.good.Storage;
import ru.nsu.fit.oop.lab4.station.Station;
import ru.nsu.fit.oop.lab4.train.Depot;
import ru.nsu.fit.oop.lab4.train.Train;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Complex {

    private final List<Factory> factories = new ArrayList<>();
    private final List<Thread> factoryThreads = new ArrayList<>();
    private final Map<String, Storage> departureStorages = new HashMap<>();
    private final Map<String, Storage> destinationStorages = new HashMap<>();
    private Station station;
    private final Depot depot;
    private final List<Consumer> consumers = new ArrayList<>();
    private final List<Thread> consumerThreads = new ArrayList<>();
    private final List<String> goodNames = new ArrayList<>();
    private boolean working = false;

    public Complex() throws IOException, InvalidConfigException, BadNumberOfTracks {
        Properties goodsConfig = new Properties();
        InputStream goodsStream = this.getClass().getResourceAsStream("good/goods.properties");
        if (null == goodsStream) {
            InvalidConfigException e = new InvalidConfigException("Wasn't able to open goods config.");
            Main.logger.throwing(this.getClass().getSimpleName(), "Complex", e);
            throw e;
        }
        goodsConfig.load(goodsStream);
        Main.logger.config("Loaded goods config.");
        int numberOfGoodTypes = goodsConfig.size() / 7;
        Main.logger.info("Creating storages and consumers...");
        createStoragesAndConsumers(numberOfGoodTypes, goodsConfig);
        Main.logger.info("Storages and consumers created.");
        Main.logger.info("Creating station...");
        createStation();
        Main.logger.info("Station created.");
        Main.logger.info("Creating factories...");
        createFactories(numberOfGoodTypes, goodsConfig);
        Main.logger.info("Factories created.");
        Main.logger.info("Creating depot...");
        depot = new Depot(station, goodsConfig);
        Main.logger.info("Depot created.");
    }

    private void createStoragesAndConsumers(int numberOfGoodTypes, Properties goodsConfig) throws IOException {
        int numberOfConsumersTotal = 0;
        for (int i = 0; i < numberOfGoodTypes; ++i) {
            String goodName = goodsConfig.getProperty(Integer.valueOf(10 * i).toString());
            departureStorages.put(goodName, new Storage(goodName,
                    Integer.parseInt(goodsConfig.getProperty(String.valueOf(10 * i + 1))),"Departure"));
            Storage storage;
            Main.logger.config("created departure storage for " + goodName);
            destinationStorages.put(goodName, storage = new Storage(goodName,
                    Integer.parseInt(goodsConfig.getProperty(String.valueOf(10 * i + 2))),"Destination"));
            Main.logger.config("created destination storage for " + goodName);
            int j;
            for (j = 0; j < Integer.parseInt(goodsConfig.getProperty(String.valueOf(10 * i + 7))); ++j) {
                Main.logger.config("created Consumer #" + j + " for " + goodName);
                consumers.add(new Consumer(storage, numberOfConsumersTotal + j));
            }
            numberOfConsumersTotal += j;
        }
    }

    private void createStation() throws InvalidConfigException, IOException, BadNumberOfTracks {
        InputStream stationStream = this.getClass().getResourceAsStream("station/station.properties");
        if (null == stationStream) {
            InvalidConfigException e = new InvalidConfigException("Wasn't able to open station config");
            Main.logger.throwing(this.getClass().getSimpleName(), "createStation", e);
            throw e;
        }
        Properties stationConfig = new Properties();
        stationConfig.load(stationStream);
        Main.logger.config("Loaded station config.");
        station = new Station(Integer.parseInt(stationConfig.getProperty("distance")),
                Integer.parseInt(stationConfig.getProperty("numberOfLoadingTracks")),
                Integer.parseInt(stationConfig.getProperty("numberOfUnloadingTracks")),
                Integer.parseInt(stationConfig.getProperty("numberOfDepartureDestinationTracks")),
                Integer.parseInt(stationConfig.getProperty("numberOfDestinationDepartureTracks")),
                departureStorages, destinationStorages);
    }

    private void createFactories(int numberOfGoodTypes, Properties goodsConfig) throws IOException {
        for (int i = 0; i < numberOfGoodTypes; ++i) {
            String goodName = goodsConfig.getProperty(String.valueOf(10 * i));
            goodNames.add(goodName);
            Main.logger.config("Creating factory for " + goodName + "...");
            factories.add(new Factory(goodName,
                    Integer.parseInt(goodsConfig.getProperty(String.valueOf(10 * i + 3))),
                    Integer.parseInt(goodsConfig.getProperty(String.valueOf(10 * i + 4))),
                    Integer.parseInt(goodsConfig.getProperty(String.valueOf(10 * i + 5))),
                    Integer.parseInt(goodsConfig.getProperty(String.valueOf(10 * i + 6))),
                    departureStorages.get(goodName),
                    i));
        }
    }

    public void start() throws InterruptedException, IOException {
        working = true;
        depot.start();
        Main.logger.config("Starting depot... ");
        for (Factory factory : factories) {
            Thread t = new Thread(factory);
            Main.logger.config("Starting factory producing " + factory.getGoodName() + "...");
            factoryThreads.add(t);
            t.start();
        }
        for (Consumer consumer : consumers) {
            Thread t = new Thread(consumer);
            Main.logger.config("Starting consumer interested in " + consumer.getGoodName() + "...");
            consumerThreads.add(t);
            t.start();
        }
    }

    public void stop() {
        Main.logger.info("Complex was stopped");
        depot.stop();
        stopFactoriesAndConsumers();
    }

    public void stopUrgently() {
        Main.logger.info("Complex was urgently stopped");
        depot.stopUrgently();
        stopFactoriesAndConsumers();
    }

    private void stopFactoriesAndConsumers() {
        for (Thread factoryThread : factoryThreads) {
            factoryThread.interrupt();
        }
        for (Thread consumerThread : consumerThreads) {
            consumerThread.interrupt();
        }
    }

    public List<Factory> getFactories() {
        return factories;
    }

    public Map<String, Storage> getDepartureStorages() {
        return departureStorages;
    }

    public Map<String, Storage> getDestinationStorages() {
        return destinationStorages;
    }

    public Station getStation() {
        return station;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public List<Train> getTrains() {
        return depot.getTrains();
    }

    public String[] getGoodNames() {
        return goodNames.toArray(new String[goodNames.size()]);
    }

    public void setDepotWindowHandlerVisible(boolean b) {
        depot.setWindowHandlerVisible(b);
    }

    public boolean isWorking() {
        return working;
    }

    public Observer setDepotObserver(Observer observer) {
        depot.setObserver(observer);
        return observer;
    }
}
