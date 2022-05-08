package ru.nsu.fit.oop.game.model;

import ru.nsu.fit.oop.game.model.entity.game_object.GameObject;
import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Unit;

import java.util.ArrayList;
import java.util.List;

public class GameObjectsInfo {

    private List<GameObject> enemies = new ArrayList<>();
    private List<GameObject> heroes = new ArrayList<>();
    private List<GameObject> enemyShells = new ArrayList<>();
    private List<GameObject> heroShells = new ArrayList<>();

    public GameObjectsInfo(List<Unit> enemies, List<Unit> heroes, List<Shell> enemyShells,
                           List<Shell> heroShells) {
        if (null != enemies)
            for (Unit enemy : enemies) {
                this.enemies.add((GameObject) enemy);
            }
        if (null != heroes)
            for (Unit hero : heroes) {
                this.heroes.add((GameObject) hero);
            }
        if (null != heroShells)
            for (Shell heroShell : heroShells) {
                this.heroShells.add((GameObject) heroShell);
            }
        if (null != enemyShells)
            for (Shell enemyShell : enemyShells) {
                this.enemyShells.add((GameObject) enemyShells);
            }
    }


    public List<GameObject> getHeroShells() {
        return heroShells;
    }

    public List<GameObject> getEnemyShells() {
        return enemyShells;
    }

    public List<GameObject> getHeroes() {
        return heroes;
    }

    public List<GameObject> getEnemies() {
        return enemies;
    }
}
