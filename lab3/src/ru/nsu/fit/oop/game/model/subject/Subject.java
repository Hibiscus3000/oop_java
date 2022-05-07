package ru.nsu.fit.oop.game.model.subject;

import java.awt.*;

public class Subject {
    protected String name;
    protected double x;
    protected double y;
    protected final int squaredSize;
    protected double speed;
    protected boolean inGame = true;
    public Subject(String name, double x, double y, int size, double speed) {
        this.name = name;
        setCoords(x,y);
        this.squaredSize = size;
        this.speed = speed;
    }

    public double getX() {return x;}

    public double getY() {return y;}

    public int getSquaredSize() {return squaredSize;}

    public Point getCoords() {
        return new Point((int)x,(int)y);
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
        this.x -= shiftX;
        this.y -= shiftY;
    }

    public void move(double angle) {
        changeCoords(Math.cos(angle) * speed, Math.sin(angle) * speed);
    }

    public boolean getInGameStatus() {
        return inGame;
    }
}
