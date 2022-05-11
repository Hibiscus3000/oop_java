package ru.nsu.fit.oop.game.view;

import ru.nsu.fit.oop.game.model.GameObjectsInfo;
import ru.nsu.fit.oop.game.model.Model;
import ru.nsu.fit.oop.game.model.entity.game_object.GameObjectParams;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class GameField extends JComponent {

    private Model model;
    private final int windowSizeX;
    private final int windowSizeY;
    private GameObjectsInfo gameObjectsInfo;
    private BasicStroke basicStroke = new BasicStroke(2.0f);

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
            else if (x > 0) {
                model.heroUseWeapon(Math.atan(y / x));
            } else if (y >= 0) {
                model.heroUseWeapon(Math.PI + Math.atan(y / x));
            } else {
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
                windowSizeX / 2 - gameObjectsInfo.getHeroParams().getRadius(),
                windowSizeY / 2 - gameObjectsInfo.getHeroParams().getRadius(),
                gameObjectsInfo.getHeroParams().getSize(),
                gameObjectsInfo.getHeroParams().getSize());
        drawWalls(g2d);
        g2d.setStroke(basicStroke);
        g2d.draw(heroEllipse);
        drawShellsAndEnemies(g2d);
    }

    private void drawWalls(Graphics2D g2d) {
        for (int i = 0; i < gameObjectsInfo.getWallsNumber(); ++i) {
            g2d.setStroke(new BasicStroke((float) gameObjectsInfo.getWallThickness(i)));
            g2d.draw(new Line2D.Double(gameObjectsInfo.getWallStartPoint(i).getX() +
                    windowSizeX / 2 - gameObjectsInfo.getHeroParams().getX(),
                    gameObjectsInfo.getWallStartPoint(i).getY() + windowSizeY / 2 -
                            gameObjectsInfo.getHeroParams().getY(),
                    gameObjectsInfo.getWallEndPoint(i).getX() + windowSizeX / 2 -
                            gameObjectsInfo.getHeroParams().getX(),
                    gameObjectsInfo.getWallEndPoint(i).getY() + windowSizeY / 2 -
                            gameObjectsInfo.getHeroParams().getY()));
        }

    }

    private void drawShellsAndEnemies(Graphics2D g2d) {
        if (null == gameObjectsInfo.getEnemiesParams())
            return;
        for (GameObjectParams enemyParams : gameObjectsInfo.getEnemiesParams()) {
            if (checkOnScreen(enemyParams.getCoords(), enemyParams.getSize(),
                    gameObjectsInfo.getHeroParams().getCoords())) {
                Ellipse2D objectEllipse = new Ellipse2D.Double(
                        enemyParams.getX() + windowSizeX / 2 -
                                gameObjectsInfo.getHeroParams().getX() - enemyParams.getRadius(),
                        enemyParams.getY() + windowSizeY / 2 -
                                gameObjectsInfo.getHeroParams().getY() - enemyParams.getRadius(),
                        enemyParams.getSize(),
                        enemyParams.getSize());
                g2d.draw(objectEllipse);
            }
        }
        for (GameObjectParams shellParams : gameObjectsInfo.getShellsParams()) {
            if (checkOnScreen(shellParams.getCoords(), shellParams.getSize(),
                    gameObjectsInfo.getHeroParams().getCoords())) {
                Ellipse2D objectEllipse = new Ellipse2D.Double(
                        shellParams.getX() + windowSizeX / 2 -
                                gameObjectsInfo.getHeroParams().getX() - shellParams.getRadius(),
                        shellParams.getY() + windowSizeY / 2 -
                                gameObjectsInfo.getHeroParams().getY() - shellParams.getRadius(),
                        shellParams.getSize(),
                        shellParams.getSize());
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
