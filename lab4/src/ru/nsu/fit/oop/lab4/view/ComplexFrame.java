package ru.nsu.fit.oop.lab4.view;

import ru.nsu.fit.oop.lab4.Complex;
import ru.nsu.fit.oop.lab4.Main;
import ru.nsu.fit.oop.lab4.exception.BadNumberOfTracks;
import ru.nsu.fit.oop.lab4.exception.InvalidConfigException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import static ru.nsu.fit.oop.lab4.Main.logger;

public class ComplexFrame extends JFrame {

    private Complex complex;

    public ComplexFrame() {
        setLocationRelativeTo(null);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setSize(3 * screenSize.width / 4, 3 * screenSize.height / 4);
        setResizable(true);
        setTitle("Transport company");
        setIconImage(new ImageIcon("images/train.png").getImage());
    }

    private class StartAction extends AbstractAction {

        public StartAction() {
            putValue(Action.NAME, "start");
            putValue(Action.SMALL_ICON, "images/start.png");
            putValue(Action.SHORT_DESCRIPTION, "start the complex");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                logger.info("Constructing complex...");
                complex = new Complex();
                logger.info("Complex constructed, complex starts working...");
                complex.start();
                logger.info("Complex started working.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private class StopAction extends AbstractAction {

        public StopAction() {
            putValue(Action.NAME, "stop");
            putValue(Action.SMALL_ICON, "images/stop.png");
            putValue(Action.SHORT_DESCRIPTION, "stop the complex");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                complex.stop();
                logger.info("Complex stopped working.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
