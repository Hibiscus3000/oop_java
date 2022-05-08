package ru.nsu.fit.oop.game.model.entity.game_object.shell;

import ru.nsu.fit.oop.game.model.entity.game_object.GameObject;

public class Shell extends GameObject {

    int damage;
    double angle;

    public Shell(String name, int size, double speed, Double angle, Double x, Double y, int damage) {
        super(name, size, speed);
        setCoords(x,y);
        this.damage = damage;
        this.angle = angle;
    }

    public void move() {
        changeCoords(Math.cos(angle) * speed, Math.sin(angle) * speed);
    }

    public int getDamage() {
        return damage;
    }

    public void setInGameFalse() {
        inGame = false;
    }
}
