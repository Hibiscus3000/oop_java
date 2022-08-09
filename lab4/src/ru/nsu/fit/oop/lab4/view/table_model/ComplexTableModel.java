package ru.nsu.fit.oop.lab4.view.table_model;

import ru.nsu.fit.oop.lab4.ObservableLogging;

import javax.swing.table.AbstractTableModel;

public abstract class ComplexTableModel extends AbstractTableModel {

    private final String[] columNames;
    protected final String format = "%d/%d";

    public ComplexTableModel(String... columNames) {
        this.columNames = columNames;
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
