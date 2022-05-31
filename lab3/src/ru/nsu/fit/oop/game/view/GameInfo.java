package ru.nsu.fit.oop.game.view;

import ru.nsu.fit.oop.game.model.GameObjectsInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static java.lang.System.exit;

public class GameInfo extends JComponent {

    private Font font = new Font("SansSerif", Font.BOLD, 14);
    private Font statisticsFont = new Font("SansSerif", Font.BOLD,18);
    private BufferedImage heroPortrait;
    private BasicStroke borderStroke = new BasicStroke(6.0f);
    private BasicStroke basicStroke = new BasicStroke(2.0f);

    public GameInfo(int windowSizeY) {
        setPreferredSize(new Dimension(300,windowSizeY));
        try {
            heroPortrait = ImageIO.read(getClass().getResourceAsStream("hero.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(borderStroke);
        g2d.setColor(new Color(0,0,130));
        g2d.drawLine(7,7,7,163);
        g2d.drawLine(163,163,7,163);
        g2d.drawLine(163,7,7,7);
        g2d.drawLine(163,163,163,7);
        g2d.drawImage(heroPortrait,10,10,150,150,this);
    }
}
