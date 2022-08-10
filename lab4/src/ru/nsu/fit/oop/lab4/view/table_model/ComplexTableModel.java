package ru.nsu.fit.oop.lab4.view.table_model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public abstract class ComplexTableModel<T> extends AbstractTableModel {

    protected final List<T> list;
    private final String[] columNames;

    public ComplexTableModel(List<T> list, String... columNames) {
        this.list = list;
        this.columNames = columNames;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return columNames.length;
    }

    @Override
    public String getColumnName(int c) {
        return columNames[c];
    }

    protected int getNumberOfColumns() {
        return columNames.length;
    }
}
