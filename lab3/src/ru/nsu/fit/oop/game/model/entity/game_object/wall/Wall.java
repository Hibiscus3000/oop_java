package ru.nsu.fit.oop.game.model.entity.game_object.wall;

import ru.nsu.fit.oop.game.model.entity.game_object.GameObject;

import java.awt.geom.Point2D;

public class Wall extends GameObject {

    private final int number;
    private WallPart[] wallParts = new WallPart[4];
    private int armor;
    private double endX;
    private double endY;
    private double thickness;

    public Wall(int number, double startX, double startY, double endX, double endY, double thickness, int armor) {
        super("wall", 0, 0);
        this.number = number;
        this.setCoords(startX, startY);
        this.armor = armor;
        this.endX = endX;
        this.endY = endY;
        this.thickness = thickness;
        double x = endX - getX();
        double y = endY - getY();
        if (0 == x) {
            angle = Math.PI / 2;
        }
        else if (x > 0) {
            angle = Math.atan(y / x);
        } else if (y > 0) {
            angle = Math.PI + Math.atan(y / x);
        } else
            angle = 0;
        wallParts[0] = new WallPart(number,startX + thickness / 2 * Math.cos(Math.PI / 2 - angle),
                startY - thickness / 2 * Math.sin(Math.PI / 2 - angle),
                endX + thickness / 2 * Math.cos(Math.PI / 2 - angle),
                endY - thickness / 2 * Math.sin(Math.PI / 2 - angle));
        wallParts[1] = new WallPart(number,startX - thickness / 2 * Math.cos(Math.PI / 2 - angle),
                startY + thickness / 2 * Math.sin(Math.PI / 2 - angle),
                endX - thickness / 2 * Math.cos(Math.PI / 2 - angle),
                endY + thickness / 2 * Math.sin(Math.PI / 2 - angle));
        wallParts[2] = new WallPart(number,startX + thickness / 2 * Math.cos(Math.PI / 2 - angle),
                startY - thickness / 2 * Math.sin(Math.PI / 2 - angle),
                startX - thickness / 2 * Math.cos(Math.PI / 2 - angle),
                startY + thickness / 2 * Math.sin(Math.PI / 2 - angle));
        wallParts[3] = new WallPart(number,endX + thickness / 2 * Math.cos(Math.PI / 2 - angle),
                endY - thickness / 2 * Math.sin(Math.PI / 2 - angle),
                endX - thickness / 2 * Math.cos(Math.PI / 2 - angle),
                endY + thickness / 2 * Math.sin(Math.PI / 2 - angle));
    }

    public void countAbsorbedDamage() {
        if (0 != armor) {
            armor -= wallParts[0].getAbsorbedDamage() + wallParts[1].getAbsorbedDamage() +
                    wallParts[2].getAbsorbedDamage() + wallParts[3].getAbsorbedDamage();
            if (0 >= armor)
                inGame = false;
        }
        wallParts[0].setAbsorbedDamageZero();
        wallParts[1].setAbsorbedDamageZero();
        wallParts[2].setAbsorbedDamageZero();
        wallParts[3].setAbsorbedDamageZero();
    }

    public double getThickness() {
        return thickness;
    }

    public Point2D.Double getEndPoint() {
        return new Point2D.Double(endX,endY);
    }

    public Point2D.Double getWallPartStartPoint(int i) {
        return wallParts[i].getStartPoint();
    }

    public Point2D.Double getWallPartEndPoint(int i) {
        return wallParts[i].getEndPoint();
    }

    public double getWallPartAngle(int i) {
        return wallParts[i].getAngle();
    }

    public double getWallPartNormalAngle(int i) {
        return wallParts[i].getNormalAngle();
    }

    public int getWallNumber() {
        return number;
    }

    public WallPart getWallPart(int i) {
        return wallParts[i];
    }
}
