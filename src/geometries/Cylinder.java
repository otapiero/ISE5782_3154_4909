package geometries;

import primitives.Ray;

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
