package lighting;

import primitives.Color;

/**
 * The type Light.
 */
abstract class Light {
    /**
     * Gets intensity.
     *
     * @return the intensity
     */
    public Color getIntensity() {
        return intensity;
    }

    private Color intensity;

    /**
     * Instantiates a new Light.
     *
     * @param intensity the intensity
     */
    protected Light(Color intensity){
        this.intensity = intensity;
    }

}
