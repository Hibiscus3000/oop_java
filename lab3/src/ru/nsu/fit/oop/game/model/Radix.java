package ru.nsu.fit.oop.game.model;

import ru.nsu.fit.oop.game.exception.model.ModelException;
import ru.nsu.fit.oop.game.exception.model.UnableToUseWeaponException;
import ru.nsu.fit.oop.game.model.entity.game_object.GameObject;
import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Hero;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Unit;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy.Enemy;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy.EnemyFrameProduction;
import ru.nsu.fit.oop.game.model.entity.game_object.wall.Wall;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Random;

public class Radix {

    private int fieldSizeX;
    private int fieldSizeY;
    private GameObjectsInfo gameObjectsInfo;

    public Radix(int fieldSizeX, int fieldSizeY, GameObjectsInfo gameObjectsInfo) {
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        this.gameObjectsInfo = gameObjectsInfo;
        setHeroCoords();
    }

    public Dimension getFieldSize() {
        return new Dimension(fieldSizeX, fieldSizeY);
    }

    public void setEnemies(List<Enemy> enemies) {
        gameObjectsInfo.setEnemies(enemies);
        for (Enemy enemy : enemies) {
            setEnemyCoords(enemy);
            while (null != checkCollisions(enemy))
                setEnemyCoords(enemy);
        }
    }

    public void setHeroCoords() {
        gameObjectsInfo.getHero().setCoords(fieldSizeX / 2 + new Random().nextDouble(100) - 50,
                fieldSizeY / 2 + new Random().nextDouble(100) - 50);
        while (null != checkCollisions(gameObjectsInfo.getHero()))
            gameObjectsInfo.getHero().setCoords(fieldSizeX / 2 + new Random().nextDouble(100)
                    - 50, fieldSizeY / 2 + new Random().nextDouble(100) - 50);
    }

    private void setEnemyCoords(Enemy enemy) {
        enemy.setCoords(new Random().nextDouble(fieldSizeX - 2 * enemy.getRadius()) +
                enemy.getRadius(), new Random().nextDouble(fieldSizeY - 2 *
                enemy.getRadius()) + enemy.getRadius());
    }

    public Point2D.Double getHeroCoords() {
        return new Point2D.Double(gameObjectsInfo.getHero().getX(), gameObjectsInfo.getHero().getY());
    }

    public void heroUseWeapon(double angle) throws ModelException {
        try {
            Shell shell = gameObjectsInfo.getHero().useWeapon(angle);
            if (null != shell)
                gameObjectsInfo.addShell(shell);
        } catch (UnableToUseWeaponException e) {
            throw new ModelException(e);
        }
    }

    public void moveHero(double angle) {
        gameObjectsInfo.getHero().move(angle);
        if (null != checkCollisions(gameObjectsInfo.getHero()))
            gameObjectsInfo.getHero().move(angle + Math.PI);
    }

    public boolean getHeroInGameStatus() {
        return gameObjectsInfo.getHero().getInGameStatus();
    }

    public void updateGameField() {
        handleWalls();
        handleEnemies();
        handleShells();
    }

    private void handleWalls() {
        if (null == gameObjectsInfo.getWalls())
            return;
        int i = 0, size = gameObjectsInfo.getWalls().getWallsNumber();
        while (i < size) {
            if (false == gameObjectsInfo.getWalls().getWallInGameStatus(i)) {
                gameObjectsInfo.getWalls().removeWall(i);
                --size;
                continue;
            }
            ++i;
        }
    }

