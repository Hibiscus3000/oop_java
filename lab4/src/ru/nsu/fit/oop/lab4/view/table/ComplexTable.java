package ru.nsu.fit.oop.lab4.view.table;

import ru.nsu.fit.oop.lab4.view.table.table_model.ComplexTableModel;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
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
    private final int[] preferredColumnsWidth;

    public ComplexTable(ComplexTableModel tableModel) {
        super(tableModel);
        this.tableModel = tableModel;
        preferredColumnsWidth = new int[getColumnCount()];
        Arrays.fill(preferredColumnsWidth,minWidth);
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        resizeColumnsSize();
    }

    private void resizeColumnsSize() {
        final TableColumnModel tableColumnModel = getColumnModel();
        final JTableHeader tableHeader = getTableHeader();
        final FontMetrics headerFontMetrics = tableHeader.getFontMetrics(tableHeader.getFont());
        for (int column = 0; column < tableColumnModel.getColumnCount(); ++column) {
            int headWidth = headerFontMetrics.stringWidth(getColumnName(column));
            int width = minWidth;
            for (int row = 0; row < getRowCount(); ++row) {
                TableCellRenderer renderer = getCellRenderer(row,column);
                Component component = prepareRenderer(renderer,row,column);
                width = max(component.getPreferredSize().width + widthIncrease,width);
            }
            if (width > maxWidth)
                width = maxWidth;
            columnModel.getColumn(column).setPreferredWidth(preferredColumnsWidth[column] =
                    max(preferredColumnsWidth[column],max(width,headWidth + widthIncrease)));
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        resizeColumnsSize();
        resizeAndRepaint();
    }

}
