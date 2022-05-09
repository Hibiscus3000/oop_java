package ru.nsu.fit.oop.game.model.wall;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class GameWalls {

    private List<Wall> unbreakableWalls = new ArrayList<>();
    private List<BreakableWall> breakableWalls = new ArrayList<>();
    private final double defaultThickness;

    public GameWalls(int fieldSizeX, int fieldSizeY) {
        defaultThickness = 3;
        unbreakableWalls.add(new Wall(0,0,fieldSizeX,0,defaultThickness));
        unbreakableWalls.add(new Wall(0,0,0,fieldSizeY,defaultThickness));
        unbreakableWalls.add(new Wall(fieldSizeX,fieldSizeY,fieldSizeX,0,defaultThickness));
        unbreakableWalls.add(new Wall(fieldSizeX,fieldSizeY,0,fieldSizeY,defaultThickness));
    }

    public Point2D.Double getUnbreakableWallStartPoint(int index) {
        return unbreakableWalls.get(index).getStartPoint();
    }

    public Point2D.Double getUnbreakableWallEndPoint(int index) {
        return unbreakableWalls.get(index).getEndPoint();
    }

    public double getUnbreakableWallThickness(int index) {
        return unbreakableWalls.get(index).getWallThickness();
    }

    public int getUnbreakableWallsNumber() {
        return unbreakableWalls.size();
    }

    public Point2D.Double getBreakableWallStartPoint(int index) {
        return breakableWalls.get(index).getStartPoint();
    }

    public Point2D.Double getBreakableWallEndPoint(int index) {
        return breakableWalls.get(index).getStartPoint();
    }

    public double getBreakableWallThickness(int index) {
        return breakableWalls.get(index).getWallThickness();
    }

    public int getBreakableWallsNumber() {
        return breakableWalls.size();
    }

    public void removeBreakableWall(int index) {
        breakableWalls.remove(index);
    }
}
