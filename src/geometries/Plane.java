package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Class Plane that implements the interface Geometry and contains a starting point and direction vector
 */
public class Plane  implements Geometry {
    private Point p0;
    private Vector normal;

    /**
     * Returns the Point
     * @return
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Returns the Normal to point
     * @return
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * A constructor that receives the point and the vector
     * @param point of the plane
     * @param vector vector normal to the plane
     */
    public Plane(Point point, Vector vector) {
        this.p0 = point;
        this.normal = vector.normalize();
    }

    /**
     * A constructor that gets three points and calculates the vector
     * @param point1 First point
     * @param point2 Second point
     * @param point3 Third point
     */
    public Plane(Point point1,Point point2,Point point3) {
        try {
            Vector v=point1.subtract(point2);
            Vector u =point3.subtract(point1);

            Vector n=u.crossProduct(v);
            normal= n.normalize();
            p0 = point1;
        }
        catch (IllegalArgumentException e){
            throw  new IllegalArgumentException("the tree points are in the same line or 2 points are equals");

        }

    }

    /**
     * Returns the Normal to plane
     * @param point on the plane
     * @return vector normal to the plane
     */
    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    /**
     * toString
     * @return
     */
    @Override
    public String toString() {
        return "Plane{" +
                "p0=" + p0 +
                ", normal=" + normal +
                '}';
    }
}
