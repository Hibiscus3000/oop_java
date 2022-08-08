package ru.nsu.fit.oop.lab4.view.panel;

import javax.swing.*;
import java.awt.*;

public abstract class ComplexPanel extends JPanel {

    public ComplexPanel(Color color, String name) {
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),name));
        setBackground(color);
    }
}
