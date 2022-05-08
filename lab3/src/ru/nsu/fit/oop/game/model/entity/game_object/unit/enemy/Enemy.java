package ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy;

import ru.nsu.fit.oop.game.model.entity.game_object.GameObject;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Unit;

import java.util.List;

public abstract class Enemy extends Unit {

    protected Enemy(String name, int size, double speed, int lives, int health, int armor, int shield) {
        super(name,size, speed, lives, health, armor, shield);
    }

    /*public EnemyFrameProduction enemyFrameTurn(GameObject hero, List<GameObject> enemies) {

    }

    private void attackHero(GameObject hero) {

    }*/
}
