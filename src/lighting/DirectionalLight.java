package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The type Directional light.
 */
public class DirectionalLight extends Light implements LightSource {
    /**
     * The Direction.
     */
    Vector direction;

    /**
     * Instantiates a new Directional light.
     *
     * @param intensity the intensity
     * @param direction the direction
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
