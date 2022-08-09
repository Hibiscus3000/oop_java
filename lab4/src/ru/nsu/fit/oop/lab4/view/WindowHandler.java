package ru.nsu.fit.oop.lab4.view;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

public class WindowHandler extends StreamHandler {

    private final JFrame frame;

    public WindowHandler(String title) {
        frame = new JFrame();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        frame.setTitle(title);
        frame.setSize(2 * screenSize.width / 5, 2 * screenSize.height / 5);
        final JTextArea output = new JTextArea();
        setOutputStream(new OutputStream() {
            @Override
            public void write(int b) {}

            @Override
            public void write(byte[] b, int off, int len) {
                output.append(new String(b,off,len));
            }
        });
    }

    @Override
    public void publish(LogRecord record) {
        if (!frame.isVisible())
            return;
        super.publish(record);
        flush();
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }
}
