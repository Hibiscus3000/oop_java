package ru.nsu.fit.oop.lab4.view.panel.action;

import ru.nsu.fit.oop.lab4.view.table.ObservableLoggingTable;

import java.awt.event.ActionEvent;

public class ShowStationLogAction extends ShowLogAction{

    public ShowStationLogAction(ObservableLoggingTable observableLoggingTable) {
        super(observableLoggingTable);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        observableLoggingTable.showLogs();
    }
}
