package ru.nsu.fit.oop.game.exception.model.factory.weapon;

public class InvalidRandomWeapon extends WeaponFactoryException{

    public InvalidRandomWeapon(int level,int randomWeaponNumber, int propertySize, String name,
                               Throwable cause) {
        super("random weapon generation failed, level of weapon required: " + level + ".\n" +
                "Number of weapons of that level in config: " + propertySize + ".\n" +
                "Weapon number randomly generated " + randomWeaponNumber + ".\n" +
                "Name of the generated weapon: " + name + '\n', cause);
    }

}
