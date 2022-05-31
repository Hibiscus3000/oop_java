package ru.nsu.fit.oop.game.model.entity.game_object.shell;

public class Arrow extends Shell{

    public Arrow(Double angle,Double x, Double y) {
        super("arrow",5, 8,angle,x,y,
                new Damage(10,4,1,0,1,0),1);
    }
}