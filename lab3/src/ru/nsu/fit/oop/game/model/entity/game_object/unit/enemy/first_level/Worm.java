package ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy.first_level;

import ru.nsu.fit.oop.game.exception.model.factory.FactoryException;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy.Enemy;

public class Worm extends Enemy {
    protected Worm() throws FactoryException {
        super("worm",17,2,80,260,500, 0,40,0,0,
                "ru.nsu.fit.oop.game.model.entity.weapon.first_level.Bow",0,0,0,0,0,0);
    }
}
