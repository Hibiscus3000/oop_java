package ru.nsu.fit.oop.game.model.entity.weapon;

public class Damage {

    private final int healthDamage;
    private final int armorDamage;
    private final int shieldDamage;
    private final double impact;

    public Damage(int healthDamage, int armorDamage, int sheildDamage,double impact) {
        this.healthDamage = healthDamage;
        this.armorDamage = armorDamage;
        this.shieldDamage = sheildDamage;
        this.impact = impact;
    }

    public double getImpact() {
        return impact;
    }

    public int getShieldDamage() {
        return shieldDamage;
    }

    public int getArmorDamage() {
        return armorDamage;
    }

    public int getHealthDamage() {
        return healthDamage;
    }
}
