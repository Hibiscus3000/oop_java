package ru.nsu.fit.oop.game.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndPanel extends JPanel {
    private String message;

    public EndPanel(String message) {
        this.message = message;
        setPreferredSize(new Dimension(400,400));
        setLayout(new BorderLayout());
        JLabel messageLabel = new JLabel(message,SwingConstants.CENTER);
        add(messageLabel,BorderLayout.CENTER);
        addButton("EXIT", new ExitAction());
    }

    private void addButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        button.setPreferredSize(new Dimension(50, 50));
        add(button,BorderLayout.SOUTH);
    }

    public class ExitAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
