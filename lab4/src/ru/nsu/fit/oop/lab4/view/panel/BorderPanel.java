package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.Complex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class BorderPanel extends JPanel {

    private final BoxPanel boxPanel;

    public BorderPanel(Complex complex) {
        setLayout(new BorderLayout());
        add(boxPanel = new BoxPanel(complex),BorderLayout.CENTER);
        add(new CheckBoxPanel(),BorderLayout.NORTH);
    }

    private class CheckBoxPanel extends JPanel {

        private final Map<String,JCheckBox> checkBoxMap = new HashMap<>();

        public CheckBoxPanel() {
            ActionListener listener = event -> {
                boxPanel.setPanelVisible(event.getActionCommand(),checkBoxMap.
                        get(event.getActionCommand()).isSelected());
            };
            addCheckBox("goods",listener);
            addCheckBox("station",listener);
            addCheckBox("trains",listener);
            addCheckBox("factories",listener);
            addCheckBox("departure storages",listener);
            addCheckBox("destination storages",listener);
            addCheckBox("consumers",listener);
            setBorder(BorderFactory.createEtchedBorder());
        }

        private void addCheckBox(String name, ActionListener listener) {
            JCheckBox checkBox = new JCheckBox(name);
            checkBoxMap.put(name,checkBox);
            checkBox.addActionListener(listener);
            checkBox.setSelected(true);
            add(checkBox);
        }
    }
}
