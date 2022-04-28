package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The type Spot light.
 */
public class SpotLight extends PointLight{
    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity(p).scale(Math.max(0, direction.dotProduct(getL(p))));
    }


    /**
     * The Direction.
     */
    Vector direction;

    /**
     * Instantiates a new Spot light.
     *
     * @param intensity the intensity
     * @param position  the position
     * @param direction the direction
     */
    public SpotLight(Color intensity, Point position,  Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }
}
