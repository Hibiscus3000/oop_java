package ru.nsu.fit.oop.lab4.view;

import ru.nsu.fit.oop.lab4.Complex;
import ru.nsu.fit.oop.lab4.view.panel.BorderPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static ru.nsu.fit.oop.lab4.Main.logger;

public class ComplexFrame extends JFrame {

    private Complex complex;
    private boolean restart = false;

    public ComplexFrame() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setSize(screenSize.width, screenSize.height);
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

        add(new BorderPanel(complex), BorderLayout.CENTER);
    }

    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        StartAction startAction = new StartAction();
        AbstractStopAction stopAction = new StopAction(startAction);
        AbstractStopAction urgentStopAction = new UrgentStopAction(startAction);
        stopAction.addAnotherStopAction(urgentStopAction);
        urgentStopAction.addAnotherStopAction(stopAction);
        stopAction.disableStopActions();
        startAction.addStopAction(stopAction).addUrgentStopAction(urgentStopAction);
        addNewAction(buttonPanel,startAction,"ctrl Z","buttonPanel.start");
        addNewAction(buttonPanel,stopAction,"ctrl X","buttonPanel.stop");
        addNewAction(buttonPanel,urgentStopAction,"ctrl C","buttonPanel.urgent_stop");
        buttonPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.ORANGE));
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private class StartAction extends AbstractAction {

        private Action stopAction;
        private Action urgentStopAction;

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

        public StartAction addStopAction(Action stopAction) {
            this.stopAction = stopAction;
            return this;
        }

        public StartAction addUrgentStopAction(Action urgentStopAction) {
            this.urgentStopAction = urgentStopAction;
            return this;
        }
    }

    private abstract class AbstractStopAction extends AbstractAction {

        private Action startAction;
        private Action anotherStopAction;

        protected AbstractStopAction(Action startAction) {
            this.startAction = startAction;
        }

        protected void enableStartAction() {
            startAction.setEnabled(true);
        }

        public void disableStopActions() {
            anotherStopAction.setEnabled(false);
            setEnabled(false);
        }

        public void addAnotherStopAction(AbstractStopAction anotherStopAction) {
            this.anotherStopAction = anotherStopAction;
        }
    }

    private class StopAction extends AbstractStopAction {

        public StopAction(Action startAction) {
            super(startAction);
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
                enableStartAction();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private class UrgentStopAction extends AbstractStopAction {

        public UrgentStopAction(Action startAction) {
            super(startAction);
            putValue(Action.NAME, "urgent stop");
            putValue(Action.SMALL_ICON, new ImageIcon(new ImageIcon("images/urgent_stop.png").getImage().
                    getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
            putValue(Action.SHORT_DESCRIPTION, "stop the complex immediately!");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            complex.stopUrgently();
            logger.info("Complex was urgently stopped.");
            enableStartAction();
            disableStopActions();
        }
    }

    private void addNewAction(JPanel buttonPanel, Action action, String key, String keyObject) {
        buttonPanel.add(new JButton(action));
        InputMap inputMap = buttonPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(key),keyObject);
        buttonPanel.getActionMap().put(keyObject,action);
    }

}
