package ru.nsu.fit.oop.lab4.view.table_model;

import javax.swing.table.AbstractTableModel;

public abstract class ComplexTableModel extends AbstractTableModel {

    private final int rowCount;
    private final String[] columNames;
    protected final String format = "%d/%d";

    public ComplexTableModel(int rowCount, String... columNames) {
        this.columNames = columNames;
        this.rowCount = rowCount;
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return columNames.length;
    }

    @Override
    public String getColumnName(int c) {
        return columNames[c];
    }

}
