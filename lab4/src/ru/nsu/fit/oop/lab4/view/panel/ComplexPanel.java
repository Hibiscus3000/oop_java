package ru.nsu.fit.oop.lab4.view.panel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class ComplexPanel extends JPanel {

    public ComplexPanel(Color color, String name, AbstractTableModel tableModel) {
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),name));
        setBackground(color);
        setLayout(new BorderLayout());
        add(new JTable(tableModel),BorderLayout.CENTER);
    }
}
