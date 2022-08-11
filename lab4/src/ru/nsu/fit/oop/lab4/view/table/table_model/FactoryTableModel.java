package ru.nsu.fit.oop.lab4.view.table.table_model;

import ru.nsu.fit.oop.lab4.good.Factory;

import java.util.List;

public class FactoryTableModel extends ObservableLoggingTableModel<Factory> {


    public FactoryTableModel(List<Factory> factories) {
        super(factories, "good", "number of goods produced");
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (null == list)
            return null;
        Factory factory = list.get(rowIndex);
        if (0 == columnIndex)
            return factory.getGoodName();
        return factory.getNumberOfGoodsProduced();
    }
}
