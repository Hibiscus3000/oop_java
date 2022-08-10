package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.station.Station;
import ru.nsu.fit.oop.lab4.view.panel.action.ShowStationLogAction;
import ru.nsu.fit.oop.lab4.view.table.StationTable;

import javax.swing.*;
import java.awt.*;

public class StationPanel extends ObservableLoggingPanel{

    public StationPanel(Station station, StationTable table) {
        super(Color.CYAN, "station", table);
        JTextArea textArea = new JTextArea("distance between departure and destination station = "
                + station.getDistance() +  " m.");
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        add(textArea,BorderLayout.NORTH);
        logPanel.add(new JButton(new ShowStationLogAction(table)), BorderLayout.SOUTH);
    }
}
