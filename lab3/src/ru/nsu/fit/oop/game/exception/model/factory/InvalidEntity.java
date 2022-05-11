package ru.nsu.fit.oop.game.exception.model.factory;

public class InvalidEntity extends FactoryException {

    public InvalidEntity(String factoryName, String entityName, Throwable cause) {
        super("\"" + factoryName + "\" wasn't able to produce \"" + entityName + "\".",cause);
    }
}
