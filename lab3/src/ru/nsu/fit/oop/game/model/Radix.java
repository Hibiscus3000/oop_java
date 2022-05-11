package ru.nsu.fit.oop.game.model;

import ru.nsu.fit.oop.game.exception.model.ModelException;
import ru.nsu.fit.oop.game.exception.model.UnableToUseWeaponException;
import ru.nsu.fit.oop.game.model.entity.game_object.GameObject;
import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Hero;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Unit;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy.Enemy;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy.EnemyFrameProduction;
import ru.nsu.fit.oop.game.model.entity.game_object.wall.GameWalls;
import ru.nsu.fit.oop.game.model.entity.game_object.wall.Wall;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Radix {

    private int fieldSizeX;
    private int fieldSizeY;
    private GameWalls gameWalls;
    private volatile Hero hero;
    private List<Enemy> enemies;
    private volatile List<Shell> shells = new ArrayList<>();
    private GameObjectsInfo gameObjectsInfo;

    public Radix(int fieldSizeX, int fieldSizeY, GameWalls gameWalls) {
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        this.gameWalls = gameWalls;
    }

    public int getNumberOfEnemies() {
        return enemies.size();
    }

    public Dimension getFieldSize() {
        return new Dimension(fieldSizeX, fieldSizeY);
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
        for (Enemy enemy : enemies) {
            setEnemyCoords(enemy);
            while (null != checkCollisions(enemy))
                setEnemyCoords(enemy);
        }
    }

    public void setHero(Hero hero) {
        this.hero = hero;
        hero.setCoords(fieldSizeX / 2 + new Random().nextDouble(100) - 50, fieldSizeY / 2
                + new Random().nextDouble(100) - 50);
        while (null != checkCollisions(hero))
            hero.setCoords(fieldSizeX / 2 + new Random().nextDouble(100) - 50, fieldSizeY / 2
                    + new Random().nextDouble(100) - 50);
    }

    private void setEnemyCoords(Enemy enemy) {
        enemy.setCoords(new Random().nextDouble(fieldSizeX - 2 * enemy.getRadius()) +
                enemy.getRadius(), new Random().nextDouble(fieldSizeY - 2 *
                enemy.getRadius()) + enemy.getRadius());
    }

    public Point2D.Double getHeroCoords() {
        return new Point2D.Double(hero.getX(), hero.getY());
    }

    public void heroUseWeapon(double angle) throws ModelException {
        try {
            Shell shell = hero.useWeapon(angle);
            if (null != shell)
                shells.add(shell);
        } catch (UnableToUseWeaponException e) {
            throw new ModelException(e);
        }
    }

    public void moveHero(double angle) {
        hero.move(angle);
        if (null != checkCollisions(hero))
            hero.move(angle + Math.PI);
    }

    public boolean getHeroInGameStatus() {
        return hero.getInGameStatus();
    }

    public void updateGameField() {
        handleWalls();
        handleEnemies();
        handleShells();
        gameObjectsInfo.renew(enemies, shells);
    }

    private void handleWalls() {
        if (null == gameWalls)
            return;
        int i = 0, size = gameWalls.getWallsNumber();
        while (i < size) {
            if (false == gameWalls.getWallInGameStatus(i)) {
                gameWalls.removeWall(i);
                --size;
                continue;
            }
            ++i;
        }
    }

    private void handleEnemies() {
        if (null == enemies)
            return;
        int i = 0, size = enemies.size();
        while (i < size) {
            if (false == enemies.get(i).getInGameStatus()) {
                enemies.remove(i);
                --size;
                continue;
            }
            ++i;
        }
        try {
            for (Enemy enemy : enemies) {
                EnemyFrameProduction enemyFrameProduction = enemy.enemyFrameTurn(gameObjectsInfo);
                if (null != enemyFrameProduction.getShell())
                    shells.add(enemyFrameProduction.getShell());
                if (null != checkCollisions(enemy)) {
                    enemy.move(enemy.getAngle() + Math.PI);
                }
            }
        } catch (UnableToUseWeaponException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void handleShells() {
        if (null == shells)
            return;
        int i = 0, size = shells.size();
        while (i < size) {
            if (false == shells.get(i).getInGameStatus()) {
                shells.remove(i);
                --size;
                continue;
            }
            ++i;
        }
        for (Shell shell : shells) {
            shell.move();
            GameObject collided = checkCollisions(shell);
            if (null != collided) {
                toDamage(shell, collided);
            }
        }
    }

    private GameObject checkCollisions(GameObject gameObject) {
        GameObject collided;
        if (null != (collided = checkCollisionsWithWalls(gameObject)))
            return collided;
        return checkCollisionsWithUnits(gameObject);
    }

    private Wall checkCollisionsWithWalls(GameObject gameObject) {
        if (null != gameWalls) {
            for (int i = 0; i < gameWalls.getWallsNumber(); ++i) {
                if (0 == gameWalls.getWallStartPoint(i).getX() - gameWalls.getWallEndPoint(i).getX()) {
                        if (Math.abs(gameWalls.getWallStartPoint(i).getX() - gameObject.getX()) <=
                        gameObject.getRadius() + gameWalls.getWallThickness(i) / 2) {
                            return gameWalls.getWall(i);
                        }
                        else
                            continue;
                }
                if (0 == gameWalls.getWallStartPoint(i).getY() - gameWalls.getWallEndPoint(i).getY()) {
                    if (Math.abs(gameWalls.getWallStartPoint(i).getY() - gameObject.getY()) <=
                            gameObject.getRadius() + gameWalls.getWallThickness(i) / 2) {
                        return gameWalls.getWall(i);
                    }
                    else
                        continue;
                }
                double xMax = (gameWalls.getWallEndPoint(i).getX() > gameWalls.getWallStartPoint(i).getX())
                        ? gameWalls.getWallEndPoint(i).getX() : gameWalls.getWallStartPoint(i).getX();
                double xMin = (gameWalls.getWallEndPoint(i).getX() > gameWalls.getWallStartPoint(i).getX())
                        ? gameWalls.getWallStartPoint(i).getX() : gameWalls.getWallEndPoint(i).getX();
                if (gameObject.getY() + gameObject.getRadius() - gameWalls.getWallStartPoint(i).getY() < 0) {
                    continue;
                }
                if (gameWalls.getWallEndPoint(i).getY() - gameObject.getY() + gameObject.getRadius() < 0) {
                    continue;
                }
                if (gameObject.getX() + gameObject.getRadius() - xMin < 0) {
                    continue;
                }
                if (xMax - gameObject.getX() + gameObject.getRadius() < 0) {
                    continue;
                }
                if (gameWalls.getWallAngle(i) > Math.PI / 2) {
                    if (Math.abs((xMax - gameObject.getX()) * Math.sin(Math.PI - gameWalls.getWallAngle(i)) -
                            (gameObject.getY() - gameWalls.getWallStartPoint(i).getY()) * Math.cos(Math.PI -
                                    gameWalls.getWallAngle(i))) <= gameObject.getRadius() +
                            gameWalls.getWallThickness(i) / 2) {
                        return gameWalls.getWall(i);
                    }
                }
                else if (Math.abs((gameObject.getX() - xMin) * Math.sin(gameWalls.getWallAngle(i)) -
                        (gameObject.getY() - gameWalls.getWallStartPoint(i).getY()) *
                                Math.cos(gameWalls.getWallAngle(i))) <= gameObject.getRadius() +
                        gameWalls.getWallThickness(i) / 2) {
                    return gameWalls.getWall(i);
                }
            }
        }
        return null;
    }

    private Unit checkCollisionsWithUnits(GameObject gameObject) {
        if (null != enemies)
            for (Enemy enemy : enemies) {
                if (gameObject == enemy)
                    continue;
                if ((gameObject.getRadius() + enemy.getRadius()) * (gameObject.getRadius() + enemy.getRadius())
                        >= Math.pow(gameObject.getX() - enemy.getX(), 2) +
                        Math.pow(gameObject.getY() - enemy.getY(), 2)) {
                    return enemy;
                }
            }
        if ((gameObject.getRadius() + hero.getRadius()) * (gameObject.getRadius() + hero.getRadius())
                >= Math.pow(gameObject.getX() - hero.getX(), 2) +
                Math.pow(gameObject.getY() - hero.getY(), 2) && gameObject != hero) {
            return hero;
        }
        return null;
    }

    private void toDamage(Shell shell, GameObject gameObject) {
        gameObject.takeDamage(shell.getDamage(), shell.getAngle());
        try {
            shell.bounce(((Wall) gameObject).getNormalAngle());
        } catch (ClassCastException e) {
            shell.setInGameFalse();
        }
    }

    public void setGameObjectsInfo(GameObjectsInfo gameObjectsInfo) {
        this.gameObjectsInfo = gameObjectsInfo;
    }
}
