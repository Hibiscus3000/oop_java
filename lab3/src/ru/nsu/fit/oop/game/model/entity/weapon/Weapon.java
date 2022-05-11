package ru.nsu.fit.oop.game.model.entity.weapon;

import ru.nsu.fit.oop.game.exception.model.shell.ShellInstantiationException;
import ru.nsu.fit.oop.game.exception.model.shell.ShellNotFoundException;
import ru.nsu.fit.oop.game.model.entity.Entity;
import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;

import javax.swing.*;
import java.lang.reflect.Constructor;

public abstract class Weapon implements Entity {

    protected Timer cooldown;
    protected boolean isReadyToBeUsed = true;
    protected String shellName;
    protected Constructor<Shell> constructor;
    protected final int shellRadius;

    protected Weapon(int cooldownTimeMillis, String shellName) throws ShellNotFoundException {
        try {
            this.shellName = shellName;
            var opClass = Class.forName(shellName);
            constructor = (Constructor<Shell>) opClass.getDeclaredConstructor(Double.class,
                    Double.class, Double.class);
            shellRadius = constructor.newInstance(Double.valueOf(0), Double.valueOf(0),
                    Double.valueOf(0)).getRadius();
        } catch (Exception e) {
            throw new ShellNotFoundException(this.getClass().getName(), shellName, e);
        }
        cooldown = new Timer(cooldownTimeMillis, null);
        cooldown.addActionListener(event -> {
            isReadyToBeUsed = true;
            cooldown.stop();
        });
        cooldown.start();
    }

    public Shell use(double angle, double radius, double x, double y) throws ShellInstantiationException {
        if (false == isReadyToBeUsed)
            return null;
        isReadyToBeUsed = false;
        cooldown.restart();
        try {
            return constructor.newInstance(angle, Math.cos(angle) * (radius + shellRadius) + x,
                    Math.sin(angle) * (radius + shellRadius) + y);
        } catch (Exception e) {
            throw new ShellInstantiationException(this.getClass().getName(), shellName, e);
        }
    }

    public boolean getIsReadyToUseStatus() {
        return isReadyToBeUsed;
    }

}
