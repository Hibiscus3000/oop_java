package ru.nsu.fit.oop.game.view;

import ru.nsu.fit.oop.game.model.GameObjectsInfo;
import ru.nsu.fit.oop.game.model.Model;
import ru.nsu.fit.oop.game.model.entity.game_object.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class GameField extends JComponent {

    Model model;
    private final int windowSizeX;
    private final int windowSizeY;
    private GameObjectsInfo gameObjectsInfo;

    public GameField(int windowSizeX, int windowSizeY, Model model) {
        setVisible(true);
        setOpaque(false);
        this.windowSizeX = windowSizeX;
        this.windowSizeY = windowSizeY;
        this.model = model;
        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent event) {
            double x = (double) (event.getX() - windowSizeX / 2);
            double y = (double) (event.getY() - windowSizeY / 2);
            if (0 == x) {
                if (y > 0)
                    model.heroUseWeapon(Math.PI / 2);
                else
                    model.heroUseWeapon(Math.PI / 2);
            }
            if (x > 0) {
                model.heroUseWeapon(Math.atan(y / x));
            }
            else if (y >= 0) {
                model.heroUseWeapon(Math.PI + Math.atan(y / x));
            }
            else if (y < 0) {
                model.heroUseWeapon(-Math.PI + Math.atan(y / x));
            }
        }
    }

    private class MouseMotionHandler extends MouseMotionAdapter {
        @Override
        public void mouseMoved(MouseEvent event) {
            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
    }

    public void setGameObjectsInfo(GameObjectsInfo gameObjectsInfo) {
        this.gameObjectsInfo = gameObjectsInfo;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);
        if (null == gameObjectsInfo)
            return;
        Ellipse2D heroEllipse = new Ellipse2D.Double(
                windowSizeX / 2 - gameObjectsInfo.getHero().getRadius(),
                windowSizeY / 2 - gameObjectsInfo.getHero().getRadius(),
                gameObjectsInfo.getHero().getSize(),
                gameObjectsInfo.getHero().getSize());
        g2d.draw(heroEllipse);
        drawShellsAndEnemies(g2d);
    }

    private void drawShellsAndEnemies(Graphics2D g2d) {
        if (null == gameObjectsInfo.getShellsAndEnemies())
            return;
        for (GameObject gameObject : gameObjectsInfo.getShellsAndEnemies()) {
            if (checkOnScreen(gameObject.getCoords(), gameObject.getSize(),
                    gameObjectsInfo.getHero().getCoords())) {
                Ellipse2D objectEllipse = new Ellipse2D.Double(
                        gameObject.getX() + windowSizeX / 2 - gameObjectsInfo.getHero().getX()
                                - gameObject.getRadius(),
                        gameObject.getY() + windowSizeY / 2 - gameObjectsInfo.getHero().getY()
                                - gameObject.getRadius(),
                        gameObject.getSize(),
                        gameObject.getSize());
                g2d.draw(objectEllipse);
            }
        }
    }

    private boolean checkOnScreen(Point2D.Double coords, int size, Point2D.Double heroCoords) {
        if (coords.getY() - size > heroCoords.getY() + windowSizeY / 2)
            return false;
        if (coords.getX() - size > heroCoords.getX() + windowSizeX / 2)
            return false;
        if (coords.getY() + size < heroCoords.getY() - windowSizeY / 2)
            return false;
        if (coords.getX() + size < heroCoords.getX() - windowSizeX / 2)
            return false;
        return true;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(windowSizeX, windowSizeY);
    }

}
