package ru.nsu.fit.oop.lab4.view.table;

import ru.nsu.fit.oop.lab4.view.table.table_model.ObservableLoggingTableModel;

public class ObservableLoggingTable extends ComplexTable{

    public ObservableLoggingTable(ObservableLoggingTableModel tableModel, double frameSizeScale) {
        super(tableModel, frameSizeScale);
    }

    public void showLogs() {
        ((ObservableLoggingTableModel)tableModel).showLogs(getSelectedRow());
    }
}
