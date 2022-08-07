package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.train.Train;

import javax.swing.*;
import java.util.List;

public class TrainsPanel extends JPanel {

    public TrainsPanel(List<Train> trains) {
        setBorder(BorderFactory.createEtchedBorder());
    }

}
