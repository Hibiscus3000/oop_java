package ru.nsu.fit.oop.game.model;

import ru.nsu.fit.oop.game.exception.model.ModelException;
import ru.nsu.fit.oop.game.exception.model.factory.FactoryException;
import ru.nsu.fit.oop.game.exception.model.factory.InvalidConfigException;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Hero;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Unit;
import ru.nsu.fit.oop.game.model.factory.wave.WaveFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Model {

    private Radix radix;
    private Timer rest;
    public Model(int  sizeX,int sizeY) {
        radix = new Radix(sizeX,sizeY);
        rest = new Timer(5000, null);
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
        heroes.add(new Hero(name));
    }

    public void setPause(boolean pause) {
        radix.setPause(pause);
    }
}
