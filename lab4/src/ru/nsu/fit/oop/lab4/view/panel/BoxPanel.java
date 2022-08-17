package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.Complex;
import ru.nsu.fit.oop.lab4.Consumer;
import ru.nsu.fit.oop.lab4.logging.ObservableLogging;
import ru.nsu.fit.oop.lab4.good.Factory;
import ru.nsu.fit.oop.lab4.good.Good;
import ru.nsu.fit.oop.lab4.good.Storage;
import ru.nsu.fit.oop.lab4.station.Station;
import ru.nsu.fit.oop.lab4.view.table.ComplexTable;
import ru.nsu.fit.oop.lab4.view.table.ObservableLoggingTable;
import ru.nsu.fit.oop.lab4.view.table.StationTable;
import ru.nsu.fit.oop.lab4.view.table.TrainsTable;
import ru.nsu.fit.oop.lab4.view.table.table_model.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class BoxPanel extends JPanel {

    private final Map<String, ComplexPanel> panelMap = new HashMap<>();
    private final List<JPanel> panels = new ArrayList<>();
    private final int maxNumberOfPanels = 3;
    private int panelCount = 0;

    public BoxPanel(Complex complex, BorderPanel.CheckBoxPanel checkBoxPanel, double sizeReduction,
                    double frameSizeScale) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        List<Factory> factories = complex.getFactories();
        List<Good> samples = new ArrayList<>();
        for (Factory factory : factories) {
            samples.add(factory.getSample());
        }
        addPanel("goods", new ComplexPanel(Color.GREEN, "goods",
                new ComplexTable(new GoodsTableModel(samples),frameSizeScale), sizeReduction,
                frameSizeScale), checkBoxPanel.isSelected("goods"));
        Station station = complex.getStation();
        StationTable stationTable;
        addPanel("station", new StationPanel(station,
                stationTable = new StationTable(new StationTableModel(station),frameSizeScale),
                sizeReduction, frameSizeScale), checkBoxPanel.isSelected("station"));
        setStationObserver(station, stationTable);
        addPanel("trains", new RowLoggingPanel(Color.WHITE, "trains",
                (ObservableLoggingTable) complex.setDepotObserver(new TrainsTable(complex.getTrains(),frameSizeScale,
                        complex.getGoodNames())), sizeReduction, frameSizeScale), checkBoxPanel.isSelected("trains"));
        ObservableLoggingTable factoriesTable = new ObservableLoggingTable(new FactoryTableModel(factories),frameSizeScale);
        setObserver(factories, factoriesTable);
        addPanel("factories", new RowLoggingPanel(Color.ORANGE, "factories", factoriesTable,
                sizeReduction, frameSizeScale), checkBoxPanel.isSelected("factories"));
        List<Storage> departureStorages = new ArrayList<>(complex.getDepartureStorages().values());
        ObservableLoggingTable departureStoragesTable = new ObservableLoggingTable(new StorageTableModel(departureStorages),frameSizeScale);
        setObserver(departureStorages, departureStoragesTable);
        addPanel("departure storages", new RowLoggingPanel(Color.LIGHT_GRAY, "departure storages",
                departureStoragesTable, sizeReduction, frameSizeScale), checkBoxPanel.isSelected("departure storages"));
        List<Storage> destinationStorages = new ArrayList<>(complex.getDestinationStorages().values());
        ObservableLoggingTable destinationStoragesTable = new ObservableLoggingTable(new StorageTableModel(destinationStorages),frameSizeScale);
        setObserver(destinationStorages, destinationStoragesTable);
        addPanel("destination storages", new RowLoggingPanel(Color.LIGHT_GRAY, "destination storages",
                destinationStoragesTable, sizeReduction, frameSizeScale), checkBoxPanel.isSelected("destination storages"));
        List<Consumer> consumers = complex.getConsumers();
        ObservableLoggingTable consumersTable = new ObservableLoggingTable(new ConsumersTableModel(consumers),frameSizeScale);
        setObserver(consumers, consumersTable);
        addPanel("consumers", new RowLoggingPanel(Color.PINK, "consumers",
                consumersTable, sizeReduction, frameSizeScale), checkBoxPanel.isSelected("consumers"));
    }

    public void resizeAllPanels() {
        for (Map.Entry<String, ComplexPanel> entry : panelMap.entrySet()) {
            entry.getValue().resizeComplexTable();
        }
    }

    private void addPanel(String name, ComplexPanel newPanel, boolean isVisible) {
        if (0 == panelCount % maxNumberOfPanels)
            createNewPanel();
        JPanel xPanel = panels.get(panels.size() - 1);
        newPanel.setVisible(isVisible);
        xPanel.add(newPanel);
        panelMap.put(name, newPanel);
        ++panelCount;
    }

    private void createNewPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panels.add(panel);
        add(panel);
    }

    private void setObserver(List<? extends ObservableLogging> observableLoggingList, Observer observer) {
        for (ObservableLogging observableLogging : observableLoggingList) {
            observableLogging.addObserver(observer);
        }
    }

    private void setStationObserver(Station station, StationTable stationTable) {
        station.addObserver(stationTable);
    }

    public void setPanelVisible(String name, boolean b) {
        panelMap.get(name).setVisible(b);
    }
}
