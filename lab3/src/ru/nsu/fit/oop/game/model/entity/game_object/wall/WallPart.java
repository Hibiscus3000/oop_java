package ru.nsu.fit.oop.game.model.entity.game_object.wall;

import ru.nsu.fit.oop.game.model.entity.game_object.GameObject;
import ru.nsu.fit.oop.game.model.entity.weapon.Damage;

import java.awt.geom.Point2D;

public class WallPart extends GameObject {

    private final int parentWallNumber;
    private final double endX;
    private final double endY;
    private final double normalAngle;
    private int absorbedDamage;

    public WallPart(int parentWallNumber,double startX, double startY, double endX, double endY) {
        super("wall", 0, 0);
        this.parentWallNumber = parentWallNumber;
        this.setCoords((startY > endY) ? endX : startX, (startY > endY) ? endY : startY);
        this.endX = (startY > endY) ? startX : endX;
        this.endY = (startY > endY) ? startY : endY;
        double x = this.endX - getX();
        double y = this.endY - getY();
        if (0 == x) {
            angle = Math.PI / 2;
        }
        else if (x > 0) {
            angle = Math.atan(y / x);
        } else if (y > 0) {
            angle = Math.PI + Math.atan(y / x);
        } else
            angle = 0;
        normalAngle = angle - Math.PI / 2;
    }

    public Point2D.Double getStartPoint() {
        return new Point2D.Double(getX(), getY());
    }

    public Point2D.Double getEndPoint() {
        return new Point2D.Double(endX, endY);
    }

    public double getNormalAngle() {
        return normalAngle;
    }

    public int getParentWallNumber() {
        return parentWallNumber;
    }

    @Override
    public void takeDamage(Damage damage,double angle) {
        absorbedDamage += damage.getArmorDamage();
    }

    public int getAbsorbedDamage() {
        return absorbedDamage;
    }

    public void setAbsorbedDamageZero() {
        absorbedDamage = 0;
    }
}
