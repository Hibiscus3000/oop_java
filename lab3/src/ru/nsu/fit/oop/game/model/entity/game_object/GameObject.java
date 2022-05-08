package ru.nsu.fit.oop.game.model.entity.game_object;

import ru.nsu.fit.oop.game.model.entity.Entity;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class GameObject extends Entity {
    protected String name;
    protected double x;
    protected double y;
    protected final int radius;
    protected final int squaredRadius;
    protected final int size;
    protected double speed;
    protected boolean inGame = true;
    public GameObject(String name, int radius, double speed) {
        this.name = name;
        this.size = 2 * radius;
        this.radius = radius;
        this.squaredRadius = radius*radius;
        this.speed = speed;
    }

    public double getX() {return x;}

    public double getY() {return y;}

    public int getRadius() {
        return radius;
    }

    public int getSquaredRadius() {
        return squaredRadius;
    }

    public int getSize() {
        return size;
    }

    public Point2D.Double getCoords() {
        return new Point2D.Double(x,y);
    }

    public void setCoords(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setCoords(Point coords) {
        this.x = coords.x;
        this.y = coords.y;
    }

    public void changeCoords(double shiftX, double shiftY) {
        this.x += shiftX;
        this.y += shiftY;
    }

    public void move(double angle) {
        changeCoords(Math.cos(angle) * speed, Math.sin(angle) * speed);
    }

    public boolean getInGameStatus() {
        return inGame;
    }
}
