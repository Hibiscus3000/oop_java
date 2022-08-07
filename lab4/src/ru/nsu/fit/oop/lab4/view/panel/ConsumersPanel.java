package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.Consumer;

import javax.swing.*;
import java.util.List;

public class ConsumersPanel extends JPanel {

    public ConsumersPanel(List<Consumer> consumers) {
        setBorder(BorderFactory.createEtchedBorder());
    }

}
