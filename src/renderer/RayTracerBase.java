package renderer;

import primitives.*;
import scene.Scene;

public abstract class RayTracerBase {
    protected Scene scene;

    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * the abstract method traces the ray to the point it hits in the scene,
     * @param ray - the traced ray
     * @return - the discovered color
     */
    public abstract Color traceRay(Ray ray);
}
