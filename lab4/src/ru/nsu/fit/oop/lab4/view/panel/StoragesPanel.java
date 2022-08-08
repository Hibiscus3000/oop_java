package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.good.Storage;

import java.awt.*;
import java.util.Map;

public class StoragesPanel extends ComplexPanel {

    public StoragesPanel(Map<String, Storage> storages, String place) {
        super(new Color(0,150,0),place +" storages");
    }

}
