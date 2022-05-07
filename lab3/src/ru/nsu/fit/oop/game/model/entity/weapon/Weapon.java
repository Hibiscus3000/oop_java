package ru.nsu.fit.oop.game.model.entity.weapon;

import ru.nsu.fit.oop.game.exception.model.shell.ShellInstantiationException;
import ru.nsu.fit.oop.game.exception.model.shell.ShellNotFoundException;
import ru.nsu.fit.oop.game.model.entity.Entity;
import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;

import javax.swing.*;
import java.lang.reflect.Constructor;

public class Weapon implements Entity {

    protected Timer reload;
    protected boolean isReadyToBeUsed = true;
    String shellName;
    Constructor<?> constructor;


    protected Weapon(int reloadTimeMillis, String shellName) throws ShellNotFoundException {
        try {
            this.shellName = shellName;
            var opClass = Class.forName(shellName);
            this.constructor = opClass.getDeclaredConstructor();
        } catch (Exception e) {
            throw new ShellNotFoundException(this.getClass().getName(),shellName,e);
        }
        reload = new Timer(reloadTimeMillis, null);
        reload.addActionListener(event -> {
            isReadyToBeUsed = true;
        });
    }

    protected Shell use() throws ShellInstantiationException {
        if (false == isReadyToBeUsed)
            return null;
        isReadyToBeUsed = false;
        try {
            return (Shell) constructor.newInstance();
        } catch (Exception e) {
            throw new ShellInstantiationException(this.getClass().getName(),shellName,e);
        }
    }

}
