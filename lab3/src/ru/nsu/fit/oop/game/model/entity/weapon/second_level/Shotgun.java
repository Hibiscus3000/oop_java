package ru.nsu.fit.oop.game.model.entity.weapon.second_level;

import ru.nsu.fit.oop.game.exception.model.shell.ShellInstantiationException;
import ru.nsu.fit.oop.game.exception.model.shell.ShellNotFoundException;
import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;
import ru.nsu.fit.oop.game.model.entity.weapon.Reloadable;

import java.util.ArrayList;
import java.util.List;

public class Shotgun extends Reloadable {

    public Shotgun() throws ShellNotFoundException {
        super(1000,2000,8,
                "ru.nsu.fit.oop.game.model.entity.game_object.shell.ShotgunBullet");
    }

    @Override
    public List<Shell> use(double angle, double radius, double x, double y) throws ShellInstantiationException {
        if ((false == isReadyToBeUsed) || (ammo <= 0))
            return null;
        --ammo;
        isReadyToBeUsed = false;
        cooldown.restart();
        List<Shell> shells = new ArrayList<>();
        try {
            double shellX = Math.cos(angle) * (radius + shellRadius) + x;
            double shellY = Math.sin(angle) * (radius + shellRadius) + y;
            shells.add(constructor.newInstance(angle,shellX,shellY));
            shells.add(constructor.newInstance(angle + Math.PI / 18, shellX,shellY));
            shells.add(constructor.newInstance(angle - Math.PI / 18, shellX, shellY));
            return shells;
        } catch (Exception e) {
            throw new ShellInstantiationException(this.getClass().getName(), shellName, e);
        }
    }
}
