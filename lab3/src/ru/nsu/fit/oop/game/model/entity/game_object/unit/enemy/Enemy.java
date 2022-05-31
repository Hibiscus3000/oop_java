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
                    double aggressionRange, int lives, int health, int armor, int shield, String weaponName,
            int healthRegenMillis, int armorRegenMillis,int shieldRegenMillis, int healthRegen,int armorRegen,
                    int shieldRegen)
            throws FactoryException {
        super(name, radius, speed, lives, health, armor, shield,healthRegenMillis, armorRegenMillis, shieldRegenMillis,
                healthRegen, armorRegen, shieldRegen);
        squaredAggressionRange = aggressionRange * aggressionRange;
        this.preferredDistance = preferredDistance;
        defaultPreferredDistance = preferredDistance;
        this.saveDistance = saveDistance;
        weapons.add((Weapon) WeaponFactory.getInstance().getEntity(weaponName));
    }

    public EnemyFrameProduction enemyFrameTurn(GameObjectsInfo gameObjectsInfo) throws UnableToUseWeaponException {
        if (getSquaredRelativeDistance(this.getCoords(), gameObjectsInfo.getHero().getCoords()) >
                squaredAggressionRange)
            return null;
        movedOnFrame = false;
        double heroRelativeAngle = getRelativeAngle(this.getCoords(), gameObjectsInfo.getHero().getCoords());
        dodge(gameObjectsInfo.getShells());
        if (!movedOnFrame) {
            if (handleWalls(gameObjectsInfo, heroRelativeAngle))
                return null;
            if (!(movedOnFrame = handleEnemies(gameObjectsInfo)))
                saveDistance(heroRelativeAngle, gameObjectsInfo.getHero(), gameObjectsInfo.getShells());
        }
        EnemyFrameProduction enemyFrameProduction = new EnemyFrameProduction();
        if (weapons.get(currentWeaponNumber).getIsReadyToUseStatus())
            enemyFrameProduction.setShells(useWeapon(heroRelativeAngle));
        return enemyFrameProduction;
    }

    private boolean handleEnemies(GameObjectsInfo gameObjectsInfo) {
        for (Enemy enemy : gameObjectsInfo.getEnemies())
            if ((enemy != this) && (getSquaredRelativeDistance(getCoords(), enemy.getCoords()) < 10000)) {
                move(getRelativeAngle(getCoords(), enemy.getCoords()) + Math.PI);
                handleAfterMovementCollisionsWithShells(gameObjectsInfo.getShells(), -1);
                return true;
            }
        return false;
    }

    private boolean handleWalls(GameObjectsInfo gameObjectsInfo, double heroRelativeAngle) {
        for (int i = 0; i < gameObjectsInfo.getWallsNumber(); ++i) {
            for (int j = 0; j < 4; ++j) {
                /*if ((inWallPartRectangle(gameObjectsInfo.getWallPart(i, j), this)) && (twoLinePartsIntersect(gameObjectsInfo.getHero().getCoords(),
                        new Point2D.Double(x + radius * Math.cos(heroRelativeAngle), y + radius * Math.sin(heroRelativeAngle)), heroRelativeAngle,
                        gameObjectsInfo.getWallPartStartPoint(i, j), gameObjectsInfo.getWallPartEndPoint(i, j),
                        gameObjectsInfo.getWallPartAngle(i, j)) || twoLinePartsIntersect(gameObjectsInfo.getHero().getCoords(),
                        new Point2D.Double(x + radius * Math.cos(heroRelativeAngle + Math.PI), y + radius * Math.sin(heroRelativeAngle + Math.PI)),
                        heroRelativeAngle, gameObjectsInfo.getWallPartStartPoint(i, j), gameObjectsInfo.getWallPartEndPoint(i, j), gameObjectsInfo.getWallPartAngle(i, j))
                || twoLinePartsIntersect(gameObjectsInfo.getHero().getCoords(), getCoords(), heroRelativeAngle, gameObjectsInfo.getWallPartStartPoint(i, j),
                        gameObjectsInfo.getWallPartEndPoint(i, j), gameObjectsInfo.getWallPartAngle(i, j)))) {
                    System.out.println(j);
                    if (!twoLinePartsIntersect(this.getCoords(), new Point2D.Double(x + Math.cos(gameObjectsInfo.getWallPartNormalAngle(i, j) + Math.PI) * (size + speed + radius),
                                    y + Math.sin(gameObjectsInfo.getWallPartNormalAngle(i, j) + Math.PI) * (size + speed + radius)), gameObjectsInfo.getWallPartNormalAngle(i, j) + Math.PI,
                            gameObjectsInfo.getWallPartStartPoint(i, j), gameObjectsInfo.getWallPartEndPoint(i, j), gameObjectsInfo.getWallPartAngle(i, j))) {
                        move(gameObjectsInfo.getWallPartNormalAngle(i,j) + Math.PI);
                        return true;
                    } else if (twoLinePartsIntersect(this.getCoords(), new Point2D.Double(x + Math.cos(gameObjectsInfo.getWallPartNormalAngle(i, j) + Math.PI) *
                                    size, y + Math.sin(gameObjectsInfo.getWallPartNormalAngle(i, j) + Math.PI) * size), gameObjectsInfo.getWallPartNormalAngle(i, j) + Math.PI, gameObjectsInfo.getWallPartStartPoint(i, j),
                            gameObjectsInfo.getWallPartEndPoint(i, j), gameObjectsInfo.getWallPartAngle(i, j))) {
                        move(gameObjectsInfo.getWallPartNormalAngle(i,j));
                        return true;
                    }
                    move(getRelativeDistance(this.getCoords(), gameObjectsInfo.getWallPartEndPoint(i, j)) +
                            getRelativeDistance(gameObjectsInfo.getWallPartEndPoint(i, j), gameObjectsInfo.getHero().getCoords()) <
                            getRelativeDistance(this.getCoords(), gameObjectsInfo.getWallPartStartPoint(i, j)) +
                                    getRelativeDistance(gameObjectsInfo.getWallPartStartPoint(i, j), gameObjectsInfo.getHero().getCoords())
                            ? gameObjectsInfo.getWallPartAngle(i,j) : gameObjectsInfo.getWallPartAngle(i,j) + Math.PI);
                    handleAfterMovementCollisionsWithShells(gameObjectsInfo.getShells(), -1);
                    return true;
            }*/
                if (twoLinePartsIntersect(gameObjectsInfo.getHero().getCoords(), getCoords(), heroRelativeAngle, gameObjectsInfo.getWallPartStartPoint(i, j),
                        gameObjectsInfo.getWallPartEndPoint(i, j), gameObjectsInfo.getWallPartAngle(i, j)))
                    return true;
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
                squaredRelativeDistance = getSquaredRelativeDistance(shell.getCoords(), this.getCoords());
                if (squaredRelativeDistance < min || -1 == min) {
                    shellToDodge = shell;
                    min = squaredRelativeDistance;
                }
            }
        }
        if (null != shellToDodge) {
            double shellRelativeAngle = getRelativeAngle(this.getCoords(), shellToDodge.getCoords());
            //if (Math.sin(shellToDodge.getAngle() - shellRelativeAngle) < 0)
            dodgeAngle = -Math.PI / 2;
            //else
            //    dodgeAngle = Math.PI / 2;
            prevAngle = angle;
            move(shellToDodge.getAngle() + dodgeAngle);
            movedOnFrame = true;
            //handleAfterMovementCollisionsWithShells(shells,min);
        }
    }

    private void handleAfterMovementCollisionsWithShells(List<Shell> shells, double min) {
        double shellRelativeAngle, squaredRelativeDistance;
        for (Shell shell : shells) {
            shellRelativeAngle = getRelativeAngle(this.getCoords(), shell.getCoords());
            if (shell.getAngle() * shellRelativeAngle <= 0 && Math.pow(Math.sin(shell.getAngle() - shellRelativeAngle), 2)
                    < Math.pow(getRadius() + shell.getRadius(), 2) / (Math.pow(getX() - shell.getX(), 2) +
                    Math.pow(getY() - shell.getY(), 2))) {
                squaredRelativeDistance = getSquaredRelativeDistance(shell.getCoords(), this.getCoords());
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
        handleAfterMovementCollisionsWithShells(shells, -1);
    }
}
