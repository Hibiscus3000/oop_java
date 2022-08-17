package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.view.table.ObservableLoggingTable;

import javax.swing.*;
import java.awt.*;

public abstract class ObservableLoggingPanel extends ComplexPanel {

    protected JPanel logPanel;
    protected Font font = new Font("Microsoft Sans Serif",Font.BOLD,14);

    public ObservableLoggingPanel(Color color, String name, ObservableLoggingTable table,
                                  double boxPanelSizeScale, double frameSizeScale) {
        super(color, name, table, boxPanelSizeScale, frameSizeScale);
        logPanel = new JPanel();
        logPanel.setBackground(color);
        add(logPanel, BorderLayout.SOUTH);
    }

}
