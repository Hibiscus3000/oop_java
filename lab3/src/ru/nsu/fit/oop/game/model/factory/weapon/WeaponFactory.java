package ru.nsu.fit.oop.game.model.factory.weapon;

import ru.nsu.fit.oop.game.exception.model.factory.InvalidConfigException;
import ru.nsu.fit.oop.game.exception.model.factory.InvalidRandomEntity;
import ru.nsu.fit.oop.game.model.entity.Entity;
import ru.nsu.fit.oop.game.model.entity.weapon.Weapon;
import ru.nsu.fit.oop.game.model.factory.Factory;

import java.util.Random;

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

    public Weapon getRandomWeapon(int level) throws InvalidRandomEntity {
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
            return (Weapon) constructor.newInstance();
        } catch (Exception e) {
            throw new InvalidRandomEntity(level,propertySize,this.getClass().getName(),randomNumber,
                    randClass.getName(),e);
        }
    }
}