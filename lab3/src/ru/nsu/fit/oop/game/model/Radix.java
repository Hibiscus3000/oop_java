package ru.nsu.fit.oop.game.model;

import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Unit;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Radix {

    private int sizeX;
    private int sizeY;
    private List<Unit> heroes = new ArrayList<>();
    private List<Unit> enemies = new ArrayList();
    private List<Shell> heroShells = new ArrayList<>();
    private List<Shell> enemyShells = new ArrayList<>();
    private Timer fieldUpdateTimer;

    public Radix(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        fieldUpdateTimer = new Timer(33, new FieldUpdateListener());
    }

    public void setPause(boolean pause) {
        if (false == pause)
            fieldUpdateTimer.stop();
        else
            fieldUpdateTimer.restart();
    }

    public int getNumberOfEnemies() {
        return enemies.size();
    }

    public void setEnemies(List<Unit> enemies) {
        this.enemies = enemies;
    }

    public void setHeroes(List<Unit> heroes) {
        this.heroes = heroes;
    }

    private class FieldUpdateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // handle units!!!
            handleShells(enemyShells,heroes);
            handleShells(heroShells,enemies);
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
                checkCollisions(targets,shell);
            }
        }

        private void checkCollisions(List<Unit> targets, Shell shell) {
            if (shell.getX() <= 0  || shell.getX() >= sizeX || shell.getY() <= 0
                    || shell.getY() >= sizeY) {
                shell.setInGameFalse();
                return;
            }
            for (Unit target: targets) {
                if (shell.getSquaredSize() + target.getSquaredSize() > (shell.getX() - target.getX()) *
                        (shell.getX() - target.getX()) + (shell.getY() - target.getY()) *
                        (shell.getY() - target.getY())) {
                    target.changeHealth(shell.getDamage());
                    shell.setInGameFalse();
                    return;
                }
            }
        }
    }
}
