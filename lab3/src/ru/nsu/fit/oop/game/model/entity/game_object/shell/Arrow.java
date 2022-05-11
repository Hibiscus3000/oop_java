package ru.nsu.fit.oop.game.model.entity.game_object.shell;

import ru.nsu.fit.oop.game.model.entity.weapon.Damage;

public class Arrow extends Shell{

    public Arrow(Double angle,Double x, Double y) {
        super("arrow",3, 10,angle,x,y,
                new Damage(10,1,0,0),4);
    }
}