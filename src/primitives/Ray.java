package primitives;
import geometries.Intersectable.GeoPoint;
import java.util.List;
import java.util.Objects;

/**
 * The type Ray.
 */
public class Ray {
    /**
     * Gets p 0.
     *
     * @return the p 0
     */
    private static final double DELTA = 0.1;

    public Point getP0() {
        return p0;
    }

    /**
     * Gets dir.
     *
     * @return the dir
     */
    public Vector getDir() {
        return dir;
    }

    private final Point p0;
    private final Vector dir;

    /**
     * constractor of a ray
     *
     * @param p0  point of the ray
     * @param dir direction of the ray by a vector
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }
    public Ray(Point point, Vector vector, Vector n)
    {
        Vector delta = n.scale(n.dotProduct(vector) > 0 ? DELTA : -DELTA);
        this.p0 = point.add(delta);
        this.dir = vector;
    }


    /**
     * Gets point.
     *
     * @param t the t
     * @return the point
     */
    public Point getPoint(double t) {
        return getP0().add(getDir().scale(t));
    }

    /**
     * Find closest point point.
     *
     * @param points the points
     * @return the point
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }


    /**
     * Find closest geo point geo point.
     *
     * @param geoPoints the geo points
     * @return the geo point
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {
        GeoPoint closestPoint = null;
        double minDistance = Double.MAX_VALUE;
        for (GeoPoint geoPoint : geoPoints) {
            double distance = geoPoint.point.distanceSquared(p0);
            if (distance < minDistance) {
                closestPoint = geoPoint;
                minDistance = distance;
            }
        }
        return closestPoint;
    }


    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Ray)) return false;
        Ray ray = (Ray) obj;
        return this.p0.equals(ray.p0) && this.dir.equals( ray.dir);
    }

}
