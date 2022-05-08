package ru.nsu.fit.oop.game.view;

import ru.nsu.fit.oop.game.model.GameObjectsInfo;
import ru.nsu.fit.oop.game.model.entity.game_object.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.List;

public class GameField extends JComponent {

    private final int windowSizeX;
    private final int windowSizeY;
    private GameObjectsInfo gameObjectsInfo;

    public GameField(int windowSizeX, int windowSizeY) {
        setVisible(true);
        setBackground(Color.WHITE);
        setOpaque(true);
        this.windowSizeX = windowSizeX;
        this.windowSizeY = windowSizeY;
    }

    public void setGameObjectsInfo(GameObjectsInfo gameObjectsInfo) {
        this.gameObjectsInfo = gameObjectsInfo;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        super.paintComponent(g);
        if (null == gameObjectsInfo)
            return;
        Ellipse2D heroEllipse = new Ellipse2D.Double(
                windowSizeX / 2 - gameObjectsInfo.getHero().getRadius(),
                windowSizeY / 2 - gameObjectsInfo.getHero().getRadius() - 20,
                gameObjectsInfo.getHero().getSize(),
                gameObjectsInfo.getHero().getSize());
        g2d.draw(heroEllipse);
        drawShellsAndEnemies(g2d);
    }

    private void drawShellsAndEnemies(Graphics2D g2d) {
        if (null == gameObjectsInfo.getShellsAndEnemies())
            return;
        for (GameObject gameObject : gameObjectsInfo.getShellsAndEnemies()) {
            if (checkOnScreen(gameObject.getCoords(),gameObject.getRadius(),
                    gameObjectsInfo.getHero().getCoords())) {
                Ellipse2D enemyEllipse = new Ellipse2D.Double(gameObject.getX() - gameObject.getRadius(),
                        gameObject.getY() - gameObject.getRadius(), gameObject.getSize(),
                        gameObject.getSize());
                g2d.draw(enemyEllipse);
            }
        }
    }

    private boolean checkOnScreen(Point2D.Double coords, int radius, Point2D.Double heroCoords) {
        if (coords.getY() - radius > heroCoords.getY() + windowSizeY / 2)
            return false;
        if (coords.getX() - radius > heroCoords.getX() + windowSizeX / 2)
            return false;
        if (coords.getY() + radius < heroCoords.getY() - windowSizeY / 2)
            return false;
        if (coords.getX() + radius < heroCoords.getY() - windowSizeX / 2)
            return false;
        return false;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(windowSizeX, windowSizeY);
    }
}
