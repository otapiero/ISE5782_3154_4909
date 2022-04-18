package lighting;

import primitives.*;

public class AmbientLight {
    Color intensity;

    public AmbientLight() {
        intensity = Color.BLACK;

    }

    public AmbientLight(Color Ia, Double3 Ka) {
        intensity = Ia.scale(Ka);
    }

    public Color getIntensity() {
        return intensity;
    }

}
