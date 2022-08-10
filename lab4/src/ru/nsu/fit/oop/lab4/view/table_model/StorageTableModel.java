package ru.nsu.fit.oop.lab4.view.table_model;

import ru.nsu.fit.oop.lab4.good.Storage;

import java.util.List;

public class StorageTableModel extends ObservableLoggingTableModel<Storage> {

    public StorageTableModel(List<Storage> storages) {
        super(storages, "good", "occupancy", "state");
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (null == list)
            return null;
        Storage storage = list.get(rowIndex);
        if (0 == columnIndex)
            return storage.getGoodName();
        if (1 == columnIndex)
            return String.format(format,storage.getGoodsQuantity(),storage.getCapacity());
        if (storage.getCapacity() == storage.getGoodsQuantity())
            return "full";
        if (0 == storage.getGoodsQuantity())
            return "empty";
        return "";
    }
}
