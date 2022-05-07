package ru.nsu.fit.oop.game.model.factory.weapon;

import ru.nsu.fit.oop.game.exception.model.factory.InvalidConfigException;
import ru.nsu.fit.oop.game.model.factory.Factory;

public class WeaponFactory extends Factory {

    protected static volatile WeaponFactory instance;

    public WeaponFactory() throws InvalidConfigException {
        super("weapons.properties");
    }

    public static WeaponFactory getInstance() throws InvalidConfigException {
        if (null == instance)
            synchronized (WeaponFactory.class) {
                if (null == instance) {
                    instance = new WeaponFactory();
                }
            }
        return instance;
    }
}