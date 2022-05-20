package ru.nsu.fit.oop.game.model.entity.weapon;

import ru.nsu.fit.oop.game.exception.model.shell.ShellNotFoundException;

import javax.swing.*;

public abstract class Reloadable extends Weapon{

    protected int ammo;
    protected int ammoToBeReloaded;
    protected int magazineCapacity;
    Timer reloadTimer;

    protected Reloadable(int cooldownTimeMillis, int reloadTimeMillis, int magazineCapacity,
                         String shellName) throws ShellNotFoundException {
        super(cooldownTimeMillis, shellName);
        this.magazineCapacity = magazineCapacity;
        ammo = magazineCapacity;
        reloadTimer = new Timer(reloadTimeMillis,null);
        reloadTimer.addActionListener(e -> {
            reloadTimer.stop();
            ammo += ammoToBeReloaded;
        });
    }

    public void reload(int ammoToBeReloaded) {
        reloadTimer.start();
    }

    public int getAmmo() {
        return ammo;
    }

    public int getMagazineCapacity() {
        return magazineCapacity;
    }
}
