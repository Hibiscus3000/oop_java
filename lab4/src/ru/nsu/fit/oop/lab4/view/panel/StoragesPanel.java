package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.good.Storage;

import javax.swing.*;
import java.util.Map;

public class StoragesPanel extends JPanel {

    public StoragesPanel(Map<String, Storage> storages) {
        setBorder(BorderFactory.createEtchedBorder());
    }

}
