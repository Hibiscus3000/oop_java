package ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy;

import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;

import java.util.List;

public class EnemyFrameProduction {

    private List<Shell> shells;

    public List<Shell> getShells() {
        return shells;
    }

    public void setShells(List<Shell> shells) {
        this.shells = shells;
    }
}
