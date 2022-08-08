package ru.nsu.fit.oop.lab4.view.table_model;

import ru.nsu.fit.oop.lab4.exception.UnknownGoodName;
import ru.nsu.fit.oop.lab4.train.Train;

import java.util.List;

import static ru.nsu.fit.oop.lab4.view.Util.append;
import static ru.nsu.fit.oop.lab4.view.Util.prepend;

public class TrainsTableModel extends ComplexTableModel{

    private final List<Train> trains;

    public TrainsTableModel(List<Train> trains,String... goodNames) {
        super(trains.size(), append(prepend(goodNames,"id"),"state"));
        this.trains = trains;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Train train = trains.get(rowIndex);
        try {
            return String.format(format, train.getGoodQuantity(getColumnName(columnIndex)),
                    train.getGoodQuantity(getColumnName(columnIndex)));
        } catch (UnknownGoodName e) {
            e.printStackTrace();
        }
        return null;
    }
}
