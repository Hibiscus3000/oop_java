package ru.nsu.fit.oop.game.view;

import ru.nsu.fit.oop.game.model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class View  extends JFrame {

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Model model;
    StartupMenu startupMenu;

    public class StartupMenu extends JPanel {

        public StartupMenu() {
            setLayout(new GridLayout(4, 1, 20, 20));
            setOpaque(true);
            addButton("PLAY", new PLayAction());
            addButton("RECORDS", null);
            addButton("CREDITS", null);
            addButton("EXIT", new ExitAction());
        }

        private void addButton(String label, ActionListener listener) {
            JButton button = new JButton(label);
            button.addActionListener(listener);
            button.setPreferredSize(new Dimension(toolkit.getScreenSize().width / 7, toolkit.getScreenSize().height / 16));
            add(button);
        }

        public class ExitAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }

        public class PLayAction implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                    setPlayerName();
            }
        }
    }

    public View() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("The Witcher 3: Wild Hunt");
        setLayout(new FlowLayout());
        Dimension size = new Dimension((2 * toolkit.getScreenSize().width) / 3,
                (2 * toolkit.getScreenSize().height) / 3);
        setPreferredSize(size);
        StartupMenu startupMenu = new StartupMenu();
        add(startupMenu);
        setResizable(true);
        setVisible(true);
        getContentPane().setBackground(new Color(30,150,10));
        pack();
        setLocationRelativeTo(null);
        toFront();
    }

    private void setPlayerName()  {
        remove(startupMenu);
        TextField nameField = new TextField(25);
        add(nameField);
        nameField.setText("Enter your name...");
        nameField.getText();
        //nameField.addActionListener();
    }
}
