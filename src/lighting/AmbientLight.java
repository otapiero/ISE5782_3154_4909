package lighting;

import primitives.*;

/**
 * The type Ambient light.
 */
public class AmbientLight extends Light {

    /**
     * Instantiates a new Ambient light.
     */
    public AmbientLight() {
        super(Color.BLACK);
    }

    /**
     * Instantiates a new Ambient light.
     *
     * @param Ia the ia
     * @param Ka the ka
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }

}
