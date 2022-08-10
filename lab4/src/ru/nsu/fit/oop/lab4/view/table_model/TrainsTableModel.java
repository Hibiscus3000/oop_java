package ru.nsu.fit.oop.lab4.view.table_model;

import ru.nsu.fit.oop.lab4.exception.UnknownGoodName;
import ru.nsu.fit.oop.lab4.train.Train;

import java.util.List;

import static ru.nsu.fit.oop.lab4.Util.append;
import static ru.nsu.fit.oop.lab4.Util.prepend;

public class TrainsTableModel extends ObservableLoggingTableModel<Train> {

    public TrainsTableModel(List<Train> trains,String... goodNames) {
        super(trains,append(append(prepend(goodNames,"id"),"state"),"marked for recycling"));
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (null == list)
            return null;
        Train train = list.get(rowIndex);
        if (0 == columnIndex) {
            return train.getId();
        }
        if (getNumberOfColumns() - 1 == columnIndex) {
            return train.isMarked();
        }
        if (getNumberOfColumns() - 2 == columnIndex) {
            return train.getState();
        }
        try {
            return String.format(format, train.getGoodQuantity(getColumnName(columnIndex)),
                    train.getGoodCapacity(getColumnName(columnIndex)));
        } catch (UnknownGoodName e) {
            e.printStackTrace();
        }
        return null;
    }
}
