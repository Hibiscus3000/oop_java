package ru.nsu.fit.oop.game.model.entity.game_object.shell;

import ru.nsu.fit.oop.game.model.entity.weapon.Damage;
import ru.nsu.fit.oop.game.model.entity.game_object.GameObject;

public class Shell extends GameObject {

    private final Damage damage;
    private int bounces;
    private int lastBounceWallIndex = -1;

    public Shell(String name, int size, double speed, Double angle, Double x, Double y, Damage damage,
                 int bounces) {
        super(name, size, speed);
        this.bounces = bounces;
        setCoords(x,y);
        this.damage = damage;
        setAngle(angle);
    }

    public Damage getDamage() {
        return damage;
    }

    public void bounce(double normalAngle, int lastBounceWallIndex) {
        if (lastBounceWallIndex == this.lastBounceWallIndex)
            return;
        this.lastBounceWallIndex = lastBounceWallIndex;
        --bounces;
        setAngle(2 * normalAngle - getAngle() + Math.PI);
        if (-1 == bounces) {
            inGame = false;
            return;
        }
    }

    public void move() {
        changeCoords(Math.cos(getAngle()) * speed,
                Math.sin(getAngle()) * speed);
    }

    public void setInGameFalse() {
        inGame = false;
    }
}
