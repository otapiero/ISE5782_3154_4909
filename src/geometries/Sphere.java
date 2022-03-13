package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Class Sphere that implements the interface Geometry and contains a center point and a radius
 */
public class Sphere implements Geometry{
    private Point center;
    private double radius;

    /**
     * A constructor that gets a point and a radius
     * @param center a center point
     * @param radius a radius
     */
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * Returns the center point
     * @return
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Returns the radius
     * @return
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Returns the Normal to Sphere
     * @param point on the edge of the sphere
     * @return vector Normal to the Sphere
     */
    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }

    /**
     * toString
     * @return
     */
    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }
}
