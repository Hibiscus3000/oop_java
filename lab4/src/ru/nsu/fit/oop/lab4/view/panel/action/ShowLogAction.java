package ru.nsu.fit.oop.lab4.view.panel.action;

import ru.nsu.fit.oop.lab4.view.table.ObservableLoggingTable;

import javax.swing.*;

public abstract class ShowLogAction extends AbstractAction {

    protected ObservableLoggingTable observableLoggingTable;

    public ShowLogAction(ObservableLoggingTable observableLoggingTable) {
        this.observableLoggingTable = observableLoggingTable;
        putValue(Action.NAME,"show logs");
    }
}
