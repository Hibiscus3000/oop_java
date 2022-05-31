package ru.nsu.fit.oop.game.model;

import ru.nsu.fit.oop.game.exception.model.ModelException;
import ru.nsu.fit.oop.game.exception.model.UnableToUseWeaponException;
import ru.nsu.fit.oop.game.model.entity.game_object.GameObject;
import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Unit;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy.Enemy;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy.EnemyFrameProduction;
import ru.nsu.fit.oop.game.model.entity.game_object.wall.WallPart;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Random;

import static ru.nsu.fit.oop.game.model.Geometry.LinePartAndCircleIntersect;

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
            List<Shell> shells = gameObjectsInfo.getHero().useWeapon(angle);
            if (null != shells)
                gameObjectsInfo.addShells(shells);
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
            gameObjectsInfo.getWall(i).countAbsorbedDamage();
            if (false == gameObjectsInfo.getWalls().getWallInGameStatus(i)) {
                gameObjectsInfo.removeWall(i);
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
                gameObjectsInfo.removeEnemy(i);
                --size;
                continue;
            }
            ++i;
        }
        try {
            for (Enemy enemy : gameObjectsInfo.getEnemies()) {
                EnemyFrameProduction enemyFrameProduction = enemy.enemyFrameTurn(gameObjectsInfo);
                if (null != enemyFrameProduction) {
                    if (null != enemyFrameProduction.getShells())
                        gameObjectsInfo.addShells(enemyFrameProduction.getShells());
                }
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
                gameObjectsInfo.removeShell(i);
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

    private WallPart checkCollisionsWithWalls(GameObject gameObject) {
        if (null != gameObjectsInfo.getWalls()) {
            for (int i = 0; i < gameObjectsInfo.getWallsNumber(); ++i) {
                for (int j = 0; j < 4; ++j) {
                    if (LinePartAndCircleIntersect(gameObjectsInfo.getWallPartStartPoint(i,j),
                            gameObjectsInfo.getWallPartEndPoint(i,j),gameObjectsInfo.getWallPartAngle(i,j),
                            gameObject.getCoords(),gameObject.getRadius())) {
                        return gameObjectsInfo.getWallPart(i,j);
                    }
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
        try {
            shell.bounce(((WallPart) gameObject).getNormalAngle(),((WallPart) gameObject).getParentWallNumber());
        } catch (ClassCastException e) {
            shell.setInGameFalse();
        }
        finally {
            gameObject.takeDamage(shell.getDamage(), shell.getAngle());
        }
    }
}
