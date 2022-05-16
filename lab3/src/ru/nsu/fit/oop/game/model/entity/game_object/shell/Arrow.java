package ru.nsu.fit.oop.game.model.entity.game_object.shell;

import ru.nsu.fit.oop.game.model.entity.weapon.Damage;

public class Arrow extends Shell{

    public Arrow(Double angle,Double x, Double y) {
        super("arrow",10, 8,angle,x,y,
                new Damage(10,200,0,0),4);
    }
}