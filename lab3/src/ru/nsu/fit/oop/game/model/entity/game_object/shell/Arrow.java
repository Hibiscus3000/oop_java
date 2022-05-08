package ru.nsu.fit.oop.game.model.entity.game_object.shell;

import ru.nsu.fit.oop.game.model.entity.Damage;

public class Arrow extends Shell{

    public Arrow(Double angle,Double x, Double y) {
        super("arrow",5, 10,angle,x,y,
                new Damage(10,0,0,0));
    }
}
