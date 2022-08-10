package ru.nsu.fit.oop.lab4.view.table_model;

import ru.nsu.fit.oop.lab4.Consumer;

import java.util.List;

public class ConsumersTableModel extends ObservableLoggingTableModel<Consumer> {

    public ConsumersTableModel(List<Consumer> consumers) {
        super(consumers, "id", "good", "number of goods consumed");
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (null == list)
            return null;
        Consumer consumer = list.get(rowIndex);
        if (0 == columnIndex)
            return consumer.getId();
        if (1 == columnIndex)
            return consumer.getGoodName();
        return consumer.getNumberOfGoodsConsumed();
    }
}
