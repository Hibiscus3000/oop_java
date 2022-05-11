package ru.nsu.fit.oop.game.model;

import ru.nsu.fit.oop.game.exception.model.ModelException;
import ru.nsu.fit.oop.game.exception.model.factory.FactoryException;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Hero;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy.Enemy;
import ru.nsu.fit.oop.game.model.factory.wave.WaveFactory;
import ru.nsu.fit.oop.game.model.entity.game_object.wall.GameWalls;
import ru.nsu.fit.oop.game.view.View;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Observable;

public class Model extends Observable {

    private final int fieldSizeX;
    private final int fieldSizeY;
    private Radix radix;
    private Timer fieldUpdateTimer;
    private Timer rest;
    private GameObjectsInfo gameObjectsInfo;

    public Model(View view, int fieldSizeX, int fieldSizeY) {
        addObserver(view);
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        rest = new Timer(3500, null);
        fieldUpdateTimer = new Timer(20, null);
        fieldUpdateTimer.addActionListener(e -> {
            radix.updateGameField();
            if ((false == rest.isRunning()) && (0 == gameObjectsInfo.getNumberOfEnemies()))
                rest.restart();
            if (false == radix.getHeroInGameStatus())
                System.exit(1);
            setChanged();
            notifyObservers();
            clearChanged();
        });
    }

    public void initGame(String name) {
        rest.addActionListener(e -> {
            try {
                rest.stop();
                List<Enemy> enemies = WaveFactory.getInstance().getNextWave();
                if (null != enemies)
                    radix.setEnemies(enemies);
                else
                    System.exit(0);
            } catch (FactoryException ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        });
        rest.start();
        try {
            Hero hero = new Hero(name);
            GameWalls gameWalls = new GameWalls(fieldSizeX, fieldSizeY);
            gameObjectsInfo = new GameObjectsInfo(hero,gameWalls);
            radix = new Radix(fieldSizeX, fieldSizeY,gameObjectsInfo);
        } catch (ModelException e) {
            e.printStackTrace();
            System.exit(1);
        }
        fieldUpdateTimer.start();
    }

    public Point2D.Double getHeroCoords() {
        return radix.getHeroCoords();
    }

    public void moveHero(double angle) {
        radix.moveHero(angle);
    }

    public void heroUseWeapon(double angle) {
        try {
            radix.heroUseWeapon(angle);
        } catch (ModelException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void setPause(boolean pause) {
        if (false == pause)
            fieldUpdateTimer.stop();
        else
            fieldUpdateTimer.restart();
    }

    public GameObjectsInfo getGameObjectsInfo() {
        return gameObjectsInfo;
    }
}
