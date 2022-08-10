package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.view.table.ObservableLoggingTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ObservableLoggingPanel extends ComplexPanel {

    public ObservableLoggingPanel(Color color, String name, ObservableLoggingTable table) {
        super(color, name, table);
        JPanel logPanel = new JPanel();
        logPanel.add(new JButton(new ObservableLoggingPanel.ShowLogAction()), BorderLayout.SOUTH);
        add(logPanel,BorderLayout.SOUTH);
    }

    private class ShowLogAction extends AbstractAction {

        public ShowLogAction() {
            putValue(Action.NAME,"show logs");
            putValue(Action.SHORT_DESCRIPTION,"show logs of the selected row");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (1 == complexTable.getSelectedRowCount())
                ((ObservableLoggingTable)complexTable).showLogs();
        }
    }

}
