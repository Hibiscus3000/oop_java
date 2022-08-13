package ru.nsu.fit.oop.lab4.view.table.table_model;

import ru.nsu.fit.oop.lab4.ObservableLogging;

import java.util.List;


public abstract class ObservableLoggingTableModel<T extends ObservableLogging> extends ComplexTableModel<T> {

    protected final String format = "%d/%d";

    public ObservableLoggingTableModel(List<T> observableLoggingList, String... columNames) {
        super(observableLoggingList, columNames);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0,columnIndex).getClass();
    }

    public void showLogs(int row) {
        list.get(row).setWindowHandlerVisible(true);
    }

}
