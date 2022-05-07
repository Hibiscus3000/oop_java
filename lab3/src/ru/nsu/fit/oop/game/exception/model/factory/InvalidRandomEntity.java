package ru.nsu.fit.oop.game.exception.model.factory;

public class InvalidRandomEntity extends FactoryException {

    public InvalidRandomEntity(String msg, Throwable cause) {
        super(msg,cause);
    }

    public InvalidRandomEntity(int level, int propertySize, String factoryName, int randomNumber,
                               String nameOfRandomEntity,Throwable cause) {
        super("Random entity generation failed, name of the factory: \"" + factoryName + "\".\n"
                + "Number of entities of that level in config: " + propertySize + ".\n" +
                "Number, that was  randomly generated: " + randomNumber + ".\n" +
                "Level of entity required: " + level +
                "Name of random entity" + nameOfRandomEntity + '.', cause);
    }
}
