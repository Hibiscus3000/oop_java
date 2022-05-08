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

    public Enemy getRandomEnemy(int level) throws InvalidRandomEntity {
        Class randClass = null;
        String Name;
        int randomNumber = -1, propertySize = -1;
        try {
            propertySize = configs.get(level - 1).size();
            randomNumber = new Random().nextInt(propertySize);
            randClass = Class.forName(configs.get(level - 1).getProperty(Integer.
                    valueOf(randomNumber).toString()));
            var constructor = randClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return (Enemy) constructor.newInstance();
        } catch (Exception e) {
            throw new InvalidRandomEntity(level,propertySize,this.getClass().getName(),randomNumber,
                    randClass.getName(),e);
        }
    }
}
