package ru.nsu.fit.oop.game.model.factory.wave;

import ru.nsu.fit.oop.game.exception.model.factory.FactoryException;
import ru.nsu.fit.oop.game.exception.model.factory.InvalidConfigException;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Unit;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy.Enemy;
import ru.nsu.fit.oop.game.model.factory.Factory;
import ru.nsu.fit.oop.game.model.factory.enemy.EnemyFactory;

import java.util.ArrayList;
import java.util.List;

public class WaveFactory extends Factory {

    protected static volatile WaveFactory instance;
    int currentWave = 0;

    public WaveFactory() throws InvalidConfigException {
        super("waves.properties");
    }

    public List<Enemy> getNextWave() throws FactoryException {
        if (currentWave == configs.size())
            return null;
        List<Enemy> enemies = new ArrayList<>();
        try {
            for (int i = 1; i < configs.get(currentWave).size() + 1; i++) {
                for (int j = 0; j < Integer.parseInt(configs.get(currentWave).getProperty
                        (Integer.valueOf(i).toString())); ++j)
                    enemies.add(EnemyFactory.getInstance().getRandomEnemy(i));
            }
        }
        catch (FactoryException e) {
            throw new FactoryException(this.getClass().getName(),e);
        }
        ++currentWave;
        return enemies;
    }

    public static WaveFactory getInstance() throws InvalidConfigException {
        if (null == instance)
            synchronized (WaveFactory.class) {
                if (null == instance) {
                    instance = new WaveFactory();
                }
            }
        return instance;
    }
}
