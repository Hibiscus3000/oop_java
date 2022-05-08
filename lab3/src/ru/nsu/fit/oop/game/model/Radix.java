package ru.nsu.fit.oop.game.model;

import ru.nsu.fit.oop.game.exception.model.factory.InvalidConfigException;
import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Hero;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Unit;
import ru.nsu.fit.oop.game.model.factory.weapon.WeaponFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Radix {

    private int sizeX;
    private int sizeY;
    private List<Unit> heroes;
    private List<Unit> enemies;
    private List<Shell> heroShells = new ArrayList<>();
    private List<Shell> enemyShells = new ArrayList<>();

    public Radix(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public int getNumberOfEnemies() {
        return enemies.size();
    }

    public void setEnemies(List<Unit> enemies) {
        this.enemies = enemies;
        for (Unit enemy : enemies)
            setEnemyCoords(enemy);
    }

    public void setHeroes(List<Unit> heroes) {
        this.heroes = heroes;
        for (Unit hero : heroes) {
            hero.setCoords(sizeX / 2 + new Random().nextDouble(100) - 50, sizeY / 2
                    + new Random().nextDouble(100) - 50);
        }
    }

    private void setEnemyCoords(Unit enemy) {
        enemy.setCoords(new Random().nextDouble(sizeX + 2 * enemy.getSize()),
                new Random().nextDouble(sizeY - enemy.getRadius()));
    }

    public void moveHero(double angle) {
        heroes.get(0).move(angle);
    }

    public void addEnemyShell(Shell shell) {
        enemyShells.add(shell);
    }

    public void addHeroShell(Shell shell) {
        heroShells.add(shell);
    }

    public void updateGameField() {
        // handle units!!!
        handleShells(enemyShells, heroes);
        handleShells(heroShells, enemies);
    }

    private void handleShells(List<Shell> shells, List<Unit> targets) {
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
            checkCollisions(targets, shell);
        }
    }

    private void checkCollisions(List<Unit> targets, Shell shell) {
        if (shell.getX() <= 0 || shell.getX() >= sizeX || shell.getY() <= 0
                || shell.getY() >= sizeY) {
            shell.setInGameFalse();
            return;
        }
        for (Unit target : targets) {
            if (shell.getSquaredRadius() + target.getSquaredRadius() > (shell.getX() - target.getX()) *
                    (shell.getX() - target.getX()) + (shell.getY() - target.getY()) *
                    (shell.getY() - target.getY())) {
                target.changeHealth(shell.getDamage());
                shell.setInGameFalse();
                return;
            }
        }
    }

    public GameObjectsInfo getGameObjectsInfo() {
        return new GameObjectsInfo(enemies,heroes,enemyShells,heroShells);
    }
}
