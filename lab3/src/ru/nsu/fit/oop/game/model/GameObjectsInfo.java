package ru.nsu.fit.oop.game.model;

import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Hero;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy.Enemy;
import ru.nsu.fit.oop.game.model.entity.game_object.wall.GameWalls;
import ru.nsu.fit.oop.game.model.entity.game_object.wall.Wall;
import ru.nsu.fit.oop.game.model.entity.game_object.wall.WallPart;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class GameObjectsInfo {

    private volatile List<Shell> shells = new ArrayList<>();
    private List<Enemy> enemies;
    private volatile Hero hero;
    private GameWalls gameWalls;
    public GameObjectsInfo(Hero hero, GameWalls gameWalls) {
        this.hero = hero;
        this.gameWalls = gameWalls;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void addShell(Shell shell) {
        shells.add(shell);
    }

    public void removeShell(int i) {
        shells.remove(i);
    }

    public void removeEnemy(int i) {
        enemies.remove(i);
    }

    public void removeWall(int i) {
        gameWalls.remove(i);
    }

    public List<Shell> getShells() {
        return shells;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public GameWalls getWalls() {
        return gameWalls;
    }

    public int getNumberOfEnemies() {
        return enemies.size();
    }

    public Hero getHero() {
        return hero;
    }

    public Point2D.Double getWallStartPoint(int index) {
        return gameWalls.getWallStartPoint(index);
    }

    public Point2D.Double getWallEndPoint(int index) {
        return gameWalls.getWallEndPoint(index);
    }

    public double getWallThickness(int index) {
        return gameWalls.getWallThickness(index);
    }

    public double getWallAngle(int index) {
        return gameWalls.getWallAngle(index);
    }

    public int getWallsNumber() {
        return gameWalls.getWallsNumber();
    }

    public Point2D.Double getWallPartStartPoint(int index, int partIndex) {
        return gameWalls.getWallPartStartPoint(index,partIndex);
    }

    public Point2D.Double getWallPartEndPoint(int index, int partIndex) {
        return gameWalls.getWallPartEndPoint(index,partIndex);
    }

    public Wall getWall(int index) {
        return gameWalls.getWall(index);
    }

    public WallPart getWallPart(int index, int partIndex) {
        return gameWalls.getWallPart(index,partIndex);
    }

    public double getWallPartNormalAngle(int index, int partIndex) {
        return gameWalls.getWallPartNormalAngle(index,partIndex);
    }

    public double getWallPartAngle(int index, int partIndex) {
        return gameWalls.getWallPartAngle(index,partIndex);
    }
}
