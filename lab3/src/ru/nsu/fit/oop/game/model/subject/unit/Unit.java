package ru.nsu.fit.oop.game.model.subject.unit;

import ru.nsu.fit.oop.game.model.ability.Abilitiy;
import ru.nsu.fit.oop.game.model.subject.Subject;
import ru.nsu.fit.oop.game.model.weapon.Weapon;

import java.awt.*;
import java.util.List;

public class Unit extends Subject {

    protected int lives;
    protected int maxHealth;
    protected int health;
    protected int armor;
    protected int maxArmor;
    protected int shield;
    protected int maxShield;
    protected List<Weapon> weapons;
    protected List<Abilitiy> abilities;

    protected Unit(String name,double x, double y, int size, double speed, int lives, int health,
                   int armor, int shield) {
        super(name, x, y,size,speed);
        this.lives = lives;
        this.health = health;
        this.maxHealth = health;
        this.armor = armor;
        this.maxArmor = armor;
        this.shield = shield;
        this.maxShield = shield;
    }

    protected void changeHealth(int healthAmount) {
        health += healthAmount;
        if (health > maxHealth) {
            health = maxHealth;
            return;
        }
        if (health <= 0) {
            --lives;
            if (-1 == lives)
                inGame = false;
        }
    }

    protected void changeArmor(int armorAmount) {
        armor += armorAmount;
        if (armor > maxArmor) {
            armor = maxArmor;
            return;
        }
        if (armor < 0)
            armor = 0;
    }

    protected void changeShield(int shieldAmount) {
        shield += shieldAmount;
        if (shield > maxShield) {
            shield = maxShield;
            return;
        }
        if (shield < 0)
            shield = 0;
    }

}
