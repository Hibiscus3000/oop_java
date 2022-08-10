package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.view.table.ComplexTable;

import javax.swing.*;
import java.awt.*;

public class ComplexPanel extends JPanel {

    protected ComplexTable complexTable;

    public ComplexPanel(Color color, String name, ComplexTable table) {
        complexTable = table;
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), name));
        setBackground(color);
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}
