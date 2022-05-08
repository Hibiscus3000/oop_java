package ru.nsu.fit.oop.game.model;

import ru.nsu.fit.oop.game.exception.model.UnitGenerationException;
import ru.nsu.fit.oop.game.exception.model.factory.FactoryException;
import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Hero;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Unit;
import ru.nsu.fit.oop.game.model.factory.wave.WaveFactory;
import ru.nsu.fit.oop.game.view.View;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Model extends Observable {

    private Radix radix;
    private Timer fieldUpdateTimer;
    private Timer rest;
    public Model(View view, int  sizeX, int sizeY) {
        addObserver(view);
        rest = new Timer(5000, null);
        fieldUpdateTimer = new Timer(33, null);
        fieldUpdateTimer.addActionListener(e -> {
            radix.updateGameField();
            setChanged();
            notifyObservers();
            clearChanged();
        });
    }

    public void initGame(String name) {
        rest.addActionListener(e -> {
            try {
                radix.setEnemies(WaveFactory.getInstance().getNextWave());
            }
            catch (FactoryException ex) {
                ex.printStackTrace();
                return;
            }
        });
        List<Unit> heroes = new ArrayList<>();
        try {
            heroes.add(new Hero(name));
        } catch (UnitGenerationException e) {
            e.printStackTrace();
            return;
        }
        radix.setHeroes(heroes);
        fieldUpdateTimer.start();
    }

    public void moveHero(double angle) {
        radix.moveHero(angle);
    }

    public void setPause(boolean pause) {
        if (false == pause)
            fieldUpdateTimer.stop();
        else
            fieldUpdateTimer.restart();
    }

    public GameObjectsInfo getGameObjectsInfo() {
        return radix.getGameObjectsInfo();
    }
}
