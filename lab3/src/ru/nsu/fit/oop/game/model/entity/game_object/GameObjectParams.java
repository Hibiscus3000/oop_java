package ru.nsu.fit.oop.game.model.entity.game_object;

import java.awt.*;
import java.awt.geom.Point2D;

public class GameObjectParams {
    private final String name;
    private double x;
    private double y;
    private final int radius;
    private final int squaredRadius;
    private final int size;
    private double speed;

    GameObjectParams(String name, int radius, double speed) {
        this.name = name;
        this.radius = radius;
        squaredRadius = radius*radius;
        size = 2*radius;
        this.speed =speed;
    }

    public int getSize() {
        return size;
    }

    public int getSquaredRadius() {
        return squaredRadius;
    }

    public int getRadius() {
        return radius;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public Point2D.Double getCoords() {
        return new Point2D.Double(x,y);
    }

    public String getName() {
        return name;
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

    public double getSpeed() {
        return speed;
    }

    public void move(double angle) {
        changeCoords(Math.cos(angle) * speed, Math.sin(angle) * speed);
    }
}
