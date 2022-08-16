package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.view.table.ObservableLoggingTable;

import javax.swing.*;
import java.awt.*;

public abstract class ObservableLoggingPanel extends ComplexPanel {

    protected JPanel logPanel;

    public ObservableLoggingPanel(Color color, String name, ObservableLoggingTable table,
                                  double boxPanelSizeScale) {
        super(color, name, table, boxPanelSizeScale);
        logPanel = new JPanel();
        add(logPanel, BorderLayout.SOUTH);
    }

}
