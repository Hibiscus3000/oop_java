package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.Complex;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GridBagPanel extends JPanel {

    private int gridx = 0;
    private int gridy = 0;
    private int gridwidth = 1;
    private int gridheight = 1;
    private int weightx = 100;
    private int weighty = 100;
    private final Map<String,JPanel> panelMap = new HashMap<>();

    public GridBagPanel(Complex complex) {
        setLayout(new GridBagLayout());
        addPanel("station", new StationPanel(complex.getStation()));
        addPanel("trains", new TrainsPanel(complex.getTrains()));
        addPanel("consumers", new ConsumersPanel(complex.getConsumers()));
        addPanel("factories", new FactoriesPanel(complex.getFactories()));
        addPanel("departure storages", new StoragesPanel(complex.getDepartureStorages()));
        addPanel("destination storages", new StoragesPanel(complex.getDestinationStorages()));
    }

    private void addPanel(String name, JPanel panel) {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = gridx;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = GridBagConstraints.REMAINDER;
        gridBagConstraints.weightx = weightx;
        gridBagConstraints.weighty = weighty;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        add(panel,gridBagConstraints);
        panelMap.put(name,panel);
        if (0 == (gridx + 1) % 3) {
            gridx = 0;
            ++gridy;
        } else {
            ++gridx;
        }
    }

    public void setPanelVisible(String name, boolean b) {
        panelMap.get(name).setVisible(b);
    }
}
