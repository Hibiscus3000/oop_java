package ru.nsu.fit.oop.game.model.factory.enemy;

import ru.nsu.fit.oop.game.exception.model.factory.InvalidConfigException;
import ru.nsu.fit.oop.game.exception.model.factory.InvalidRandomEntity;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy.Enemy;
import ru.nsu.fit.oop.game.model.factory.Factory;

import java.util.Random;

public class EnemyFactory extends Factory {

    protected static volatile EnemyFactory instance;

    public EnemyFactory() throws InvalidConfigException {
        super("enemies.properties");
    }

    public static EnemyFactory getInstance() throws InvalidConfigException {
        if (null == instance)
            synchronized (EnemyFactory.class) {
                if (null == instance) {
                    instance = new EnemyFactory();
                }
            }
        return instance;
    }
}
