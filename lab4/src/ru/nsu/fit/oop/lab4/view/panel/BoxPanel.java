package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.Complex;
import ru.nsu.fit.oop.lab4.Consumer;
import ru.nsu.fit.oop.lab4.ObservableLogging;
import ru.nsu.fit.oop.lab4.good.Factory;
import ru.nsu.fit.oop.lab4.good.Good;
import ru.nsu.fit.oop.lab4.good.Storage;
import ru.nsu.fit.oop.lab4.view.table.ComplexTable;
import ru.nsu.fit.oop.lab4.view.table.ObservableLoggingTable;
import ru.nsu.fit.oop.lab4.view.table_model.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class BoxPanel extends JPanel {

    private final Map<String, JPanel> panelMap = new HashMap<>();

    public BoxPanel(Complex complex) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        List<Factory> factories = complex.getFactories();
        List<Good> samples = new ArrayList<>();
        for (Factory factory : factories) {
            samples.add(factory.getSample());
        }
        addPanel("goods", new ComplexPanel(Color.GREEN, "goods",
                new ComplexTable(new GoodsTableModel(samples))));
        addPanel("station", new StationPanel(complex.getStation()));
        addPanel("trains", new ObservableLoggingPanel(Color.WHITE, "trains",
                (ObservableLoggingTable) complex.setDepotObserver(new ObservableLoggingTable(
                        new TrainsTableModel(complex.getTrains(), complex.getGoodNames())))));
        ObservableLoggingTable factoriesTable = new ObservableLoggingTable(new FactoryTableModel(factories));
        setObserver(factories, factoriesTable);
        addPanel("factories", new ObservableLoggingPanel(Color.ORANGE, "factories", factoriesTable
        ));
        List<Storage> departureStorages = new ArrayList<Storage>(complex.getDepartureStorages().values());
        ObservableLoggingTable departureStoragesTable = new ObservableLoggingTable(new StorageTableModel(departureStorages));
        setObserver(departureStorages, departureStoragesTable);
        addPanel("departure storages", new ObservableLoggingPanel(Color.LIGHT_GRAY, "departure storages",
                departureStoragesTable));
        List<Storage> destinationStorages = new ArrayList<Storage>(complex.getDestinationStorages().values());
        ObservableLoggingTable destinationStoragesTable = new ObservableLoggingTable(new StorageTableModel(destinationStorages));
        setObserver(destinationStorages, destinationStoragesTable);
        addPanel("destination storages", new ObservableLoggingPanel(Color.LIGHT_GRAY, "destination storages",
                destinationStoragesTable));
        List<Consumer> consumers = complex.getConsumers();
        ObservableLoggingTable consumersTable = new ObservableLoggingTable(new ConsumersTableModel(consumers));
        setObserver(consumers, consumersTable);
        addPanel("consumers", new ObservableLoggingPanel(Color.PINK, "consumers",
                consumersTable));
    }

    private void addPanel(String name, ComplexPanel panel) {
        add(panel);
        panelMap.put(name, panel);
    }

    private void setObserver(List<? extends ObservableLogging> observableLoggingList, Observer observer) {
        for (ObservableLogging observableLogging : observableLoggingList) {
            observableLogging.addObserver(observer);
        }
    }

    public void setPanelVisible(String name, boolean b) {
        panelMap.get(name).setVisible(b);
    }
}
