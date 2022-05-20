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

    protected double prevAngle;
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
        dodge(gameObjectsInfo.getShells());
        if (true != movedOnFrame) {
            if (true == handleWalls(gameObjectsInfo, heroRelativeAngle))
                return null;
            saveDistance(heroRelativeAngle,gameObjectsInfo.getHero(),gameObjectsInfo.getShells());
        }
        EnemyFrameProduction enemyFrameProduction = new EnemyFrameProduction();
        if (weapons.get(currentWeaponNumber).getIsReadyToUseStatus())
            enemyFrameProduction.setShells(useWeapon(heroRelativeAngle));
        return enemyFrameProduction;
    }

    private boolean handleWalls(GameObjectsInfo gameObjectsInfo, double heroRelativeAngle) {
        for (int i = 0; i < gameObjectsInfo.getWallsNumber(); ++i) {
            for (int j = 0; j < 4; ++j) {
                if (true == twoLinePartsIntersect(gameObjectsInfo.getHero().getCoords(), this.getCoords(), heroRelativeAngle,
                        gameObjectsInfo.getWallPartStartPoint(i, j), gameObjectsInfo.getWallPartEndPoint(i, j),
                        gameObjectsInfo.getWallAngle(i))) {
                    double distBetweenWallAndEnemy = getDistanceBetweenWallAndGameObjectCenter(gameObjectsInfo.getWallPart(i, j),
                            this);
                    if (distBetweenWallAndEnemy > size + speed + radius) {
                        move(heroRelativeAngle);
                        return true;
                    } else if (distBetweenWallAndEnemy < size) {
                        move(heroRelativeAngle + Math.PI);
                        return true;
                    }
                    move(getRelativeDistance(this.getCoords(), gameObjectsInfo.getWallPartEndPoint(i, j)) +
                            getRelativeDistance(gameObjectsInfo.getWallPartEndPoint(i, j), gameObjectsInfo.getHero().getCoords()) <
                            getRelativeDistance(this.getCoords(), gameObjectsInfo.getWallPartStartPoint(i, j)) +
                                    getRelativeDistance(gameObjectsInfo.getWallPartStartPoint(i, j), gameObjectsInfo.getHero().getCoords())
                            ? gameObjectsInfo.getWallAngle(i) : gameObjectsInfo.getWallAngle(i) + Math.PI);
                    handleAfterMovementCollisionsWithShells(gameObjectsInfo.getShells(),-1);
                    return true;
                }
            }
        }
        return false;
    }

    private void dodge(List<Shell> shells) {
        double dodgeAngle;
        double squaredRelativeDistance;
        double min = -1;
        Shell shellToDodge = null;
        for (Shell shell : shells) {
            double shellRelativeAngle = getRelativeAngle(this.getCoords(), shell.getCoords());
            if (shell.getAngle() * shellRelativeAngle <= 0 && Math.pow(Math.sin(shell.getAngle() - shellRelativeAngle), 2)
                    < Math.pow(getRadius() + shell.getRadius(), 2) / (Math.pow(getX() - shell.getX(), 2) +
                    Math.pow(getY() - shell.getY(), 2))) {
                squaredRelativeDistance = getSquaredRelativeDistance(shell.getCoords(),this.getCoords());
                if (squaredRelativeDistance < min || -1 == min) {
                    shellToDodge = shell;
                    min = squaredRelativeDistance;
                }
            }
        }
        if (null != shellToDodge) {
            double shellRelativeAngle = getRelativeAngle(this.getCoords(), shellToDodge.getCoords());
            if (Math.sin(shellToDodge.getAngle() - shellRelativeAngle) < 0)
                dodgeAngle = -Math.PI / 2;
            else
                dodgeAngle = Math.PI / 2;
            prevAngle = angle;
            move(shellToDodge.getAngle() + dodgeAngle);
            movedOnFrame = true;
            handleAfterMovementCollisionsWithShells(shells,min);
        }
    }

    private void handleAfterMovementCollisionsWithShells(List<Shell> shells, double min) {
        double shellRelativeAngle, squaredRelativeDistance;
        for (Shell shell : shells) {
            shellRelativeAngle = getRelativeAngle(this.getCoords(), shell.getCoords());
            if (shell.getAngle() * shellRelativeAngle <= 0 && Math.pow(Math.sin(shell.getAngle() - shellRelativeAngle), 2)
                    < Math.pow(getRadius() + shell.getRadius(), 2) / (Math.pow(getX() - shell.getX(), 2) +
                    Math.pow(getY() - shell.getY(), 2))) {
                squaredRelativeDistance = getSquaredRelativeDistance(shell.getCoords(),this.getCoords());
                if (squaredRelativeDistance < min || -1 == min) {
                    move(angle + Math.PI);
                    angle = prevAngle;
                    return;
                }
            }
        }
    }

    private void saveDistance(double heroRelativeAngle, Hero hero, List<Shell> shells) {
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
        handleAfterMovementCollisionsWithShells(shells,-1);
    }
}
