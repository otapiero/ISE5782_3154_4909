package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Interface with the function of returning the normal to shape
 */
public interface Geometry {
    public Vector getNormal(Point point);
}
