package ru.nsu.fit.oop.game.model.entity.game_object.shell;

import ru.nsu.fit.oop.game.model.entity.game_object.GameObject;

public class Shell extends GameObject {

    int damage;
    double angle;

    public Shell(String name, int size, double speed, int damage) {
        super(name,size, speed);
        this.damage = damage;
    }

    public void move() {
        changeCoords(Math.cos(angle) * speed,-Math.sin(angle) * speed);
    }

    public int getDamage() {
        return damage;
    }

    public void setInGameFalse() {
        inGame = false;
    }
}
