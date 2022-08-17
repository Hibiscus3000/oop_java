package ru.nsu.fit.oop.lab4.view.table;

import ru.nsu.fit.oop.lab4.view.panel.ComplexPanel;
import ru.nsu.fit.oop.lab4.view.table.table_model.ComplexTableModel;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import static java.lang.Math.max;

public class ComplexTable extends JTable implements Observer {

    protected ComplexTableModel tableModel;
    private final int minWidth = 15;
    private final int maxWidth = 250;
    private final int widthIncrease = 30;
    private final int maxEmptyWidth = 30;
    private final int[] preferredColumnsWidth;

    public ComplexTable(ComplexTableModel tableModel) {
        super(tableModel);
        this.tableModel = tableModel;
        preferredColumnsWidth = new int[getColumnCount()];
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Arrays.fill(preferredColumnsWidth, minWidth);
        resizeColumnsSize();
    }

    public void resizeColumnsSize() {
        final ComplexPanel parent = (ComplexPanel) SwingUtilities.getAncestorOfClass(
                ComplexPanel.class, this);
        if (null == parent)
            return;
        final int parentWidth = parent.getWidth();
        int emptyWidth = parentWidth;
        final TableColumnModel tableColumnModel = getColumnModel();
        final JTableHeader tableHeader = getTableHeader();
        final FontMetrics headerFontMetrics = tableHeader.getFontMetrics(tableHeader.getFont());
        int columnCount = tableColumnModel.getColumnCount();
        for (int column = 0; column < columnCount; ++column) {
            int headWidth = headerFontMetrics.stringWidth(getColumnName(column));
            int width = minWidth;
            for (int row = 0; row < getRowCount(); ++row) {
                TableCellRenderer renderer = getCellRenderer(row, column);
                Component component = prepareRenderer(renderer, row, column);
                width = max(component.getPreferredSize().width + widthIncrease, width);
            }
            if (width > maxWidth)
                width = maxWidth;
            preferredColumnsWidth[column] = max(preferredColumnsWidth[column],
                    max(width, headWidth + widthIncrease));
            emptyWidth -= preferredColumnsWidth[column];
        }
        for (int column = 0; column < columnCount; ++column) {
            TableColumn tableColumn = columnModel.getColumn(column);
            if (emptyWidth <= maxEmptyWidth)
                tableColumn.setPreferredWidth(preferredColumnsWidth[column]);
            else
                tableColumn.setPreferredWidth(preferredColumnsWidth[column] +
                        (emptyWidth - maxEmptyWidth) / columnCount);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        resizeColumnsSize();
        resizeAndRepaint();
    }

}
