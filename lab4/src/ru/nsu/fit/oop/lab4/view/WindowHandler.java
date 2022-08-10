package ru.nsu.fit.oop.lab4.view;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
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
        output.setLineWrap(true);
        DefaultCaret caret = (DefaultCaret)output.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        frame.add(new JScrollPane(output));
        frame.setLocationRelativeTo(null);
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
        super.publish(record);
        flush();
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }
}
