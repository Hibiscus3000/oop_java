package ru.nsu.fit.oop.game.model.entity.game_object.unit.enemy;

import ru.nsu.fit.oop.game.exception.model.UnableToUseWeaponException;
import ru.nsu.fit.oop.game.exception.model.factory.FactoryException;
import ru.nsu.fit.oop.game.model.GameObjectsInfo;
import ru.nsu.fit.oop.game.model.entity.game_object.shell.Shell;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Hero;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Unit;
import ru.nsu.fit.oop.game.model.factory.weapon.WeaponFactory;
import ru.nsu.fit.oop.game.model.entity.weapon.Weapon;

import java.util.List;

import static ru.nsu.fit.oop.game.model.Geometry.*;

public abstract class Enemy extends Unit {

    protected double preferredDistance;
    protected double saveDistance;
    protected double defaultPreferredDistance;
    protected final double squaredAggressionRange;
    protected boolean movedOnFrame;

    protected Enemy(String name, int radius, double speed, double preferredDistance, double saveDistance,
                    double aggressionRange, int lives, int health, int armor, int shield, String weaponName)
            throws FactoryException {
        super(name, radius, speed, lives, health, armor, shield);
        squaredAggressionRange = aggressionRange * aggressionRange;
        this.preferredDistance = preferredDistance;
        defaultPreferredDistance = preferredDistance;
        this.saveDistance = saveDistance;
        weapons.add((Weapon) WeaponFactory.getInstance().getEntity(weaponName));
    }

    protected Enemy(String name, int radius, double speed, double preferredDistance, double saveDistance,
                    double aggressionRange, int lives, int health, int armor, int shield) {
        super(name, radius, speed, lives, health, armor, shield);
        squaredAggressionRange = aggressionRange * aggressionRange;
        this.preferredDistance = preferredDistance;
        defaultPreferredDistance = preferredDistance;
        this.saveDistance = saveDistance;
    }

    public EnemyFrameProduction enemyFrameTurn(GameObjectsInfo gameObjectsInfo) throws UnableToUseWeaponException {
        if (getSquaredRelativeDistance(this.getCoords(), gameObjectsInfo.getHero().getCoords()) >
                squaredAggressionRange)
            return null;
        double heroRelativeAngle = getRelativeAngle(this.getCoords(), gameObjectsInfo.getHero().getCoords());
        if (true == handleWalls(gameObjectsInfo, heroRelativeAngle))
            return null;
        EnemyFrameProduction enemyFrameProduction = new EnemyFrameProduction();
        move(heroRelativeAngle, gameObjectsInfo.getHero(), gameObjectsInfo.getShells());
        if (weapons.get(currentWeaponNumber).getIsReadyToUseStatus())
            enemyFrameProduction.setShell(useWeapon(heroRelativeAngle));
        return enemyFrameProduction;
    }

    private boolean handleWalls(GameObjectsInfo gameObjectsInfo, double heroRelativeAngle) {
        for (int i = 0; i < gameObjectsInfo.getWallsNumber(); ++i) {
            if (true == twoLinePartsIntersect(gameObjectsInfo.getHero().getCoords(), this.getCoords(), heroRelativeAngle,
                    gameObjectsInfo.getWallStartPoint(i), gameObjectsInfo.getWallEndPoint(i),
                    gameObjectsInfo.getWallAngle(i))) {
                double distBetweenWallAndEnemy = getDistanceBetweenWallAndGameObject(gameObjectsInfo.getWall(i),
                        this);
                if (distBetweenWallAndEnemy > size) {
                    move(heroRelativeAngle);
                    return true;
                }
                move(getSquaredRelativeDistance(this.getCoords(), gameObjectsInfo.getWallEndPoint(i)) <
                        getSquaredRelativeDistance(this.getCoords(), gameObjectsInfo.getWallStartPoint(i))
                        ? gameObjectsInfo.getWallAngle(i) : gameObjectsInfo.getWallAngle(i) + Math.PI);
                return true;
            }
        }
        return false;
    }

    private void move(double heroRelativeAngle, Hero hero, List<Shell> shells) {
        movedOnFrame = false;
        dodge(shells);
        if (true == movedOnFrame)
            return;
        saveDistance(heroRelativeAngle, hero);
    }

    private void dodge(List<Shell> shells) {
        double dodgeAngle;
        for (Shell shell : shells) {
            double shellRelativeAngle = getRelativeAngle(this.getCoords(), shell.getCoords());
            if (shell.getAngle() * shellRelativeAngle <= 0 && Math.pow(Math.sin(shell.getAngle() - shellRelativeAngle), 2) < Math.pow(getRadius() +
                    shell.getRadius(), 2) / (Math.pow(getX() - shell.getX(), 2) + Math.pow(getY() -
                    shell.getY(), 2))) {
                if (Math.sin(shell.getAngle() - shellRelativeAngle) < 0)
                    dodgeAngle = -Math.PI / 2;
                else
                    dodgeAngle = Math.PI / 2;
                move(shell.getAngle() + dodgeAngle);
                movedOnFrame = true;
                return;
            }
        }
    }

    private void saveDistance(double heroRelativeAngle, Hero hero) {
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


}
