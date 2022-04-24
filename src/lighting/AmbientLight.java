package lighting;

import primitives.*;

public class AmbientLight {
    Color intensity;

    /**
     * default constructor
     */
    public AmbientLight() {
        intensity = Color.BLACK;

    }

    /**
     * the ctor creates the intensity by multiplying the given color with the given attenuation factor.
     * @param
     * @param
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        intensity = Ia.scale(Ka);
    }

    /**
     * get for intensity
     * @return
     */
    public Color getIntensity() {
        return intensity;
    }

}
