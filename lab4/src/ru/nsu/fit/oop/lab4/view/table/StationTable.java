package ru.nsu.fit.oop.lab4.view.table;

import ru.nsu.fit.oop.lab4.view.table.table_model.StationTableModel;

public class StationTable extends ObservableLoggingTable{

    public StationTable(StationTableModel tableModel) {
        super(tableModel);
    }

    @Override
    public void showLogs() {
        ((StationTableModel)tableModel).showLogs();
    }
}
