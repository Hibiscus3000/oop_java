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

    public StationPanel(Station station, StationTable table, double boxPanelSizeScale) {
        super(Color.CYAN, "station", table, boxPanelSizeScale);
        message = "distance between departure and destination station = "
                + station.getDistance() +  " m.";
        stationTable = table;
        textArea = new JTextArea();
        textArea.setText(message);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        add(textArea,BorderLayout.NORTH);
        logPanel.add(new JButton(new ShowStationLogAction(table)), BorderLayout.SOUTH);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        FontRenderContext context = getFontMetrics(getFont()).getFontRenderContext();
        Rectangle2D bounds = getFont().getStringBounds(message,context);
        size.width = (int) Math.max(bounds.getWidth(),stationTable.getWidth());
        return size;
    }
}
