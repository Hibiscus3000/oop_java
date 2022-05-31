package ru.nsu.fit.oop.game.model.entity.weapon.first_level;

import ru.nsu.fit.oop.game.exception.model.shell.ShellNotFoundException;
import ru.nsu.fit.oop.game.model.entity.weapon.Weapon;

public class Bow extends Weapon {
    protected Bow() throws ShellNotFoundException {
        super(1300, "ru.nsu.fit.oop.game.model.entity.game_object.shell.Arrow","Bow");
    }
}
