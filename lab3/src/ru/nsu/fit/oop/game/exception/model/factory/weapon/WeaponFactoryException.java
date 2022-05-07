package ru.nsu.fit.oop.game.exception.model.factory.weapon;

import ru.nsu.fit.oop.game.exception.model.factory.FactoryException;

public class WeaponFactoryException extends FactoryException {

    public WeaponFactoryException() {
        super("weapon factory exception");
    }

    public WeaponFactoryException(String msg) {
        super(msg);
    }

    public WeaponFactoryException(String msg,Throwable cause) {
        super(msg,cause);
    }
}
