package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.view.panel.action.ShowRowLogAction;
import ru.nsu.fit.oop.lab4.view.table.ObservableLoggingTable;

import javax.swing.*;
import java.awt.*;

public class RowLoggingPanel extends ObservableLoggingPanel{
    public RowLoggingPanel(Color color, String name, ObservableLoggingTable table,
                           double boxPanelSizeScale, double frameSizeScale) {
        super(color, name, table, boxPanelSizeScale, frameSizeScale);
        JButton button = new JButton(new ShowRowLogAction(table));
        button.setFont(font);
        logPanel.add(button, BorderLayout.SOUTH);
    }
}
