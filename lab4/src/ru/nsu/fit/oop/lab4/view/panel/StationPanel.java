package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.station.Station;
import ru.nsu.fit.oop.lab4.view.panel.action.ShowStationLogAction;
import ru.nsu.fit.oop.lab4.view.table.StationTable;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class StationPanel extends ObservableLoggingPanel{

    private final StationTable stationTable;
    private final JTextArea textArea;
    private final String message;

    public StationPanel(Station station, StationTable table, double boxPanelSizeScale,
                        double frameSizeScale) {
        super(Color.CYAN, "station", table, boxPanelSizeScale, frameSizeScale);
        message = "distance between departure and destination station = "
                + station.getDistance() +  " m.";
        stationTable = table;
        textArea = new JTextArea();
        textArea.setText(message);
        textArea.setFont(new Font("Arial Rounded MT Bold",Font.PLAIN,14));
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        add(textArea,BorderLayout.NORTH);
        JButton button = new JButton(new ShowStationLogAction(table));
        button.setFont(font);
        logPanel.add(button, BorderLayout.SOUTH);
    }
}
