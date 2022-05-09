package ru.nsu.fit.oop.game.model.wall;

import java.awt.*;
import java.awt.geom.Point2D;

public class Wall {

    private final double startX;
    private final double startY;
    private final double endX;
    private final double endY;
    private final double thickness;

    public Wall(double startX, double startY, double endX, double endY, double thickness) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.thickness = thickness;
    }

    public Point2D.Double getStartPoint() {
        return new Point2D.Double(startX, startY);
    }

    public Point2D.Double getEndPoint() {
        return new Point2D.Double(endX, endY);
    }

    public double getWallThickness() {
        return thickness;
    }
}
