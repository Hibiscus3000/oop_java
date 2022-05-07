package ru.nsu.fit.oop.game.exception.model;

public class UnitGenerationException extends ModelException{

    public UnitGenerationException(String name) {
        super("Failed to generate unit \"" + name + "\"\n");
    }

    public UnitGenerationException(String name,Throwable cause) {
        super("Failed to generate unit \"" + name + ".\"",cause);
    }
}
