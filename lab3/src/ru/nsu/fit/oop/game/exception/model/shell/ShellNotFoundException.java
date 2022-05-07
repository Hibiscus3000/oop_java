package ru.nsu.fit.oop.game.exception.model.shell;

import ru.nsu.fit.oop.game.exception.model.ModelException;

public class ShellNotFoundException extends ShellException {

    public ShellNotFoundException(String weaponName, String shellName, Throwable cause) {
        super("\"" + shellName + "\" not found in \"" + weaponName + "\" constructor.",cause);
    }
}
