package primitives;
import primitives.*;

public class Point {

    protected Double3 xyz;

    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     *
     * @param other second vector
     * @return new vector of
     */
    public Vector subtract(Point other) {
        return new Vector(this.xyz.subtract(other.xyz));
    }

    public Point add(primitives.Vector vector) {
        return new Point(this.xyz.add(vector.xyz));
    }

    public double distanceSquared(Point other) {
        Double3 temp = this.xyz.subtract(other.xyz);
        return temp.d1 * temp.d1 + temp.d2 * temp.d2 + temp.d3 * temp.d3;
    }

    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point)) return false;
        Point other = (Point) obj;
        return this.xyz.equals(other.xyz);

    }


    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz +
                '}';
    }
}
