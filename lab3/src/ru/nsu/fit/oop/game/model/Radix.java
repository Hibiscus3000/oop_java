package ru.nsu.fit.oop.game.model;

import ru.nsu.fit.oop.game.exception.model.ModelException;
import ru.nsu.fit.oop.game.exception.model.UnableToUseWeaponException;
import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Hero;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy.Enemy;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Radix {

    private int sizeX;
    private int sizeY;
    private Hero hero;
    private List<Enemy> enemies;
    private volatile List<Shell> shells = new ArrayList<>();

    public Radix(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public int getNumberOfEnemies() {
        return enemies.size();
    }

    public Dimension getFieldSize() {
        return new Dimension(sizeX, sizeY);
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
        for (Enemy enemy : enemies)
            setEnemyCoords(enemy);
    }

    public void setHero(Hero hero) {
        this.hero = hero;
        hero.setCoords(sizeX / 2 + new Random().nextDouble(100) - 50, sizeY / 2
                + new Random().nextDouble(100) - 50);
    }

    private void setEnemyCoords(Enemy enemy) {
        enemy.setCoords(new Random().nextDouble(sizeX + 2 * enemy.getSize()),
                new Random().nextDouble(sizeY - enemy.getRadius()));
    }

    public void moveHero(double angle) {
        hero.move(angle);
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

    public void addShell(Shell shell) {
        shells.add(shell);
    }

    public void updateGameField() {
        // handle units!!!
        handleShells();
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
            checkCollisions(shell);
        }
    }

    private void checkCollisions(Shell shell) {
        if (shell.getX() <= 0 || shell.getX() >= sizeX || shell.getY() <= 0
                || shell.getY() >= sizeY) {
            shell.setInGameFalse();
            return;
        }
        if (null != enemies)
            for (Enemy enemy : enemies) {
                if (shell.getSquaredRadius() + enemy.getSquaredRadius() > (shell.getX() - enemy.getX()) *
                        (shell.getX() - enemy.getX()) + (shell.getY() - enemy.getY()) *
                        (shell.getY() - enemy.getY())) {
                    enemy.changeHealth(shell.getDamage());
                    shell.setInGameFalse();
                    return;
                }
            }
    }

    public GameObjectsInfo getGameObjectsInfo() {
        return new GameObjectsInfo(enemies, hero, shells);
    }
}
