package ru.nsu.fit.oop.game.model.entity.game_object.shell;

public class ShotgunBullet extends Shell{

    public ShotgunBullet(Double angle, Double x, Double y) {
        super("shotgun bullet", 5,12, angle, x, y, new Damage(20,10,
                5,2,5,0), 0);
    }
}
