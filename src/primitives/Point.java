package primitives;
import primitives.*;

/**
 * The type Point.
 */
public class Point {

    /**
     * The constant ZERO.
     */
    public static final Point ZERO = new Point(0d, 0d, 0d);
    /**
     * The Xyz.
     */
    protected final Double3 xyz;

    /**
     * constructor of point by a given double3
     *
     * @param xyz a double3 of the coordinates
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * constructor of point by 3 given double
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     * sub the point by a point and creat a vector
     *
     * @param other a point
     * @return new vector result of this-other
     */
    public Vector subtract(Point other) {

        return new Vector(this.xyz.subtract(other.xyz));
    }

    /**
     * add a  vector to the point and creat a point
     *
     * @param vector a vector to add to the point
     * @return new point result of this+vector
     */
    public Point add(Vector vector) {
        return new Point(this.xyz.add(vector.xyz));
    }

    /**
     * find the distance squared between this point and an other point
     *
     * @param other second point
     * @return a double of distance squared between this and other
     */
    public double distanceSquared(Point other) {
        Double3 temp = this.xyz.subtract(other.xyz);
        return temp.d1 * temp.d1 + temp.d2 * temp.d2 + temp.d3 * temp.d3;
    }

    /**
     * find the distance  between this point and an other point
     *
     * @param other second point
     * @return a double of distance  between this and other
     */
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

    /**
     * Gets x.
     *
     * @return the x
     */
    public double getX() {
        return xyz.d1;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public double getY() {
        return xyz.d2;
    }

    /**
     * Gets z.
     *
     * @return the z
     */
    public double getZ() {
        return xyz.d3;
    }
}
