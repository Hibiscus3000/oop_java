package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.view.panel.action.ShowRowLogAction;
import ru.nsu.fit.oop.lab4.view.table.ObservableLoggingTable;

import javax.swing.*;
import java.awt.*;

public class RowLoggingPanel extends ObservableLoggingPanel{
    public RowLoggingPanel(Color color, String name, ObservableLoggingTable table) {
        super(color, name, table);
        logPanel.add(new JButton(new ShowRowLogAction(table)), BorderLayout.SOUTH);
    }
}
