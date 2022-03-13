package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * A class class that inherits from the Tube class and contains the height of the cylinder
 */
public class Cylinder extends Tube {

    private   double height;

    /**
     * Returns the height of the cylinder
     * @return
     */
    public double getHeight() {
        return height;
    }


    @Override
    public Vector getNormal(Point point) {
        Point p0 = axisRay.getP0();
        Vector N = axisRay.getDir();

        // find the vector on the lower base
        Vector pp0 = point.subtract(p0);
        if(pp0.dotProduct(N) == 0){ // the vectors is orthogonal to each other
            if(pp0.length() <= radius){ // the point is on the base of the cylinder
                return N;
            }
        }

        // find the vector on the upper base
        Vector vN = N.scale(N.dotProduct(pp0));
        if(pp0.equals(vN)){
            return N;
        }
        else {
            Vector pp0_vN = pp0.subtract(vN);
            return pp0_vN.length() == radius ? pp0_vN.normalize() : N;
        }
    }


    /**
     * A constructor that receives the Ray, radius and height of the cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }



    /**
     * toString
     * @return
     */
    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }
}
