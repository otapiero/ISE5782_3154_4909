package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The type Point light.
 */
public class PointLight extends Light implements LightSource {
    /**
     * The Position.
     */
    Point position;
    /**
     * The Kc.
     */
    double kC=1,
    /**
     * The Kl.
     */
    kL=0,
    /**
     * The Kq.
     */
    kQ=0;

    /**
     * Instantiates a new Point light.
     *
     * @param intensity the intensity
     * @param position  the position
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Sets c.
     *
     * @param kC the kc
     * @return the c
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }


    /**
     * Sets l.
     *
     * @param kL the kl
     * @return the l
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets q.
     *
     * @param kQ the kq
     * @return the q
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double distance = p.distance(position);
        return getIntensity().reduce(kC+distance*kL+distance*distance*kQ);
    }

    @Override
    public Vector getL(Point p) {
        return  p.subtract(position).normalize();
    }
}
