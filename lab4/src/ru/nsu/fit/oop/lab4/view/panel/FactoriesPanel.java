package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.good.Factory;

import javax.swing.*;
import java.util.List;

public class FactoriesPanel extends JPanel {

    public FactoriesPanel(List<Factory> factories) {
        setBorder(BorderFactory.createEtchedBorder());
    }

}
