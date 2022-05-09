package lighting;

import primitives.*;

/**
 * The interface Light source.
 */
public interface LightSource {
    /**
     * Gets intensity.
     *
     * @param p the p
     * @return the intensity
     */
    public Color getIntensity(Point p);

    /**
     * Gets l.
     *
     * @param p the p
     * @return the l
     */
    public Vector getL(Point p);

    /**
     * getDistance - calculate the distance from light to the point
     * @param point the point we want to find the distance from
     * @return the distance from light to the point
     */
    public double getDistance(Point point);
}
