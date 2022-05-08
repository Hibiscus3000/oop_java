package ru.nsu.fit.oop.game.view;

import ru.nsu.fit.oop.game.model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class View  extends JFrame implements Observer {

    private final int fieldSizeX;
    private final int fieldSizeY;
    private final int windowSizeX;
    private final int windowSizeY;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Model model;
    private StartupMenu startupMenu;
    private GameField gameField;

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
        windowSizeX = (int)(2 * toolkit.getScreenSize().getWidth()) / 3;
        windowSizeY = (int)(2 * toolkit.getScreenSize().getHeight()) / 3;
        fieldSizeX = windowSizeX;
        fieldSizeY = windowSizeY;
        Dimension size = new Dimension(windowSizeX,windowSizeY );
        setPreferredSize(size);
        startupMenu = new StartupMenu();
        add(startupMenu);
        setResizable(false);
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
        model = new Model(this,fieldSizeX,fieldSizeY);
        gameField = new GameField(windowSizeX, windowSizeY, model);
        add(gameField);
        model.initGame(name);
        getContentPane().setBackground(new Color(180,180,180));
        add(new HeroMover(model.getGameObjectsInfo()));
    }

    @Override
    public void update(Observable o, Object arg) {
        gameField.setGameObjectsInfo(model.getGameObjectsInfo());
        gameField.repaint();
    }
}
