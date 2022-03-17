package primitives;

import java.util.Objects;

public class Ray {
    public Point getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }

    private final Point p0;
    private final Vector dir;

    /**
     * constractor of a ray
     * @param p0 point of the ray
     * @param dir direction of the ray by a vector
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }
    public Point getPoint(double t) {
        return getP0().add(getDir().scale(t));
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
