package ru.nsu.fit.oop.game.model.entity.game_object.shell;

public class Arrow extends Shell{

    public Arrow(Double angle,Double x, Double y) {
        super("arrow",5, 1.7,angle,x,y,10);
    }
}
