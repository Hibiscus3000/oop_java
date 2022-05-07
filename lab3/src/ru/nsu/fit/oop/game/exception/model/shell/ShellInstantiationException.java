package ru.nsu.fit.oop.game.exception.model.shell;

public class ShellInstantiationException extends ShellException {

    public ShellInstantiationException(String weaponName, String shellName, Throwable cause) {
        super("Failed to create new instance of \"" + shellName + "\" in \"" + weaponName + "\".", cause);
    }
}
