package ru.nsu.fit.oop.game.model.entity.game_object.unit;

import ru.nsu.fit.oop.game.exception.model.UnitGenerationException;
import ru.nsu.fit.oop.game.exception.model.factory.FactoryException;
import ru.nsu.fit.oop.game.model.factory.weapon.WeaponFactory;

public class Hero extends Unit{

    public Hero(String name,double x, double y) throws UnitGenerationException {
        super(name,x,y,10,1,1,40,0,0);
        try {
            WeaponFactory.getInstance().getRandom(1);
        } catch (FactoryException e) {
            throw new UnitGenerationException(this.getClass().getSimpleName(),e);
        }
    }
}
