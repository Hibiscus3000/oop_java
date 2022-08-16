package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.station.Station;
import ru.nsu.fit.oop.lab4.view.panel.action.ShowStationLogAction;
import ru.nsu.fit.oop.lab4.view.table.StationTable;

import javax.swing.*;
import java.awt.*;

public class StationPanel extends ObservableLoggingPanel{

    private final StationTable stationTable;
    private final JTextArea textArea;

    public StationPanel(Station station, StationTable table, double boxPanelSizeScale) {
        super(Color.CYAN, "station", table, boxPanelSizeScale);
        stationTable = table;
        textArea = new JTextArea("distance between departure and destination station = "
                + station.getDistance() +  " m.");
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        add(textArea,BorderLayout.NORTH);
        logPanel.add(new JButton(new ShowStationLogAction(table)), BorderLayout.SOUTH);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        size.width = Math.max(textArea.getWidth(),stationTable.getWidth());
        return size;
    }
}
