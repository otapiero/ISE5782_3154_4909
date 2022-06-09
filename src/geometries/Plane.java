package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class Plane that implements the interface Geometry and contains a starting point and direction vector
 */
public class Plane  extends Geometry {
    private Point p0;
    private Vector normal;

    /**
     * Returns the Point
     *
     * @return p 0
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Returns the Normal to point
     *
     * @return normal
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * A constructor that receives the point and the vector
     *
     * @param point  of the plane
     * @param vector vector normal to the plane
     */
    public Plane(Point point, Vector vector) {
        this.p0 = point;
        this.normal = vector.normalize();
    }

    /**
     * A constructor that gets three points and calculates the vector
     *
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

    /**
     *
     * @param ray for finding intersections with plan
     * @return list of point that intersect with the plane
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        if(getP0().equals(ray.getP0())) return null;

        double Td = getNormal().dotProduct(getP0().subtract(ray.getP0())); // מונה
        double Tn = getNormal().dotProduct(ray.getDir()); // מכנה
        if (isZero(Td) || isZero(Tn)) return null;
        double t = Td/Tn;

        if (t < 0)return null;
        if (alignZero(ray.getPoint(t).distance(ray.getPoint(t))) - maxDistance > 0)
            return null;
        return List.of(new GeoPoint(this,ray.getPoint(t)));

    }
}













