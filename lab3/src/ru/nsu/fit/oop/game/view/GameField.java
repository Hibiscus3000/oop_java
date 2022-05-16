package ru.nsu.fit.oop.game.view;

import ru.nsu.fit.oop.game.model.GameObjectsInfo;
import ru.nsu.fit.oop.game.model.Model;
import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Unit;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy.Enemy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.font.FontRenderContext;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class GameField extends JComponent {

    private Model model;
    private final int windowSizeX;
    private final int windowSizeY;
    private GameObjectsInfo gameObjectsInfo;
    private BasicStroke basicStroke = new BasicStroke(2.0f);
    private Font font = new Font("SansSerif", Font.BOLD, 14);
    private Font statisticsFont = new Font("SansSerif", Font.BOLD,18);

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
            } else if (x > 0) {
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
        g2d.setFont(font);
        FontRenderContext context = g2d.getFontRenderContext();
        drawWalls(g2d);
        g2d.setStroke(basicStroke);
        drawShells(g2d);
        drawEnemies(g2d,context);
        drawHero(g2d,context);
        g2d.setColor(Color.RED);
        g2d.setFont(statisticsFont);
        String health = new String("Health: " + gameObjectsInfo.getHero().getHealth() + '/' +
                gameObjectsInfo.getHero().getMaxHealth());
        g2d.drawString(health,20,20);
        g2d.setFont(font);
    }

    private void drawHero(Graphics2D g2d, FontRenderContext context) {
        Ellipse2D heroEllipse = new Ellipse2D.Double(
                windowSizeX / 2 - gameObjectsInfo.getHero().getRadius(),
                windowSizeY / 2 - gameObjectsInfo.getHero().getRadius(),
                gameObjectsInfo.getHero().getSize(),
                gameObjectsInfo.getHero().getSize());
        g2d.draw(heroEllipse);
        g2d.setColor(new Color(0,0,150));
        g2d.fill(heroEllipse);
        g2d.setColor(Color.BLACK);
        drawName(g2d,context,gameObjectsInfo.getHero());
    }

    private void drawWalls(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke((float) 1));
        for (int i = 0; i < gameObjectsInfo.getWallsNumber(); ++i) {
            for (int j = 0; j < 4; ++j) {
                g2d.draw(new Line2D.Double(gameObjectsInfo.getWallPartStartPoint(i, j).getX() +
                        windowSizeX / 2 - gameObjectsInfo.getHero().getX(),
                        gameObjectsInfo.getWallPartStartPoint(i, j).getY() + windowSizeY / 2 -
                                gameObjectsInfo.getHero().getY(),
                        gameObjectsInfo.getWallPartEndPoint(i, j).getX() + windowSizeX / 2 -
                                gameObjectsInfo.getHero().getX(),
                        gameObjectsInfo.getWallPartEndPoint(i, j).getY() + windowSizeY / 2 -
                                gameObjectsInfo.getHero().getY()));
            }
            /*g2d.setStroke(new BasicStroke((float) gameObjectsInfo.getWallThickness(i)));
            g2d.draw(new Line2D.Double(gameObjectsInfo.getWallStartPoint(i).getX() +
                    windowSizeX / 2 - gameObjectsInfo.getHero().getX(),
                    gameObjectsInfo.getWallStartPoint(i).getY() + windowSizeY / 2 -
                            gameObjectsInfo.getHero().getY(),
                    gameObjectsInfo.getWallEndPoint(i).getX() + windowSizeX / 2 -
                            gameObjectsInfo.getHero().getX(),
                    gameObjectsInfo.getWallEndPoint(i).getY() + windowSizeY / 2 -
                            gameObjectsInfo.getHero().getY()));*/
        }
    }

    private void drawShells(Graphics2D g2d) {
        if (null != gameObjectsInfo.getShells())
            for (Shell shell : gameObjectsInfo.getShells()) {
                if (checkOnScreen(shell.getCoords(), shell.getSize(),
                        gameObjectsInfo.getHero().getCoords())) {
                    Ellipse2D shellEllipse = new Ellipse2D.Double(
                            shell.getX() + windowSizeX / 2 -
                                    gameObjectsInfo.getHero().getX() - shell.getRadius(),
                            shell.getY() + windowSizeY / 2 -
                                    gameObjectsInfo.getHero().getY() - shell.getRadius(),
                            shell.getSize(),
                            shell.getSize());
                    g2d.draw(shellEllipse);
                    g2d.fill(shellEllipse);
                }
            }
    }

    private void drawName(Graphics2D g2d, FontRenderContext context, Unit unit) {
        Rectangle2D bounds = font.getStringBounds(unit.getName(), context);
        double x = (unit.getX() + windowSizeX/2  - gameObjectsInfo.getHero().getX() - bounds.getWidth()
                / 2);
        double y = (unit.getY() + windowSizeY/2 - gameObjectsInfo.getHero().getY() - unit.getRadius()
                - bounds.getHeight() / 2);
        g2d.drawString(unit.getName(), (float) x, (float) y);
    }

    private void drawEnemies(Graphics2D g2d, FontRenderContext context) {
        if (null == gameObjectsInfo.getEnemies())
            return;
        for (Enemy enemy : gameObjectsInfo.getEnemies()) {
            if (checkOnScreen(enemy.getCoords(), enemy.getSize(),
                    gameObjectsInfo.getHero().getCoords())) {
                drawName(g2d,context,enemy);
                Ellipse2D enemyEllipse = new Ellipse2D.Double(
                        enemy.getX() + windowSizeX / 2 -
                                gameObjectsInfo.getHero().getX() - enemy.getRadius(),
                        enemy.getY() + windowSizeY / 2 -
                                gameObjectsInfo.getHero().getY() - enemy.getRadius(),
                        enemy.getSize(),
                        enemy.getSize());
                g2d.draw(enemyEllipse);
                g2d.setColor(new Color(180,0,0));
                g2d.fill(enemyEllipse);
                g2d.setColor(Color.BLACK);
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
