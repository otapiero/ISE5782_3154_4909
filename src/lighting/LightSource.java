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
}
