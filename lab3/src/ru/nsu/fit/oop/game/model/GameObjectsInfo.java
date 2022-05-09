package ru.nsu.fit.oop.game.model;

import ru.nsu.fit.oop.game.model.entity.game_object.GameObjectParams;
import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Hero;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy.Enemy;
import ru.nsu.fit.oop.game.model.wall.GameWalls;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class GameObjectsInfo {

    private List<GameObjectParams> shellsAndEnemiesParams;
    private GameObjectParams heroParams;
    private GameWalls gameWalls;
    public GameObjectsInfo(Hero hero, GameWalls gameWalls) {
        heroParams = hero.getGameObjectParams();
        this.gameWalls = gameWalls;
    }

    public void renew(List<Enemy> enemies,  List<Shell> shells) {
        shellsAndEnemiesParams = new ArrayList<>();
        if (null != enemies)
            for (Enemy enemy : enemies) {
                this.shellsAndEnemiesParams.add(enemy.getGameObjectParams());
            }
        if (null != shells) {
            for (Shell heroShell : shells) {
                this.shellsAndEnemiesParams.add(heroShell.getGameObjectParams());
            }
        }
    }

    public List<GameObjectParams> getShellsAndEnemies() {
        return shellsAndEnemiesParams;
    }

    public GameObjectParams getHeroParams() {
        return heroParams;
    }

    public Point2D.Double getUnbreakableWallStartPoint(int index) {
        return gameWalls.getUnbreakableWallStartPoint(index);
    }

    public Point2D.Double getUnbreakableWallEndPoint(int index) {
        return gameWalls.getUnbreakableWallEndPoint(index);
    }

    public double getUnbreakableWallThickness(int index) {
        return gameWalls.getUnbreakableWallThickness(index);
    }

    public int getUnbreakableWallsNumber() {
        return gameWalls.getUnbreakableWallsNumber();
    }

    public Point2D.Double getBreakableWallStartPoint(int index) {
        return gameWalls.getBreakableWallStartPoint(index);
    }

    public Point2D.Double getBreakableWallEndPoint(int index) {
        return gameWalls.getBreakableWallEndPoint(index);
    }

    public double getBreakableWallThickness(int index) {
        return gameWalls.getBreakableWallThickness(index);
    }

    public int getBreakableWallsNumber() {
        return gameWalls.getBreakableWallsNumber();
    }

}
