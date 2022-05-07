package ru.nsu.fit.oop.game.model.subject.unit.enemy;

import ru.nsu.fit.oop.game.model.subject.unit.Unit;

public class Enemy extends Unit {

    protected Enemy(String name, double x, double y, int size, double speed, int lives, int health, int armor, int shield) {
        super(name, x, y,size, speed, lives, health, armor, shield);
    }
}
