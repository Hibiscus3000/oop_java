package ru.nsu.fit.oop.lab4.view.table_model;

import ru.nsu.fit.oop.lab4.good.Good;

import java.util.List;

public class GoodsTableModel extends ComplexTableModel<Good>{


    public GoodsTableModel(List<Good> goods) {
        super(goods, "name","loading time","unloading time","consumption time");
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (null == list)
            return null;
        Good good = list.get(rowIndex);
        if (0 == columnIndex)
            return good.getName();
        if (1 == columnIndex)
            return (double) good.getLoadingTimeMillis() / 1000;
        if (2 == columnIndex)
            return (double) good.getUnloadingTimeMillis() / 1000;
        return (double) good.getConsumptionTimeMillis() / 1000;
    }
}
