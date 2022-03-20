package geometries;
import static primitives.Util.*;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 *A Triangle class that inherits from the Polygon class
 */
public class Triangle extends Polygon{

    /**
     *A constructor that receives three points per triangle and sends to
     * @param point1 First point
     * @param point2 Second point
     * @param point3 Third point
     */
    public Triangle(Point point1,Point point2,Point point3) {
        super(point1,point2,point3);
    }

    /**
     * toString
     * @return
     */
    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> result = plane.findIntersections(ray);
        if (result == null) {
            return null;
        }
        Point p0 =ray.getP0();
        Vector dir=ray.getDir();
        Vector v1 = this.vertices.get(0).subtract(p0),
                v2 = this.vertices.get(1).subtract(p0),
                v3 = this.vertices.get(2).subtract(p0);

        Vector n1 = v1.crossProduct(v2).normalize(),
                n2 = v2.crossProduct(v3).normalize(),
                n3 = v3.crossProduct(v1).normalize();

        double x1 = alignZero(dir.dotProduct(n1)),
                x2 = alignZero(dir.dotProduct(n2)),
                x3 = alignZero(dir.dotProduct(n3));

        if((x1 < 0 && x2 < 0 && x3 < 0) || (x1 > 0 && x2 > 0 && x3 > 0)) {
            return result;
        }
        return null;
    }
}
