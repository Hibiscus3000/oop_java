package ru.nsu.fit.oop.game.model.entity.game_object;

import ru.nsu.fit.oop.game.model.entity.Entity;
import ru.nsu.fit.oop.game.model.entity.weapon.Damage;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class GameObject implements Entity {

    protected String name;
    protected final GameObjectParams gameObjectParams;
    protected boolean inGame = true;

    public GameObject(String name, int radius, double speed) {
        this.name = name;
        gameObjectParams = new GameObjectParams(name,radius,speed);
    }

    public double getSpeed() {
        return gameObjectParams.getSpeed();
    }

    public double getX() {
        return gameObjectParams.getX();
    }

    public double getY() {
        return gameObjectParams.getY();
    }

    public int getRadius() {
        return gameObjectParams.getRadius();
    }

    public int getSquaredRadius() {
        return gameObjectParams.getSquaredRadius();
    }

    public int getSize() {
        return gameObjectParams.getSize();
    }

    public double getAngle() {
        return gameObjectParams.getAngle();
    }

    public Point2D.Double getCoords() {
        return gameObjectParams.getCoords();
    }

    public GameObjectParams getGameObjectParams() {
        return gameObjectParams;
    }

    public void setCoords(double x, double y) {
        gameObjectParams.setCoords(x,y);
    }

    public void setCoords(Point coords) {
        gameObjectParams.setCoords(coords);
    }

    public void setAngle(double angle) {
        gameObjectParams.setAngle(angle);
    }

    public void changeCoords(double shiftX, double shiftY) {
        gameObjectParams.changeCoords(shiftX,shiftY);
    }

    public void move(double angle) {
        gameObjectParams.setAngle(angle);
        changeCoords(Math.cos(angle) * gameObjectParams.getSpeed(),
                Math.sin(angle) * gameObjectParams.getSpeed());
    }

    public void takeDamage(Damage damage, double angle) {}

    public boolean getInGameStatus() {
        return inGame;
    }
}
