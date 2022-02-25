package geometries;

import primitives.Ray;

public class Cylinder extends Tube {

    private   double height;

    public double getHeight() {
        return height;
    }

    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }
}
