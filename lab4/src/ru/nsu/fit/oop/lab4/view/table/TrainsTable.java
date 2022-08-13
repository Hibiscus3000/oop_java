package ru.nsu.fit.oop.lab4.view.table;

import ru.nsu.fit.oop.lab4.train.Train;
import ru.nsu.fit.oop.lab4.view.table.table_model.TrainsTableModel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class TrainsTable extends ObservableLoggingTable {

    private TableRowSorter<TrainsTableModel> sorter;
    private List<RowSorter.SortKey> sortKeys;
    private boolean firstUpdate = true;

    public TrainsTable(List<Train> trains, String... goodNames) {
        super(new TrainsTableModel(trains, goodNames));
        sorter = new TableRowSorter<TrainsTableModel>((TrainsTableModel) this.getModel());
        this.setRowSorter(sorter);
        sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
    }

    @Override
    public void update(Observable o, Object arg) {
        super.update(o, arg);
        if (firstUpdate) {
            firstUpdate = false;
            sorter.setSortKeys(sortKeys);
        }
        sorter.sort();
    }
}
