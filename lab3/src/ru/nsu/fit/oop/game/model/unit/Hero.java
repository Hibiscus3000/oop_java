package ru.nsu.fit.oop.game.model.unit;

import ru.nsu.fit.oop.game.exception.model.UnitGenerationException;
import ru.nsu.fit.oop.game.exception.model.factory.FactoryException;
import ru.nsu.fit.oop.game.exception.model.factory.InvalidConfigException;
import ru.nsu.fit.oop.game.exception.model.factory.weapon.InvalidRandomWeapon;
import ru.nsu.fit.oop.game.model.weapon.WeaponFactory;

public class Hero extends Unit{

    public Hero(String name) throws UnitGenerationException {
        this.name = name;
        health = 40;
        maxHealth = 40;
        maxArmor = 10;
        maxShield = 0;
        try {
            WeaponFactory.getInstance().getRandomWeapon(1);
        } catch (FactoryException e) {
            throw new UnitGenerationException(this.getClass().getSimpleName(),e);
        }
    }
}
