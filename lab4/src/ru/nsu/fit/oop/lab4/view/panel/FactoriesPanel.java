package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.good.Factory;

import java.awt.*;
import java.util.List;

public class FactoriesPanel extends ComplexPanel {

    public FactoriesPanel(List<Factory> factories) {
        super(new Color(150,150,0),"factories");
    }

}
