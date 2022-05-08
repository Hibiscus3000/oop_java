package ru.nsu.fit.oop.game.model.entity.game_object.unit;

import ru.nsu.fit.oop.game.exception.model.UnableToUseWeaponException;
import ru.nsu.fit.oop.game.exception.model.shell.ShellInstantiationException;
import ru.nsu.fit.oop.game.model.Radix;
import ru.nsu.fit.oop.game.model.ability.Abilitiy;
import ru.nsu.fit.oop.game.model.entity.game_object.GameObject;
import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;
import ru.nsu.fit.oop.game.model.entity.weapon.Weapon;

import java.util.ArrayList;
import java.util.List;

public abstract class Unit extends GameObject {

    protected double defaultSpeed;
    protected int lives;
    protected int maxHealth;
    protected int health;
    protected int armor;
    protected int maxArmor;
    protected int shield;
    protected int maxShield;
    protected int currentWeaponNumber;
    protected int currentAbilityNumber;
    protected List<Weapon> weapons = new ArrayList<>();
    protected List<Abilitiy> abilities = new ArrayList<>();

    protected Unit(String name, int size, double speed, int lives, int health,
                   int armor, int shield) {
        super(name,size,speed);
        defaultSpeed = speed;
        this.lives = lives;
        this.health = health;
        this.maxHealth = health;
        this.armor = armor;
        this.maxArmor = armor;
        this.shield = shield;
        this.maxShield = shield;
        this.currentWeaponNumber = 0;
        this.currentAbilityNumber = 0;
    }

    public void changeHealth(int healthAmount) {
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

    public void changeArmor(int armorAmount) {
        armor += armorAmount;
        if (armor > maxArmor) {
            armor = maxArmor;
            return;
        }
        if (armor < 0)
            armor = 0;
    }

    public void changeShield(int shieldAmount) {
        shield += shieldAmount;
        if (shield > maxShield) {
            shield = maxShield;
            return;
        }
        if (shield < 0)
            shield = 0;
    }

    public Shell useWeapon(double angle) throws UnableToUseWeaponException {
        try {
            return weapons.get(currentWeaponNumber).use(angle, getX(), getY());
        } catch (ShellInstantiationException e) {
            throw new UnableToUseWeaponException(this.name,e);
        }
    }
}
