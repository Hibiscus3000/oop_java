package ru.nsu.fit.oop.lab4.view;

import ru.nsu.fit.oop.lab4.Complex;
import ru.nsu.fit.oop.lab4.view.panel.BorderPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.nsu.fit.oop.lab4.Main.logger;

public class ComplexFrame extends JFrame {

    private Complex complex;
    private boolean restart = false;
    private BorderPanel borderPanel;
    private JPanel buttonPanel = new JPanel();
    private StartAction startAction;
    private StopAction stopAction;
    private UrgentStopAction urgentStopAction;
    private final WindowHandler windowHandlerAll;
    private final WindowHandler windowHandlerInfo;

    public ComplexFrame(Logger mainLogger) {
        windowHandlerAll = new WindowHandler("config logs");
        windowHandlerAll.setLevel(Level.ALL);
        windowHandlerInfo = new WindowHandler("info logs");
        windowHandlerInfo.setLevel(Level.INFO);
        mainLogger.addHandler(windowHandlerAll);
        mainLogger.addHandler(windowHandlerInfo);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setSize(screenSize.width, screenSize.height - 50);
        setLocationRelativeTo(null);
        setResizable(true);
        setTitle("Transport company");
        setIconImage(new ImageIcon("images/train.png").getImage());
        addButtonPanel();
        try {
            complex = new Complex();
        } catch (Exception e) {
            e.printStackTrace();
        }
        add(borderPanel = new BorderPanel(complex), BorderLayout.CENTER);
    }

    private void addButtonPanel() {
        buttonPanel = new JPanel();
        addActionButtonAndKey(startAction = new StartAction(),"ctrl Z","buttonPanel.start");
        addActionButtonAndKey(stopAction = new StopAction(),"ctrl X","buttonPanel.stop");
        addActionButtonAndKey(urgentStopAction = new UrgentStopAction(),"ctrl C","buttonPanel.urgent_stop");
        stopAction.setEnabled(false);
        urgentStopAction.setEnabled(false);
        addActionButtonAndKey(new showAllLogsAction(),"ctrl A","buttonPanel.showAllLogs");
        addActionButtonAndKey(new showInfoLogsAction(),"ctrl L","buttonPanel.showInfoLogs");
        buttonPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.ORANGE));
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private class StartAction extends AbstractAction {

        public StartAction() {
            putValue(Action.NAME, "start");
            putValue(Action.SMALL_ICON, new ImageIcon(new ImageIcon("images/start.png").getImage().
                    getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
            putValue(Action.SHORT_DESCRIPTION, "start the complex");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                logger.info("Constructing complex...");
                if (restart) {
                    complex = new Complex();
                    borderPanel.setVisible(false);
                    borderPanel = new BorderPanel(complex);
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
            putValue(Action.SHORT_DESCRIPTION, "stop the complex");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                complex.stop();
                logger.info("Complex stopped working.");
                startAction.setEnabled(true);
                setEnabled(false);
                urgentStopAction.setEnabled(false);
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
            putValue(Action.SHORT_DESCRIPTION, "stop the complex immediately!");
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

    private class showAllLogsAction extends AbstractAction{

        public showAllLogsAction() {
            putValue(Action.NAME, "all logs");
            putValue(Action.SHORT_DESCRIPTION, "show logs with INFO logging level");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            windowHandlerAll.setVisible(true);
        }
    }

    private class showInfoLogsAction extends AbstractAction{

        public showInfoLogsAction() {
            putValue(Action.NAME, "info logs");
            putValue(Action.SHORT_DESCRIPTION, "show logs with all logging levels");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            windowHandlerInfo.setVisible(true);
        }
    }

    private void addActionButtonAndKey(Action action, String key, String keyObject) {
        buttonPanel.add(new JButton(action));
        InputMap inputMap = buttonPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(key),keyObject);
        buttonPanel.getActionMap().put(keyObject,action);
    }

}
