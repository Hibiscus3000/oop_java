package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.Complex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class BorderPanel extends JPanel {

    private final BoxPanel boxPanel;
    private final double buttonPanelSizeScale;
    private final double checkboxPanelSizeScale = 0.05;
    private final double frameSizeScale;

    public BorderPanel(Complex complex, double buttonPanelSizeScale, double frameSizeScale) {
        this.frameSizeScale = frameSizeScale;
        this.buttonPanelSizeScale = buttonPanelSizeScale;
        setLayout(new BorderLayout());
        CheckBoxPanel checkBoxPanel = new CheckBoxPanel();
        add(checkBoxPanel, BorderLayout.NORTH);
        add(boxPanel = new BoxPanel(complex, checkBoxPanel,
                        frameSizeScale * (1 - buttonPanelSizeScale - checkboxPanelSizeScale)),
                BorderLayout.CENTER);
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

        @Override
        public Dimension getPreferredSize() {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension screenSize = toolkit.getScreenSize();
            return new Dimension((int) (checkboxPanelSizeScale * screenSize.width),
                    (int) (checkboxPanelSizeScale * screenSize.height));
        }
    }

    @Override
    public Dimension getPreferredSize() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        return new Dimension((int) (screenSize.width * frameSizeScale * (1 - checkboxPanelSizeScale - buttonPanelSizeScale)),
                (int) (screenSize.height * frameSizeScale * (1 - checkboxPanelSizeScale - buttonPanelSizeScale)));
    }
}
