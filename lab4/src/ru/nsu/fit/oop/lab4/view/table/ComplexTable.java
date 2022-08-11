package ru.nsu.fit.oop.lab4.view.table;

import ru.nsu.fit.oop.lab4.view.table.table_model.ComplexTableModel;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class ComplexTable extends JTable implements Observer {

    protected ComplexTableModel tableModel;

    public ComplexTable(ComplexTableModel tableModel) {
        super(tableModel);
        this.tableModel = tableModel;
    }

    @Override
    public void update(Observable o, Object arg) {
        updateUI();
    }

}
