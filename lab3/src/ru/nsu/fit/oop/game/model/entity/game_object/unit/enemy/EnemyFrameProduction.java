package ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy;

import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;

public class EnemyFrameProduction {

    private Shell shell;

    public EnemyFrameProduction(Shell shell) {
        this.shell = shell;
    }

    public Shell getShell() {
        return shell;
    }
}
