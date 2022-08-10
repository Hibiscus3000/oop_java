package ru.nsu.fit.oop.lab4.view.panel.action;

import ru.nsu.fit.oop.lab4.view.table.ObservableLoggingTable;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ShowRowLogAction extends ShowLogAction{

    public ShowRowLogAction(ObservableLoggingTable observableLoggingTable) {
        super(observableLoggingTable);
        putValue(Action.SHORT_DESCRIPTION,"show logs of the selected row");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (1 == observableLoggingTable.getSelectedRowCount())
            observableLoggingTable.showLogs();
    }
}
