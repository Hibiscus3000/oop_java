package ru.nsu.fit.oop.game.exception.model;

public class UnableToUseWeaponException extends ModelException{

    public UnableToUseWeaponException(String unitName, Throwable cause) {
        super("\"" + unitName + "\" was unable to use his weapon.",cause);
    }
}
