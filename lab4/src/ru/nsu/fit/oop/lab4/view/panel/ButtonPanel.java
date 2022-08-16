package ru.nsu.fit.oop.lab4.view.panel;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {

    private final double sizeScale = 1.0 / 15.0;
    private final double frameSizeScale;

    public ButtonPanel(double frameSizeScale) {
        this.frameSizeScale = frameSizeScale;
    }

    @Override
    public Dimension getPreferredSize() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        return new Dimension((int) (screenSize.width * frameSizeScale * sizeScale),
                (int) (screenSize.height * frameSizeScale * sizeScale));
    }

    public double getSizeScale() {
        return sizeScale;
    }
}
