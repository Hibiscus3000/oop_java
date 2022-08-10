package ru.nsu.fit.oop.lab4.view.table_model;

import ru.nsu.fit.oop.lab4.ObservableLogging;

import java.util.List;


public abstract class ObservableLoggingTableModel<T extends ObservableLogging> extends ComplexTableModel<T> {

    protected final String format = "%d/%d";

    public ObservableLoggingTableModel(List<T> observableLoggingList, String... columNames) {
        super(observableLoggingList, columNames);
    }

    public void showLogs(int row) {
        list.get(row).setWindowHandlerVisible(true);
    }

}
