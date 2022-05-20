package ru.nsu.fit.oop.game.model.entity.game_object;

import ru.nsu.fit.oop.game.model.entity.Entity;
import ru.nsu.fit.oop.game.model.entity.game_object.shell.Damage;

import java.awt.geom.Point2D;

public abstract class GameObject implements Entity {

    protected final String name;
    protected double x;
    protected double y;
    protected final int radius;
    protected final int squaredRadius;
    protected final int size;
    protected double speed;
    protected double angle;
    protected boolean inGame = true;

    public GameObject(String name, int radius, double speed) {
        this.name = name;
        this.radius = radius;
        squaredRadius = radius*radius;
        size = 2*radius;
        this.speed =speed;
    }

    public double getSpeed() {
        return speed;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public int getSquaredRadius() {
        return squaredRadius;
    }

    public int getSize() {
        return size;
    }

    public double getAngle() {
        return angle;
    }

    public Point2D.Double getCoords() {
        return new Point2D.Double(x,y);
    }

    public void setCoords(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void changeCoords(double shiftX, double shiftY) {
        x += shiftX;
        y += shiftY;
    }

    public void move(double angle) {
        this.angle = angle;
        changeCoords(Math.cos(angle) * speed, Math.sin(angle) * speed);
    }

    public void takeDamage(Damage damage, double angle) {}

    public boolean getInGameStatus() {
        return inGame;
    }
}
