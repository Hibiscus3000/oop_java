package ru.nsu.fit.oop.game.model.entity.game_object.wall;

import ru.nsu.fit.oop.game.exception.model.factory.InvalidConfigException;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GameWalls {

    private List<Wall> walls = new ArrayList<>();
    private final double defaultThicknessForUBW;

    public GameWalls(int fieldSizeX, int fieldSizeY) throws InvalidConfigException {
        defaultThicknessForUBW = 10;
        walls.add(new Wall(0,0,0,fieldSizeX,0,defaultThicknessForUBW,0));
        walls.add(new Wall(1,0,0,0,fieldSizeY,defaultThicknessForUBW,0));
        walls.add(new Wall(2,fieldSizeX,0,fieldSizeX,fieldSizeY,defaultThicknessForUBW,0));
        walls.add(new Wall(3,fieldSizeX,fieldSizeY,0,fieldSizeY,defaultThicknessForUBW,0));
        Properties config = new Properties();
        var stream = this.getClass().getResourceAsStream("wall.properties");
        if (null == stream)
            throw new InvalidConfigException("wall.properties",this.getClass().getName());
        try {
            config.load(stream);
        }
        catch (Exception e) {
            throw new InvalidConfigException("wall.properties",this.getClass().getName(),e);
        }
        for (int i = 0; i < config.size() / 6; ++i) {
            walls.add(new Wall(4 + i,
                    Integer.parseInt(config.getProperty(Integer.valueOf(10 * i).toString())),
                    Integer.parseInt(config.getProperty(Integer.valueOf(10 * i + 1).toString())),
                    Integer.parseInt(config.getProperty(Integer.valueOf(10 * i + 2).toString())),
                    Integer.parseInt(config.getProperty(Integer.valueOf(10 * i + 3).toString())),
                    Integer.parseInt(config.getProperty(Integer.valueOf(10 * i + 4).toString())),
                    Integer.parseInt(config.getProperty(Integer.valueOf(10 * i + 5).toString()))));
        }
    }

    public Point2D.Double getWallStartPoint(int index) {
        return walls.get(index).getCoords();
    }

    public Point2D.Double getWallEndPoint(int index) {
        return walls.get(index).getEndPoint();
    }

    public Point2D.Double getWallPartStartPoint(int index, int partIndex) {
        return walls.get(index).getWallPartStartPoint(partIndex);
    }

    public Point2D.Double getWallPartEndPoint(int index, int partIndex) {
        return walls.get(index).getWallPartEndPoint(partIndex);
    }

    public double getWallThickness(int index) {
        return walls.get(index).getThickness();
    }

    public int getWallsNumber() {
        return walls.size();
    }

    public double getWallAngle(int index) {
        return walls.get(index).getAngle();
    }


    public double getWallPartAngle(int index, int partIndex) {
        return walls.get(index).getWallPartAngle(partIndex);
    }

    public double getWallPartNormalAngle(int index, int partIndex) {
        return walls.get(index).getWallPartNormalAngle(partIndex);
    }

    public Wall getWall(int index) {
        return walls.get(index);
    }

    public WallPart getWallPart(int index, int partIndex) {
        return walls.get(index).getWallPart(partIndex);
    }

    public boolean getWallInGameStatus(int index) {
        return walls.get(index).getInGameStatus();
    }

    public void remove(int index) {
        walls.remove(index);
    }
}
