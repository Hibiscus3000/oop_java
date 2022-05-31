package ru.nsu.fit.oop.game.model.entity.game_object.unit;

import ru.nsu.fit.oop.game.exception.model.UnableToUseWeaponException;
import ru.nsu.fit.oop.game.exception.model.shell.ShellInstantiationException;
import ru.nsu.fit.oop.game.model.ability.Abilitiy;
import ru.nsu.fit.oop.game.model.entity.game_object.shell.Damage;
import ru.nsu.fit.oop.game.model.entity.game_object.GameObject;
import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;
import ru.nsu.fit.oop.game.model.entity.weapon.Weapon;

import javax.swing.*;
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
    protected Timer healthRegenTimer;
    protected Timer armorRegenTimer;
    protected Timer shieldRegenTimer;
    protected int healthRegen;
    protected int armorRegen;
    protected int shieldRegen;
    protected List<Weapon> weapons = new ArrayList<>();
    protected List<Abilitiy> abilities = new ArrayList<>();

    protected Unit(String name, int radius, double speed, int lives, int health,
                   int armor, int shield, int healthRegenMillis, int armorRegenMillis,int shieldRegenMillis,
                   int healthRegen,int armorRegen,int shieldRegen) {
        super(name,radius,speed);
        defaultSpeed = speed;
        setHealthRegen(healthRegenMillis, healthRegen);
        setShieldRegen(shieldRegenMillis,shieldRegen);
        setArmorRegen(armorRegenMillis,armorRegen);
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

    public void setHealthRegen(int healthRegenMillis, int healthRegen) {
        if (null != healthRegenTimer)
            healthRegenTimer.stop();
        this.healthRegen = healthRegen;
        if (0 == healthRegen)
            return;
        healthRegenTimer = new Timer(healthRegenMillis,event -> {
            changeHealth(healthRegen);
        });
        healthRegenTimer.start();
    }

    public void setArmorRegen(int armorRegenMillis, int armorRegen) {
        if (null != armorRegenTimer)
            armorRegenTimer.stop();
        this.armorRegen = armorRegen;
        if (0 == armorRegen)
            return;
        armorRegenTimer = new Timer(armorRegenMillis,event -> {
            changeArmor(armorRegen);
        });
        armorRegenTimer.start();
    }

    public void setShieldRegen(int shieldRegenMillis, int shieldRegen) {
        if (null != shieldRegenTimer)
            shieldRegenTimer.stop();
        this.shieldRegen = shieldRegen;
        if (0 == shieldRegen)
            return;
        shieldRegenTimer = new Timer(shieldRegenMillis,event -> {
            changeShield(shieldRegen);
        });
        shieldRegenTimer.start();
    }

    public void changeHealth(int healthAmount) {
        health += healthAmount;
        if (health > maxHealth) {
            health = maxHealth;
            return;
        }
        if (health <= 0) {
            --lives;
            health = maxHealth;
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

    public List<Shell> useWeapon(double angle) throws UnableToUseWeaponException {
        try {
            return weapons.get(currentWeaponNumber).use(angle, getRadius(),getX(),getY());
        } catch (ShellInstantiationException e) {
            throw new UnableToUseWeaponException(this.name,e);
        }
    }

    protected void move(double angle,double impact) {
        setAngle(angle);
        changeCoords(Math.cos(angle) * impact, Math.sin(angle) * impact);
    }

    public void takeDamage(Damage damage, double angle) {
        if (0 == armor && 0 == shield)
            changeHealth(-damage.getHealthDamageWithoutProtection());
        else
            changeHealth(-damage.getHealthDamageWithProtection());
        if (0 == shield)
            changeArmor(-damage.getArmorDamageWithoutShield());
        else
            changeArmor(-damage.getArmorDamageWithShield());
        changeShield(-damage.getShieldDamage());
        move(angle,damage.getImpact());
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentWeaponNumber() {
        return currentWeaponNumber;
    }
}
