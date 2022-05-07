package ru.nsu.fit.oop.game.model.unit;

import ru.nsu.fit.oop.game.model.ability.Abilitiy;
import ru.nsu.fit.oop.game.model.weapon.Weapon;

import java.awt.*;
import java.util.List;

public class Unit {

    protected String name;
    protected int x;
    protected int y;
    protected int sizeX;
    protected int sizeY;
    protected int lives;
    protected int maxHealth;
    protected int health;
    protected int armor;
    protected int maxArmor;
    protected int shield;
    protected int maxShield;
    protected List<Weapon> weapons;
    protected List<Abilitiy> abilities;
    protected boolean isAlive = true;

    protected Unit(String name,int x, int y, int sizeX, int sizeY, int lives, int health,int armor,
                   int shield) {
        this.name = name;
        setCoords(x,y);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
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
                isAlive = false;
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

    public void setCoords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void changeCoords(int shiftX, int shiftY) {
        this.x -= shiftX;
        this.y -= shiftY;
    }

    public Dimension getCoords() {
        return new Dimension(x,y);
    }

    public boolean getLiveStatus() {
        return isAlive;
    }
}
