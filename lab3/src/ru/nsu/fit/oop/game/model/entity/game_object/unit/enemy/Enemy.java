package ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy;

import ru.nsu.fit.oop.game.exception.model.UnableToUseWeaponException;
import ru.nsu.fit.oop.game.exception.model.factory.FactoryException;
import ru.nsu.fit.oop.game.model.GameObjectsInfo;
import ru.nsu.fit.oop.game.model.entity.game_object.GameObject;
import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Hero;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Unit;
import ru.nsu.fit.oop.game.model.factory.weapon.WeaponFactory;
import ru.nsu.fit.oop.game.model.entity.weapon.Weapon;

import java.util.List;

public abstract class Enemy extends Unit {

    protected double preferredDistance;
    protected double saveDistance;
    protected double defaultPreferredDistance;

    protected Enemy(String name, int radius, double speed, double preferredDistance,double saveDistance,
                    int lives, int health, int armor, int shield, String weaponName)
            throws FactoryException {
        super(name, radius, speed, lives, health, armor, shield);
        this.preferredDistance = preferredDistance;
        defaultPreferredDistance = preferredDistance;
        this.saveDistance = saveDistance;
        weapons.add((Weapon) WeaponFactory.getInstance().getEntity(weaponName));
    }

    protected Enemy(String name, int radius, double speed, double preferredDistance, double saveDistance,
                    int lives, int health, int armor, int shield) {
        super(name, radius, speed, lives, health, armor, shield);
        this.preferredDistance = preferredDistance;
        defaultPreferredDistance = preferredDistance;
        this.saveDistance = saveDistance;
    }

    public EnemyFrameProduction enemyFrameTurn(GameObjectsInfo gameObjectsInfo) throws UnableToUseWeaponException {
        EnemyFrameProduction enemyFrameProduction = new EnemyFrameProduction();
        double heroRelativeAngle = getRelativeAngle(this, gameObjectsInfo.getHero());
        move(heroRelativeAngle, gameObjectsInfo.getHero(), gameObjectsInfo.getShells());
        if (weapons.get(getCurrentWeaponNumber()).getIsReadyToUseStatus())
            enemyFrameProduction.setShell(useWeapon(heroRelativeAngle));
        return enemyFrameProduction;
    }

    private void move(double heroRelativeAngle, Hero hero, List<Shell> shellsParams) {
        double dodgeAngle = 0;
        int counterClockWiseRotation = 0,clockWiseRotation = 0;
        for (Shell shell : shellsParams) {
            double shellRelativeAngle = getRelativeAngle(this,shell);
            if (shell.getAngle() * shellRelativeAngle <= 0 && Math.pow(Math.sin(shell.getAngle() - shellRelativeAngle), 2) < Math.pow(getRadius() +
                    shell.getRadius(), 2) / (Math.pow(getX() - shell.getX(), 2) + Math.pow(getY() -
                    shell.getY(), 2))) {
                if (Math.sin(shell.getAngle() - shellRelativeAngle) < 0)
                    dodgeAngle = -Math.PI / 2;
                else
                    dodgeAngle = Math.PI / 2;
                move(shell.getAngle() + dodgeAngle);
                return;
            }
        }
        if (health >= maxHealth / 2)
            preferredDistance = defaultPreferredDistance;
        else
            preferredDistance = saveDistance;
        if (Math.pow(hero.getX() - getX(), 2) + Math.pow(hero.getY() -
                getY(), 2) > Math.pow(preferredDistance + getRadius() + getSpeed() + hero.getSpeed() +
                hero.getRadius(), 2)) {
            move(heroRelativeAngle);
        } else if (Math.pow(hero.getX() - getX(), 2) + Math.pow(hero.getY() - getY(), 2) <
                Math.pow(preferredDistance + getRadius() + hero.getRadius(), 2))
            move(heroRelativeAngle + Math.PI);
    }

    private double getRelativeAngle(GameObject gameObject, GameObject relativeGameObject) {
        double x = relativeGameObject.getX() - gameObject.getX();
        double y = relativeGameObject.getY() - gameObject.getY();
        double angle;
        if (0 == x) {
            if (y > 0)
                angle = Math.PI / 2;
            else
                angle = Math.PI / 2;
        } else if (x > 0) {
            angle = Math.atan(y / x);
        } else if (y >= 0) {
            angle = Math.PI + Math.atan(y / x);
        } else {
            angle = -Math.PI + Math.atan(y / x);
        }
        return angle;
    }
}
