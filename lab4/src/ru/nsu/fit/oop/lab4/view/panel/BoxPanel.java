package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.Complex;
import ru.nsu.fit.oop.lab4.view.table_model.TrainsTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BoxPanel extends JPanel {

    private int gridx = 0;
    private int gridy = 0;
    private int gridwidth = 1;
    private int gridheight = 1;
    private int weightx = 100;
    private int weighty = 100;
    private final Map<String,JPanel> panelMap = new HashMap<>();

    public BoxPanel(Complex complex) {
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        addPanel("station", new JPanel());
        addPanel("trains", new ComplexPanel(Color.white,"trains",new TrainsTableModel(
                complex.getTrains(),complex.getGoodNames())));
        addPanel("consumers", new JPanel());
        addPanel("factories", new JPanel());
        addPanel("departure storages", new JPanel());
        addPanel("destination storages", new JPanel());
    }

    private void addPanel(String name, JPanel panel) {
        add(panel);
        panelMap.put(name,panel);
    }

    public void setPanelVisible(String name, boolean b) {
        panelMap.get(name).setVisible(b);
    }
}
