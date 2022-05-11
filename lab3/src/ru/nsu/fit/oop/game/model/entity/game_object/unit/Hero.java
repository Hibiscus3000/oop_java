package ru.nsu.fit.oop.game.model.entity.game_object.unit;

import ru.nsu.fit.oop.game.exception.model.UnitGenerationException;
import ru.nsu.fit.oop.game.exception.model.factory.FactoryException;
import ru.nsu.fit.oop.game.model.factory.weapon.WeaponFactory;
import ru.nsu.fit.oop.game.model.entity.weapon.Weapon;

public class Hero extends Unit {

    public Hero(String name) throws UnitGenerationException {
        super(name, 30, 4, 0, 400, 0, 0);
        try {
            weapons.add((Weapon) WeaponFactory.getInstance().getRandomEntity(1));
        } catch (FactoryException e) {
            throw new UnitGenerationException(this.getClass().getSimpleName(), e);
        }
    }
}
