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
        CheckBoxPanel checkBoxPanel = new CheckBoxPanel();
        add(checkBoxPanel, BorderLayout.NORTH);
        add(boxPanel = new BoxPanel(complex, checkBoxPanel), BorderLayout.CENTER);
    }

    class CheckBoxPanel extends JPanel {

        private final Map<String, JCheckBox> checkBoxMap = new HashMap<>();

        public CheckBoxPanel() {
            ActionListener listener = event -> {
                boxPanel.setPanelVisible(event.getActionCommand(), checkBoxMap.
                        get(event.getActionCommand()).isSelected());
            };
            addCheckBox("goods", listener, false);
            addCheckBox("station", listener, true);
            addCheckBox("trains", listener, true);
            addCheckBox("factories", listener, true);
            addCheckBox("departure storages", listener, true);
            addCheckBox("destination storages", listener, true);
            addCheckBox("consumers", listener, true);
            setBorder(BorderFactory.createEtchedBorder());
        }

        private void addCheckBox(String name, ActionListener listener, boolean selected) {
            JCheckBox checkBox = new JCheckBox(name);
            checkBoxMap.put(name, checkBox);
            checkBox.addActionListener(listener);
            checkBox.setSelected(selected);
            add(checkBox);
        }

        public boolean isSelected(String name) {
            return checkBoxMap.get(name).isSelected();
        }
    }
}
