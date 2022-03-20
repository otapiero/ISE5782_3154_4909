package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;
import static primitives.Util.alignZero;

/**
 * Class Sphere that implements the interface Geometry and contains a center point and a radius
 */
public class Sphere implements Geometry {
    private Point center;
    private double radius;

    /**
     * A constructor that gets a point and a radius
     *
     * @param center a center point
     * @param radius a radius
     */
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * Returns the center point
     *
     * @return
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Returns the radius
     *
     * @return
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Returns the Normal to Sphere
     *
     * @param point on the edge of the sphere
     * @return vector Normal to the Sphere
     */
    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }

    /**
     * toString
     *
     * @return
     */
    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }
    /**
     * @param ray for finding intersections with sphere
     * @return list of point that intersect with the sphere
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        ;
        if(getCenter().equals(ray.getP0())){
            return List.of(ray.getPoint(getRadius()));
        }
        Vector u = getCenter().subtract(ray.getP0());
        double Tm = alignZero(ray.getDir().dotProduct(u));
        double d = alignZero(Math.sqrt(u.lengthSquared() - Math.pow(Tm, 2)));
        if (d >= getRadius()) {
            return null;
        }
        double Th = alignZero(Math.sqrt(Math.pow(getRadius(), 2) - Math.pow(d, 2)));
        double T1 = alignZero(Tm + Th);
        double T2 = alignZero(Tm - Th);
        if (T1 <= 0 && T2 <=0) {
            return null;
        }

        if (T1 > 0 && T2 > 0) {
            return List.of(ray.getPoint(T1), ray.getPoint(T2));
        } else if (T1 > 0) {
            return List.of(ray.getPoint(T1));
        } else
            return List.of(ray.getPoint(T2));
    }

}
