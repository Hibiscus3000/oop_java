package ru.nsu.fit.oop.game.model.entity.game_object.shell;

public class Damage {

    private final int healthDamageWithoutProtection;
    private final int healthDamageWithProtection;
    private final int armorDamageWithoutShield;
    private final int armorDamageWithShield;
    private final int shieldDamage;
    private final double impact;

    public Damage(int healthDamageWithoutProtection,int healthDamageWithProtection,
                  int armorDamageWithoutShield,int armorDamageWithShield, int shieldDamage,double impact) {
        this.healthDamageWithoutProtection = healthDamageWithoutProtection;
        this.healthDamageWithProtection = healthDamageWithProtection;
        this.armorDamageWithoutShield = armorDamageWithoutShield;
        this.armorDamageWithShield = armorDamageWithShield;
        this.shieldDamage = shieldDamage;
        this.impact = impact;
    }

    public double getImpact() {
        return impact;
    }

    public int getHealthDamageWithoutProtection() {
        return healthDamageWithoutProtection;
    }

    public int getHealthDamageWithProtection() {
        return healthDamageWithProtection;
    }

    public int getArmorDamageWithoutShield() {
        return armorDamageWithoutShield;
    }

    public int getArmorDamageWithShield() {
        return armorDamageWithShield;
    }

    public int getShieldDamage() {
        return shieldDamage;
    }
}
