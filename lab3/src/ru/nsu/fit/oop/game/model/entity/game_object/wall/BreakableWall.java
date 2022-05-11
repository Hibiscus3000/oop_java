package ru.nsu.fit.oop.game.model.entity.game_object.wall;

import ru.nsu.fit.oop.game.model.entity.weapon.Damage;

public class BreakableWall extends Wall{

    private int armor;

    public BreakableWall(int startX, int startY, int endX, int endY, double thickness,int armor) {
        super(startX, startY, endX, endY, thickness);
        this.armor = armor;
    }

    @Override
    public void takeDamage(Damage damage,double angle) {
        armor -= damage.getArmorDamage();
        if (armor <= 0)
            inGame = false;
    }

}
