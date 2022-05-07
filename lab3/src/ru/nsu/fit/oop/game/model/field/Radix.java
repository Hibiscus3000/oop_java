package ru.nsu.fit.oop.game.model.field;

import ru.nsu.fit.oop.game.model.subject.projectile.Projectile;
import ru.nsu.fit.oop.game.model.subject.unit.Hero;
import ru.nsu.fit.oop.game.model.subject.unit.enemy.Enemy;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Radix {

    private int sizeX;
    private int sizeY;
    private Hero hero;
    private List<Enemy> enemies = new ArrayList();
    private List<Projectile> heroProjectiles = new ArrayList<>();
    private List<Projectile> enemyProjectiles = new ArrayList<>();
    private Timer fieldUpdateTimer;

    public Radix(int sizeX, int sizeY, Hero hero) {
        this.hero = hero;
        fieldUpdateTimer = new Timer(33,new FieldUpdateListener());
    }

    private class FieldUpdateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 0;
            for (Projectile projectile : heroProjectiles) {
                if (false == projectile.getInGameStatus())
                    heroProjectiles.remove(i);
                projectile.move();

                ++i;
            }
        }

        private void checkCollisions(Projectile projectile) {
            for (Enemy enemy : enemies) {
                if ((enemy.getX() - projectile.getX())*(enemy.getX() - projectile.getX()) +
                        (enemy.getY() - projectile.getY())*(enemy.getY() - projectile.getY()) <
                        projectile.getSquaredSize() + enemy.getSquaredSize())

            }
        }
    }
}
