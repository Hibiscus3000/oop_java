package ru.nsu.fit.oop.game.exception.model.factory;

public class InvalidConfigException extends FactoryException{

    public InvalidConfigException(String configName, String className, Throwable cause) {
        super("Getting config [" + configName + "] in " + className + " failed.",cause);
    }
}
