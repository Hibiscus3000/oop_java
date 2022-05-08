package ru.nsu.fit.oop.game.view;

import ru.nsu.fit.oop.game.model.Model;
import ru.nsu.fit.oop.game.model.Radix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class View  extends JFrame implements Observer {

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Model model;
    StartupMenu startupMenu;
    GameField gameField;

    private class StartupMenu extends JPanel {

        public StartupMenu() {
            setLayout(new GridLayout(4, 1, 20, 20));
            setOpaque(false);
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
        Dimension size = new Dimension((int)(2 * toolkit.getScreenSize().getWidth()) / 3,
                (int)(2 * toolkit.getScreenSize().getHeight()) / 3);
        setPreferredSize(size);
        startupMenu = new StartupMenu();
        add(startupMenu);
        setResizable(true);
        setVisible(true);
        getContentPane().setBackground(new Color(30,150,10));
        pack();
        setLocationRelativeTo(null);
        toFront();
    }

    private void setPlayerName()  {
        startupMenu.setVisible(false);
        JPanel namePanel = new JPanel();
        add(namePanel);
        namePanel.setOpaque(false);
        namePanel.setVisible(true);
        TextField nameField = new TextField(25);
        namePanel.add(nameField);
        nameField.setText("Enter your name...");
        nameField.setPreferredSize(new Dimension(30,30));
        JButton startButton = new JButton("start");
        startButton.setPreferredSize(new Dimension(100,30));
        namePanel.add(startButton);
        startButton.addActionListener(e -> {
            namePanel.setVisible(false);
            initGame(nameField.getText());
        });
    }

    private void initGame(String name) {
        gameField = new GameField((int)toolkit.getScreenSize().getWidth(),
                (int)toolkit.getScreenSize().getHeight(),model.getGameObjectsInfo());
        gameField.setVisible(true);
        add(gameField);
        getContentPane().setBackground(Color.WHITE);
        model = new Model(this,2500,1400);
        model.initGame(name);
    }

    @Override
    public void update(Observable o, Object arg) {
        Radix radix = (Radix)arg;
        gameField.repaint();
    }
}
