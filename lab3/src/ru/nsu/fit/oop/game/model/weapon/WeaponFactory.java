package ru.nsu.fit.oop.game.model.weapon;

import ru.nsu.fit.oop.game.exception.model.factory.InvalidConfigException;
import ru.nsu.fit.oop.game.exception.model.factory.weapon.InvalidRandomWeapon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class WeaponFactory {

    private static volatile WeaponFactory instance;
    private static List<Properties> weaponsConfigs = new ArrayList<>();
    private static Properties weaponsConfigsConfig = new Properties();

    public WeaponFactory() throws InvalidConfigException {
        String propertyName = null;
        try {
            var stream = WeaponFactory.class.getResourceAsStream(propertyName = "weapons_config.properties");
            weaponsConfigsConfig = new Properties();
            Properties nextWeaponConfig;
            weaponsConfigsConfig.load(stream);
            for (int i = 1; i < Integer.parseInt(weaponsConfigsConfig.getProperty("size")) + 1; i++) {
                stream = WeaponFactory.class.getResourceAsStream(propertyName =
                        weaponsConfigsConfig.getProperty(Integer.valueOf(i).toString()));
                nextWeaponConfig = new Properties();
                nextWeaponConfig.load(stream);
                weaponsConfigs.add(nextWeaponConfig);
            }
        } catch (IOException e) {
            throw new InvalidConfigException(propertyName, this.getClass().getSimpleName(), e);
        }
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

    public Weapon getRandomWeapon(int level) throws InvalidRandomWeapon {
        Class weaponClass;
        String weaponName;
        int randomWeaponNumber = -1, propertySize = -1;
        try {
            propertySize = weaponsConfigs.get(level - 1).size();
            randomWeaponNumber = new Random().nextInt(propertySize);
            weaponClass = Class.forName(weaponsConfigs.get(level - 1).getProperty(Integer.valueOf(randomWeaponNumber).toString()));
            var constructor = weaponClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return (Weapon) constructor.newInstance();
        } catch (Exception e) {
            throw new InvalidRandomWeapon(level,randomWeaponNumber,propertySize,
                    weaponsConfigsConfig.getProperty(Integer.valueOf(level).toString()),e);
        }
    }
}