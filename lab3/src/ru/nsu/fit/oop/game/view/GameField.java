package ru.nsu.fit.oop.game.view;

import ru.nsu.fit.oop.game.model.GameObjectsInfo;
import ru.nsu.fit.oop.game.model.entity.game_object.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.List;

public class GameField extends JComponent {

    private final int screenSizeX;
    private final int screenSizeY;
    private GameObjectsInfo gameObjectsInfo;

    public GameField(int screenSizeX, int screenSizeY, GameObjectsInfo gameObjectsInfo) {
        this.screenSizeX = screenSizeX;
        this.screenSizeY = screenSizeY;
        this.gameObjectsInfo = gameObjectsInfo;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        super.paintComponent(g);
        Ellipse2D heroEllipse = new Ellipse2D.Double(
                screenSizeX / 2 - gameObjectsInfo.getHeroes().get(0).getRadius(),
                screenSizeY / 2 - gameObjectsInfo.getHeroes().get(0).getRadius(),
                gameObjectsInfo.getHeroes().get(0).getSize(),
                gameObjectsInfo.getHeroes().get(0).getSize());
        g2d.draw(heroEllipse);
        drawGameObjects(g2d,gameObjectsInfo.getEnemies());
        drawGameObjects(g2d,gameObjectsInfo.getHeroShells());
        drawGameObjects(g2d,gameObjectsInfo.getEnemyShells());
    }

    private void drawGameObjects(Graphics2D g2d,List<GameObject> gameObjectsList) {
        for (GameObject gameObject : gameObjectsList) {
            if (checkOnScreen(gameObject.getCoords(),gameObject.getRadius(),
                    gameObjectsInfo.getHeroes().get(0).getCoords())) {
                Ellipse2D enemyEllipse = new Ellipse2D.Double(gameObject.getX() - gameObject.getRadius(),
                        gameObject.getY() - gameObject.getRadius(), gameObject.getSize(),
                        gameObject.getSize());
                g2d.draw(enemyEllipse);
            }
        }
    }

    private boolean checkOnScreen(Point2D.Double coords, int radius, Point2D.Double heroCoords) {
        if (coords.getY() - radius > heroCoords.getY() + screenSizeY / 2)
            return false;
        if (coords.getX() - radius > heroCoords.getX() + screenSizeX / 2)
            return false;
        if (coords.getY() + radius < heroCoords.getY() - screenSizeY / 2)
            return false;
        if (coords.getX() + radius < heroCoords.getY() - screenSizeX / 2)
            return false;
        return false;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(screenSizeX,screenSizeY);
    }
}
