package ru.nsu.fit.oop.game.model.entity.game_object.shell;

import ru.nsu.fit.oop.game.model.entity.Damage;
import ru.nsu.fit.oop.game.model.entity.game_object.GameObject;

public class Shell extends GameObject {

    private final Damage damage;
    private int bounces;

    public Shell(String name, int size, double speed, Double angle, Double x, Double y, Damage damage,
                 int bounces) {
        super(name, size, speed);
        this.bounces = bounces;
        setCoords(x,y);
        this.damage = damage;
        this.angle = angle;
    }

    public Damage getDamage() {
        return damage;
    }

    public void bounce(double normalAngle) {
        --bounces;
        angle = 2 * normalAngle - angle + Math.PI;
        if (-1 == bounces) {
            inGame = false;
            return;
        }
    }

    public void move() {
        changeCoords(Math.cos(angle) * gameObjectParams.getSpeed(),
                Math.sin(angle) * gameObjectParams.getSpeed());
    }

    public void setInGameFalse() {
        inGame = false;
    }
}
