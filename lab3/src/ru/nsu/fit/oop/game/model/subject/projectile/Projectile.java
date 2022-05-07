package ru.nsu.fit.oop.game.model.subject.projectile;

import ru.nsu.fit.oop.game.model.subject.Subject;

import java.awt.*;

public class Projectile extends Subject {

    double angle;

    public Projectile(String name, double x, double y, int size, int speed) {
        super(name, x, y,size, speed);
    }

    public double getAngle() {
        return angle;
    }

    public void move() {
        changeCoords(Math.cos(angle) * speed,Math.sin(angle) * speed);
    }

    public void setInGameFalse() {
        inGame = false;
    }
}
