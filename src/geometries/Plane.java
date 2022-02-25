package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane  implements Geometry {
    private Point p0;
    private Vector normal;

    public Point getP0() {
        return p0;
    }

    public Vector getNormal() {
        return normal;
    }

    public Plane(Point point, Vector vector) {
        this.p0 = point;
        this.normal = vector.normalize();
    }

    public Plane(Point point1,Point point2,Point point3) {
        normal = null;
        p0 = point1;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }


    @Override
    public String toString() {
        return "Plane{" +
                "p0=" + p0 +
                ", normal=" + normal +
                '}';
    }
}
