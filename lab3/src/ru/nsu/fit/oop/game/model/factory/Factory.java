package ru.nsu.fit.oop.game.model.factory;

import ru.nsu.fit.oop.game.exception.model.factory.InvalidConfigException;
import ru.nsu.fit.oop.game.exception.model.factory.InvalidRandomEntity;
import ru.nsu.fit.oop.game.model.entity.Entity;
import ru.nsu.fit.oop.game.model.factory.weapon.WeaponFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class Factory {

    protected static List<Properties> configs = new ArrayList<>();
    protected static Properties configsConfig = new Properties();

    public Factory(String configsConfigName) throws InvalidConfigException {
        String propertyName = null;
        try {
            var stream = Factory.class.getResourceAsStream(propertyName = configsConfigName);
            configsConfig = new Properties();
            Properties nextConfig;
            configsConfig.load(stream);
            for (int i = 0; i < configsConfig.size(); i++) {
                stream = Factory.class.getResourceAsStream(propertyName =
                        configsConfig.getProperty(Integer.valueOf(i).toString()));
                nextConfig = new Properties();
                nextConfig.load(stream);
                configs.add(nextConfig);
            }
        } catch (IOException e) {
            throw new InvalidConfigException(propertyName, this.getClass().getSimpleName(), e);
        }
    }

    public Entity getRandom(int level) throws InvalidRandomEntity {
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
            return (Entity) constructor.newInstance();
        } catch (Exception e) {
            throw new InvalidRandomEntity(level,propertySize,this.getClass().getName(),randomNumber,
                    randClass.getName(),e);
        }
    }
}
