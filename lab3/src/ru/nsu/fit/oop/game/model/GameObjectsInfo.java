package ru.nsu.fit.oop.game.model;

import ru.nsu.fit.oop.game.model.entity.game_object.GameObjectParams;
import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Hero;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy.Enemy;

import java.util.ArrayList;
import java.util.List;

public class GameObjectsInfo {

    private List<GameObjectParams> shellsAndEnemiesParams;
    private GameObjectParams heroParams;

    public GameObjectsInfo(Hero hero) {
        heroParams = hero.getGameObjectParams();
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
}