    private void handleEnemies() {
        if (null == gameObjectsInfo.getEnemies())
            return;
        int i = 0, size = gameObjectsInfo.getEnemies().size();
        while (i < size) {
            if (false == gameObjectsInfo.getEnemies().get(i).getInGameStatus()) {
                gameObjectsInfo.getEnemies().remove(i);
                --size;
                continue;
            }
            ++i;
        }
        try {
            for (Enemy enemy : gameObjectsInfo.getEnemies()) {
                EnemyFrameProduction enemyFrameProduction = enemy.enemyFrameTurn(gameObjectsInfo);
                if (null != enemyFrameProduction.getShell())
                    gameObjectsInfo.addShell(enemyFrameProduction.getShell());
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
        if (null == gameObjectsInfo.getShells())
            return;
        int i = 0, size = gameObjectsInfo.getShells().size();
        while (i < size) {
            if (false == gameObjectsInfo.getShells().get(i).getInGameStatus()) {
                gameObjectsInfo.getShells().remove(i);
                --size;
                continue;
            }
            ++i;
        }
        for (Shell shell : gameObjectsInfo.getShells()) {
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
        if (null != gameObjectsInfo.getWalls()) {
            for (int i = 0; i < gameObjectsInfo.getWallsNumber(); ++i) {
                double xMax = (gameObjectsInfo.getWallEndPoint(i).getX() > gameObjectsInfo.getWallStartPoint(i).getX())
                        ? gameObjectsInfo.getWallEndPoint(i).getX() : gameObjectsInfo.getWallStartPoint(i).getX();
                double xMin = (gameObjectsInfo.getWallEndPoint(i).getX() > gameObjectsInfo.getWallStartPoint(i).getX())
                        ? gameObjectsInfo.getWallStartPoint(i).getX() : gameObjectsInfo.getWallEndPoint(i).getX();
                double distBetweenWallAndObj = (gameObjectsInfo.getWallAngle(i) > Math.PI / 2) ?
                        Math.abs((xMax - gameObject.getX()) * Math.sin(gameObjectsInfo.getWallAngle(i)) -
                                (-gameObject.getY() + gameObjectsInfo.getWallStartPoint(i).getY()) * Math.cos(
                                        gameObjectsInfo.getWallAngle(i))) :
                        Math.abs((gameObject.getX() - xMin) * Math.sin(gameObjectsInfo.getWallAngle(i)) -
                                (gameObject.getY() - gameObjectsInfo.getWallStartPoint(i).getY()) *
                                        Math.cos(gameObjectsInfo.getWallAngle(i)));
                if (gameObject.getY() + gameObjectsInfo.getWallThickness(i) / 2 + gameObject.getRadius() *
                        Math.abs(Math.sin(gameObjectsInfo.getWallNormalAngle(i))) -
                        gameObjectsInfo.getWallStartPoint(i).getY() < 0) {
                    continue;
                }
                if (gameObjectsInfo.getWallEndPoint(i).getY() + gameObjectsInfo.getWallThickness(i) / 2 +
                        gameObject.getRadius() * Math.abs(Math.sin(gameObjectsInfo.getWallNormalAngle(i))) -
                        gameObject.getY() < 0) {
                    continue;
                }
                if (gameObject.getX() + gameObject.getRadius() * Math.cos(gameObjectsInfo.getWallNormalAngle(i))
                        + gameObjectsInfo.getWallThickness(i) / 2 - xMin < 0) {
                    continue;
                }
                if (xMax - gameObject.getX() + gameObject.getRadius() * Math.cos(gameObjectsInfo.getWallNormalAngle(i)) +
                        gameObjectsInfo.getWallThickness(i) / 2 < 0) {
                    continue;
                }
                /*double countWallThickness = ((xMax - gameObject.getX() - gameObject.getRadius() - gameObjectsInfo.getWallThickness(i)
                        * Math.abs(Math.cos(gameObjectsInfo.getWallNormalAnngle(i))) < 0 || gameObject.getX()
                        + gameObject.getRadius() - xMin - gameObjectsInfo.getWallThickness(i)
                        * Math.abs(Math.cos(gameObjectsInfo.getWallNormalAnngle(i))) < 0) && (gameObjectsInfo.getWallEndPoint(i).getY()
                        - gameObject.getY() + gameObject.getRadius()- gameObjectsInfo.getWallThickness(i)
                        * Math.abs(Math.sin(gameObjectsInfo.getWallNormalAnngle(i))) < 0 || gameObject.getY() + gameObject.getRadius()
                        - gameObjectsInfo.getWallStartPoint(i).getY() - gameObjectsInfo.getWallThickness(i)
                        * Math.abs(Math.sin(gameObjectsInfo.getWallNormalAnngle(i))) < 0)) ? -4*gameObjectsInfo.getWallThickness(i) :
                        gameObjectsInfo.getWallThickness(i);*/
                if (distBetweenWallAndObj <= gameObject.getRadius() + gameObjectsInfo.getWallThickness(i) / 2) {
                    return gameObjectsInfo.getWall(i);
                }
            }
        }
        return null;
    }

    private Unit checkCollisionsWithUnits(GameObject gameObject) {
        if (null != gameObjectsInfo.getEnemies())
            for (Enemy enemy : gameObjectsInfo.getEnemies()) {
                if (gameObject == enemy)
                    continue;
                if ((gameObject.getRadius() + enemy.getRadius()) * (gameObject.getRadius() + enemy.getRadius())
                        >= Math.pow(gameObject.getX() - enemy.getX(), 2) +
                        Math.pow(gameObject.getY() - enemy.getY(), 2)) {
                    return enemy;
                }
            }
        if ((gameObject.getRadius() + gameObjectsInfo.getHero().getRadius()) * (gameObject.getRadius()
                + gameObjectsInfo.getHero().getRadius()) >= Math.pow(gameObject.getX() -
                gameObjectsInfo.getHero().getX(), 2) + Math.pow(gameObject.getY() - gameObjectsInfo.getHero().getY(),
                2) && gameObject != gameObjectsInfo.getHero()) {
            return gameObjectsInfo.getHero();
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
}
