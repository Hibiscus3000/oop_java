package ru.nsu.fit.oop.game.model.entity.game_object.shell;

import ru.nsu.fit.oop.game.model.entity.Damage;
import ru.nsu.fit.oop.game.model.entity.game_object.GameObject;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Unit;

public class Shell extends GameObject {

    private final Damage damage;

    public Shell(String name, int size, double speed, Double angle, Double x, Double y, Damage damage) {
        super(name, size, speed);
        setCoords(x,y);
        this.damage = damage;
        this.angle = angle;
    }

    public void move() {
        changeCoords(Math.cos(angle) * gameObjectParams.getSpeed(),
                Math.sin(angle) * gameObjectParams.getSpeed());
    }

    public Damage getDamage() {
        return damage;
    }

    public void setInGameFalse() {
        inGame = false;
    }
}
