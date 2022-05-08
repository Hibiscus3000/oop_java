package ru.nsu.fit.oop.game.model.entity.weapon;

import ru.nsu.fit.oop.game.exception.model.shell.ShellInstantiationException;
import ru.nsu.fit.oop.game.exception.model.shell.ShellNotFoundException;
import ru.nsu.fit.oop.game.model.entity.Entity;
import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;

import javax.swing.*;
import java.lang.reflect.Constructor;

public abstract class Weapon extends Entity {

    protected Timer cooldown;
    protected boolean isReadyToBeUsed = true;
    String shellName;
    Constructor<Shell> constructor;

    protected Weapon(int cooldownTimeMillis, String shellName) throws ShellNotFoundException {
        try {
            this.shellName = shellName;
            var opClass = Class.forName(shellName);
            this.constructor = (Constructor<Shell>)opClass.getDeclaredConstructor(Double.class,
                    Double.class,Double.class);
        } catch (Exception e) {
            throw new ShellNotFoundException(this.getClass().getName(),shellName,e);
        }
        cooldown = new Timer(cooldownTimeMillis, null);
        cooldown.start();
        cooldown.addActionListener(event -> {
            isReadyToBeUsed = true;
            cooldown.stop();
        });
    }

    public Shell use(double angle,double x, double y) throws ShellInstantiationException {
        if (false == isReadyToBeUsed)
            return null;
        isReadyToBeUsed = false;
        cooldown.restart();
        try {
            return constructor.newInstance(angle,x,y);
        } catch (Exception e) {
            throw new ShellInstantiationException(this.getClass().getName(),shellName,e);
        }
    }

}
