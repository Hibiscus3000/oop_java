package ru.nsu.fit.oop.game.model;

import ru.nsu.fit.oop.game.model.entity.game_object.GameObject;
import ru.nsu.fit.oop.game.model.entity.game_object.wall.WallPart;

import java.awt.geom.Point2D;

public class Geometry {

    public static boolean inWallPartRectangle(WallPart wallPart, GameObject gameObject) {
        if (gameObject.getY() + gameObject.getRadius() < wallPart.getStartPoint().getY())
            return false;
        if (gameObject.getY() - gameObject.getRadius() > wallPart.getEndPoint().getY())
            return false;
        if (wallPart.getIsLowerPart() && gameObject.getX() > Math.max(wallPart.getStartPoint().getX(),
                wallPart.getEndPoint().getX()))
            return false;
        if (wallPart.getIsLowerPart() && gameObject.getX() < Math.min(wallPart.getStartPoint().getX(),
                wallPart.getEndPoint().getX()))
            return false;
        System.out.println("true");
        return true;
    }

    public static double getDistanceBetweenWallAndGameObjectCenter(WallPart wallPart, GameObject gameObject) {
        double xMax = (wallPart.getEndPoint().getX() > wallPart.getX())
                ? wallPart.getEndPoint().getX() : wallPart.getStartPoint().getX();
        double xMin = (wallPart.getEndPoint().getX() > wallPart.getX())
                ? wallPart.getX() : wallPart.getEndPoint().getX();
        double distBetweenWallAndObj = (wallPart.getAngle() > Math.PI / 2) ?
                Math.abs((xMax - gameObject.getX()) * Math.sin(wallPart.getAngle()) -
                        (-gameObject.getY() + wallPart.getY()) * Math.cos(wallPart.getAngle())) :
                Math.abs((gameObject.getX() - xMin) * Math.sin(wallPart.getAngle()) -
                        (gameObject.getY() - wallPart.getY()) * Math.cos(wallPart.getAngle()));
        return distBetweenWallAndObj;
    }

    public static double getSquaredRelativeDistance(Point2D.Double p1, Point2D.Double p2) {
        return (Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }

    public static double getRelativeDistance(Point2D.Double p1, Point2D.Double p2) {
        return Math.sqrt(getSquaredRelativeDistance(p1,p2));
    }

    public static double getRelativeAngle(Point2D.Double p, Point2D.Double pr) {
        double x = pr.getX() - p.getX();
        double y = pr.getY() - p.getY();
        double angle;
        if (0 == x) {
            if (y > 0)
                angle = Math.PI / 2;
            else
                angle = -Math.PI / 2;
        } else if (x > 0) {
            angle = Math.atan(y / x);
        } else if (y >= 0) {
            angle = Math.PI + Math.atan(y / x);
        } else {
            angle = -Math.PI + Math.atan(y / x);
        }
        return angle;
    }

    public static boolean LinePartAndCircleIntersect(Point2D.Double startPoint, Point2D.Double endPoint, double angle,
                                                     Point2D.Double circleCenter, double R) {

        if (circleCenter.getX() - R > Math.max(startPoint.getX(), endPoint.getX()) || circleCenter.getX() + R <
                Math.min(startPoint.getX(), endPoint.getX()) || circleCenter.getY() - R > endPoint.getY() ||
        circleCenter.getY() + R < startPoint.getY()) {
            return false;
        }

        if (startPoint.getX() == endPoint.getX()) {
            double a = 1;
            double b = -2 * circleCenter.getY();
            double c = startPoint.getX() * startPoint.getX() - 2 * startPoint.getX() * circleCenter.getX()
            + circleCenter.getX() * circleCenter.getX() + circleCenter.getY() * circleCenter.getY() - R*R;

            double D = b * b - 4 * a * c;
            if (D < 0)
                return false;
            double y1 = (-b - Math.sqrt(D)) / (2 * a);
            double y2 = (-b + Math.sqrt(D)) / (2 * a);
            if (y1 > Math.max(startPoint.getY(), endPoint.getY()) ||
                    y2 < Math.min(startPoint.getY(), endPoint.getY()))
                return false;
            else
                return true;
        }

        double A = Math.tan(angle);
        double B = endPoint.getY() - A * endPoint.getX();

        double a = (A * A + 1);
        double b = 2 * (A * B - circleCenter.getY() * A - circleCenter.getX());
        double c = circleCenter.getX() * circleCenter.getX() + B * B + circleCenter.getY() * circleCenter.getY() - 2 * B * circleCenter.getY()
                - R * R;

        double D = b * b - 4 * a * c;
        if (D < 0)
            return false;
        double x1 = (-b - Math.sqrt(D)) / (2 * a);
        double x2 = (-b + Math.sqrt(D)) / (2 * a);

        if (x1 > Math.max(startPoint.getX(), endPoint.getX()) || x2 < Math.min(startPoint.getX(), endPoint.getX()))
            return false;
        else
            return true;
    }

    public static boolean twoLinePartsIntersect(Point2D.Double p1s, Point2D.Double p1e, double angle1,
                                                Point2D.Double p2s, Point2D.Double p2e, double angle2) {
        if (p1e.getX() < p1s.getX()) {
            Point2D.Double tmp = p1s;
            p1s = p1e;
            p1e = tmp;
        }
        if (p2e.getX() < p2s.getX()) {
            Point2D.Double tmp = p2s;
            p2s = p2e;
            p2e = tmp;
        }
        if (p2e.getX() < p1s.getX() || (p1e.getX() < p2s.getX()))
            return false;
        if (0 == p1s.getX() - p1e.getX() && 0 == p2s.getX() - p2e.getX()) {
            if (p1s.getX() == p2s.getX() && !(Math.min(p1s.getY(), p1e.getY()) >
                    Math.max(p2s.getY(), p2e.getY()) || Math.min(p2s.getY(), p2e.getY()) >
                    Math.max(p1s.getY(), p1e.getY()))) {
                return true;
            } else {
                return false;
            }
        }

        if (0 == p1s.getX() - p1e.getX()) {
            double XIntersect = p1s.getX();
            double a2 = Math.tan(angle2);
            double b2 = p2s.getY() - a2 * p2s.getX();
            double YIntersect = a2 * XIntersect + b2;
            if (XIntersect >= p2s.getX() && XIntersect <= p2e.getX() && YIntersect >= Math.min(p1s.getY(),
                    p1e.getY()) && YIntersect <= Math.max(p1s.getY(), p1e.getY())) {
                return true;
            } else {
                return false;
            }
        }

        if (0 == p2s.getX() - p2e.getX()) {
            double XIntersect = p2s.getX();
            double a1 = Math.tan(angle1);
            double b1 = p1s.getY() - a1 * p1s.getX();
            double YIntersect = a1 * XIntersect + b1;
            if (XIntersect >= p1s.getX() && XIntersect <= p1e.getX() && YIntersect >= Math.min(p2s.getY(),
                    p2e.getY()) && YIntersect <= Math.max(p2s.getY(), p2e.getY())) {
                return true;
            } else {
                return false;
            }
        }

        double a1 = Math.tan(angle1);
        double a2 = Math.tan(angle2);
        double b1 = p1s.getY() - a1 * p1s.getX();
        double b2 = p2s.getY() - a2 * p2s.getX();
        double XIntersect = (b2 - b1) / (a1 - a2);

        if (a1 == a2)
            return false;

        if (XIntersect >= Math.max(p1s.getX(), p2s.getX()) && XIntersect <= Math.min(p1e.getX(), p2e.getX()))
            return true;
        else
            return false;
    }
}
