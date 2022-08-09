package ru.nsu.fit.oop.lab4.view.panel;

import ru.nsu.fit.oop.lab4.Complex;
import ru.nsu.fit.oop.lab4.view.ComplexTable;
import ru.nsu.fit.oop.lab4.view.table_model.ComplexTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ComplexPanel extends JPanel {

    private ComplexTable complexTable;
    private JButton logButton;

    public ComplexPanel(Complex complex, Color color, String name, ComplexTableModel tableModel) {
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), name));
        setBackground(color);
        setLayout(new BorderLayout());
        add(new JScrollPane((JComponent) complex.setDepotObserver(complexTable = new ComplexTable(tableModel))), BorderLayout.CENTER);
        add(logButton = new JButton(new ShowLogAction()), BorderLayout.SOUTH);
    }

    private class ShowLogAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (1 == complexTable.getSelectedRowCount())
                complexTable.showLogs();
        }
    }

}
