package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.view.table.ComplexTable;

import javax.swing.*;
import java.awt.*;

public class ComplexPanel extends JPanel {

    protected ComplexTable complexTable;
    protected final double sizeScale;
    protected final int numberOfRows = 3;
    protected final double frameSizeScale;

    public ComplexPanel(Color color, String name, ComplexTable table, double boxPanelSizeScale,
                        double frameSizeScale) {
        this.frameSizeScale = frameSizeScale;
        sizeScale = boxPanelSizeScale / numberOfRows;
        complexTable = table;
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), name));
        setBackground(color);
        setLayout(new BorderLayout());
        add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
        table.resizeColumnsSize();
    }

    public void resizeComplexTable() {
        complexTable.resizeColumnsSize();
    }

    public void resizeComplexTable() {
        complexTable.resizeAndRepaint();
    }

    public int getNumberOfVisibleComponentsInRow() {
        final JPanel parent = (JPanel) SwingUtilities.getAncestorOfClass(
                JPanel.class, this);
        if (null == parent)
            return -1;
        int numberOfVisibleComponentsInRow = 0;
        for (Component c : parent.getComponents()) {
            if (c.isVisible())
                ++numberOfVisibleComponentsInRow;
        }
        return numberOfVisibleComponentsInRow;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return new Dimension((int)(frameSizeScale * screenSize.width / getNumberOfVisibleComponentsInRow()),
                (int) (screenSize.height * sizeScale));
    }
}
