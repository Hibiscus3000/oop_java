package ru.nsu.fit.oop.game.model;

import ru.nsu.fit.oop.game.model.entity.game_object.GameObject;
import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Hero;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy.Enemy;

import java.util.ArrayList;
import java.util.List;

public class GameObjectsInfo {

    private List<GameObject> shellsAndEnemies = new ArrayList<>();
    private GameObject hero;
    public GameObjectsInfo(List<Enemy> enemies, Hero hero, List<Shell> shells) {
        if (null != enemies)
            for (Enemy enemy : enemies) {
                this.shellsAndEnemies.add((GameObject) enemy);
            }
        if (null != hero)
            this.hero = hero;
        if (null != shells) {
            for (Shell heroShell : shells) {
                this.shellsAndEnemies.add((GameObject) heroShell);
            }
        }
    }

    public List<GameObject> getShellsAndEnemies() {
        return shellsAndEnemies;
    }

    public GameObject getHero() {
        return hero;
    }
}
