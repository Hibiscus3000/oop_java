package ru.nsu.fit.oop.lab4.view;

import ru.nsu.fit.oop.lab4.Complex;
import ru.nsu.fit.oop.lab4.Util;
import ru.nsu.fit.oop.lab4.view.panel.BorderPanel;
import ru.nsu.fit.oop.lab4.view.panel.ButtonPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.nsu.fit.oop.lab4.Main.logger;

public class ComplexFrame extends JFrame {

    private Complex complex;
    private boolean restart = false;
    private BorderPanel borderPanel;
    private ButtonPanel buttonPanel;
    private StartAction startAction;
    private StopAction stopAction;
    private UrgentStopAction urgentStopAction;
    private final WindowHandler windowHandlerAll;
    private final WindowHandler windowHandlerInfo;
    private final double sizeScale = 0.9;

    public ComplexFrame(Logger mainLogger) {
        windowHandlerAll = new WindowHandler("config logs");
        windowHandlerAll.setLevel(Level.ALL);
        windowHandlerInfo = new WindowHandler("info logs");
        windowHandlerInfo.setLevel(Level.INFO);
        mainLogger.addHandler(windowHandlerAll);
        mainLogger.addHandler(windowHandlerInfo);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setSize((int) (sizeScale * screenSize.width), (int) (sizeScale * screenSize.height));
        setResizable(true);
        setTitle("Transport company");
        setIconImage(new ImageIcon("images/train.png").getImage());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
        addButtonPanel();
        try {
            complex = new Complex();
        } catch (Exception e) {
            e.printStackTrace();
        }
        add(borderPanel = new BorderPanel(complex,buttonPanel.getSizeScale(),sizeScale), BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }

    private void addButtonPanel() {
        buttonPanel = new ButtonPanel(sizeScale);
        addActionButtonAndKey(startAction = new StartAction(),"ctrl Z","buttonPanel.start");
        addActionButtonAndKey(stopAction = new StopAction(),"ctrl X","buttonPanel.stop");
        addActionButtonAndKey(urgentStopAction = new UrgentStopAction(),"ctrl C","buttonPanel.urgent_stop");
        stopAction.setEnabled(false);
        urgentStopAction.setEnabled(false);
        addActionButtonAndKey(new ShowAllLogsAction(),"ctrl A","buttonPanel.showAllLogs");
        addActionButtonAndKey(new ShowInfoLogsAction(),"ctrl L","buttonPanel.showInfoLogs");
        addActionButtonAndKey(new ShowDepotLogsAction(),"ctrl D","buttonPanel.showDepotLogs");
        addActionButtonAndKey(new ExitAction(),"ctrl E","buttonPanel.exit");
        buttonPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.ORANGE));
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private class StartAction extends AbstractAction {

        public StartAction() {
            putValue(Action.NAME, "start");
            putValue(Action.SMALL_ICON, new ImageIcon(new ImageIcon("images/start.png").getImage().
                    getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
            putValue(Action.SHORT_DESCRIPTION, "start the complex (Ctrl+Z)");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                logger.info("Constructing complex...");
                if (restart) {
                    complex = new Complex();
                    remove(borderPanel);
                    add(borderPanel = new BorderPanel(complex,buttonPanel.getSizeScale(),sizeScale),
                            BorderLayout.CENTER);
                    validate();
                    repaint();
                }
                restart = true;
                logger.info("Complex constructed, complex starts working...");
                complex.start();
                logger.info("Complex started working.");
                stopAction.setEnabled(true);
                urgentStopAction.setEnabled(true);
                setEnabled(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private class StopAction extends AbstractAction{

        public StopAction() {
            putValue(Action.NAME, "stop");
            putValue(Action.SMALL_ICON, new ImageIcon(new ImageIcon("images/stop.png").getImage().
                    getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
            putValue(Action.SHORT_DESCRIPTION, "stop the complex  (Ctrl+X)");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                complex.stop();
                logger.info("Complex stopped working.");
                startAction.setEnabled(true);
                setEnabled(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private class UrgentStopAction extends AbstractAction {

        public UrgentStopAction() {
            putValue(Action.NAME, "urgent stop");
            putValue(Action.SMALL_ICON, new ImageIcon(new ImageIcon("images/urgent_stop.png").getImage().
                    getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
            putValue(Action.SHORT_DESCRIPTION, "stop the complex immediately! (Ctrl+C)");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            complex.stopUrgently();
            logger.info("Complex was urgently stopped.");
            startAction.setEnabled(true);
            setEnabled(false);
            stopAction.setEnabled(false);
        }
    }

    private class ShowAllLogsAction extends AbstractAction{

        public ShowAllLogsAction() {
            putValue(Action.NAME, "all logs");
            putValue(Action.SHORT_DESCRIPTION, "show logs with INFO logging level (Ctrl+L)");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            windowHandlerAll.setVisible(true);
        }
    }

    private class ShowInfoLogsAction extends AbstractAction{

        public ShowInfoLogsAction() {
            putValue(Action.NAME, "info logs");
            putValue(Action.SHORT_DESCRIPTION, "show logs with all logging levels (Ctrl+A)");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            windowHandlerInfo.setVisible(true);
        }
    }

    private class ShowDepotLogsAction extends AbstractAction {

        public ShowDepotLogsAction() {
            putValue(Action.NAME,"depot logs");
            putValue(Action.SHORT_DESCRIPTION,"show depot logs (Ctrl+D)");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            complex.setDepotWindowHandlerVisible(true);
        }
    }

    private class ExitAction extends AbstractAction{

        public ExitAction() {
            putValue(Action.NAME,"exit");
            putValue(Action.SHORT_DESCRIPTION,"terminate the program and leave (Ctrl+E)");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            exit();
        }
    }

    private void exit() {
        if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(ComplexFrame.this,
                "Are you sure that you want to exit????", "confirm exit",
                JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE)) {
            complex.stopUrgently();
            System.exit(0);
        }
    }

    private void addActionButtonAndKey(Action action, String key, String keyObject) {
        buttonPanel.add(new JButton(action));
        InputMap inputMap = buttonPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(key),keyObject);
        buttonPanel.getActionMap().put(keyObject,action);
    }


}
