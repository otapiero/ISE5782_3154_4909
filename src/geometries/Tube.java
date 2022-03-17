package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static java.lang.System.out;

/**
 *Tube class that implements the interface Geometry
 */
public class Tube implements Geometry{

    /**
     * A constructor that receives the radius and ray
     * @param  axisRay The ray of the tube
     * @param radius The radius of the tube
     */
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    protected Ray axisRay;
    protected double radius;


    /**
     * Returns the ray
     * @return
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * Returns the radius
     * @return
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Returns the Normal to shape
     * @param point on the edge of the tube
     * @return vector of normal to the tube
     */
    @Override
    public Vector getNormal(Point point) {
        Vector pp0=point.subtract(axisRay.getP0());
        double t =axisRay.getDir().dotProduct(pp0);
        Point o=axisRay.getP0().add(axisRay.getDir().scale(t));
        pp0.crossProduct(axisRay.getDir());//test


        return point.subtract(o).normalize();
    }

    /**
     * toString
     * @return
     */
    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
