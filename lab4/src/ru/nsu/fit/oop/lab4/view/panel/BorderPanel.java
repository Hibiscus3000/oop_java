package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.Complex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class BorderPanel extends JPanel {

    private final GridBagPanel gridBagPanel;

    public BorderPanel(Complex complex) {
        setLayout(new BorderLayout());
        add(gridBagPanel = new GridBagPanel(complex),BorderLayout.CENTER);
        add(new CheckBoxPanel(),BorderLayout.NORTH);
    }

    private class CheckBoxPanel extends JPanel {

        private final Map<String,JCheckBox> checkBoxMap = new HashMap<>();

        public CheckBoxPanel() {
            JCheckBox stationBox = new JCheckBox("station");
            checkBoxMap.put(stationBox.getActionCommand(),stationBox);
            JCheckBox trainsBox = new JCheckBox("trains");
            checkBoxMap.put(trainsBox.getActionCommand(),trainsBox);
            JCheckBox consumersBox = new JCheckBox("consumers");
            checkBoxMap.put(consumersBox.getActionCommand(),consumersBox);
            JCheckBox factoriesBox = new JCheckBox("factories");
            checkBoxMap.put(factoriesBox.getActionCommand(),factoriesBox);
            JCheckBox departureStoragesBox = new JCheckBox("departure storages");
            checkBoxMap.put(departureStoragesBox.getActionCommand(),departureStoragesBox);
            JCheckBox destinationStoragesBox = new JCheckBox("destination storages");
            checkBoxMap.put(destinationStoragesBox.getActionCommand(),destinationStoragesBox);
            ActionListener actionListener = event -> {
              gridBagPanel.setPanelVisible(event.getActionCommand(),checkBoxMap.
                      get(event.getActionCommand()).isSelected());
            };
            stationBox.addActionListener(actionListener);
            trainsBox.addActionListener(actionListener);
            consumersBox.addActionListener(actionListener);
            factoriesBox.addActionListener(actionListener);
            departureStoragesBox.addActionListener(actionListener);
            destinationStoragesBox.addActionListener(actionListener);
            add(stationBox);
            add(trainsBox);
            add(consumersBox);
            add(factoriesBox);
            add(departureStoragesBox);
            add(destinationStoragesBox);
            stationBox.setSelected(true);
            trainsBox.setSelected(true);
            consumersBox.setSelected(true);
            factoriesBox.setSelected(true);
            departureStoragesBox.setSelected(true);
            destinationStoragesBox.setSelected(true);
            setBorder(BorderFactory.createEtchedBorder());
        }
    }
}
