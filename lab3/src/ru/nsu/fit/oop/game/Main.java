package ru.nsu.fit.oop.game;
import ru.nsu.fit.oop.game.view.View;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                View view = new View();
            }
        });
    }
}
