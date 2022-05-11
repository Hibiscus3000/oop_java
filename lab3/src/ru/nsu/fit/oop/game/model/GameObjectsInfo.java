package ru.nsu.fit.oop.game.model;

import ru.nsu.fit.oop.game.model.entity.game_object.GameObjectParams;
import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Hero;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy.Enemy;
import ru.nsu.fit.oop.game.model.entity.game_object.wall.GameWalls;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class GameObjectsInfo {

    private List<GameObjectParams> shellsParams;
    private List<GameObjectParams> enemiesParams;
    private GameObjectParams heroParams;
    private GameWalls gameWalls;
    public GameObjectsInfo(Hero hero, GameWalls gameWalls) {
        heroParams = hero.getGameObjectParams();
        this.gameWalls = gameWalls;
    }

    public void renew(List<Enemy> enemies,  List<Shell> shells) {
        shellsParams = new ArrayList<>();
        enemiesParams = new ArrayList<>();
        if (null != enemies)
            for (Enemy enemy : enemies) {
                this.enemiesParams.add(enemy.getGameObjectParams());
            }
        if (null != shells) {
            for (Shell shell : shells) {
                this.shellsParams.add(shell.getGameObjectParams());
            }
        }
    }

    public List<GameObjectParams> getShellsParams() {
        return shellsParams;
    }

    public List<GameObjectParams> getEnemiesParams() {
        return enemiesParams;
    }

    public GameObjectParams getHeroParams() {
        return heroParams;
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

    public int getWallsNumber() {
        return gameWalls.getWallsNumber();
    }

}
