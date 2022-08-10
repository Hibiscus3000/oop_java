package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.station.Station;
import ru.nsu.fit.oop.lab4.view.table.ObservableLoggingTable;
import ru.nsu.fit.oop.lab4.view.table_model.StationTableModel;

import javax.swing.*;
import java.awt.*;

public class StationPanel extends ObservableLoggingPanel{

    public StationPanel(Station station) {
        super(Color.CYAN, "station", new ObservableLoggingTable(new StationTableModel(station)));
        JTextArea textArea = new JTextArea("distance between departure and destination station = "
                + station.getDistance() +  " m.");
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        add(textArea,BorderLayout.NORTH);
    }
}
