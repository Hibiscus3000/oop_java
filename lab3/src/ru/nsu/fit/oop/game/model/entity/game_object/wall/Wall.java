package ru.nsu.fit.oop.game.model.entity.game_object.wall;

import ru.nsu.fit.oop.game.model.entity.game_object.GameObject;

import java.awt.geom.Point2D;

public class Wall extends GameObject {

    private final double endX;
    private final double endY;
    private final double thickness;
    private final double normalAngle;

    public Wall(double startX, double startY, double endX, double endY, double thickness) {
        super("wall", 0, 0);
        this.setCoords(startX, startY);
        this.endX = endX;
        this.endY = endY;
        this.thickness = thickness;
        double x = endX - getX();
        double y = endY - getY();
        if (0 == x) {
            setAngle(Math.PI / 2);
        }
        else if (x > 0) {
            setAngle(Math.atan(y / x));
        } else if (y > 0) {
            setAngle(Math.PI + Math.atan(y / x));
        } else
            setAngle(0);
        normalAngle = getAngle() - Math.PI / 2;
    }

    public Point2D.Double getStartPoint() {
        return new Point2D.Double(getX(), getY());
    }

    public Point2D.Double getEndPoint() {
        return new Point2D.Double(endX, endY);
    }

    public double getWallThickness() {
        return thickness;
    }

    public double getNormalAngle() {
        return normalAngle;
    }
}
