package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.view.table.ComplexTable;

import javax.swing.*;
import java.awt.*;

public class ComplexPanel extends JPanel {

    protected ComplexTable complexTable;
    protected final double sizeScale;
    protected final int numberOfRows = 3;

    public ComplexPanel(Color color, String name, ComplexTable table, double boxPanelSizeScale) {
        sizeScale = boxPanelSizeScale / 3;
        complexTable = table;
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), name));
        setBackground(color);
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    @Override
    public Dimension getPreferredSize() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        return new Dimension(complexTable.getWidth(),
                (int) (toolkit.getScreenSize().height * sizeScale));
    }
}
