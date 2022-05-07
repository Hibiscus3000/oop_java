package ru.nsu.fit.oop.game.model.subject.unit;

import ru.nsu.fit.oop.game.exception.model.UnitGenerationException;
import ru.nsu.fit.oop.game.exception.model.factory.FactoryException;
import ru.nsu.fit.oop.game.model.weapon.WeaponFactory;

public class Hero extends Unit{

    public Hero(String name,int x, int y) throws UnitGenerationException {
        super(name,x,y,10,10,1,40,0,0,10);
        try {
            WeaponFactory.getInstance().getRandomWeapon(1);
        } catch (FactoryException e) {
            throw new UnitGenerationException(this.getClass().getSimpleName(),e);
        }
    }
}
