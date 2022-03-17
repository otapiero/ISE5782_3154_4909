package geometries;
import static primitives.Util.*;
import primitives.Point;
import primitives.Ray;

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
        return super.findIntersections(ray);
    }
}
