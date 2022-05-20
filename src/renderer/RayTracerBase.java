package renderer;

import primitives.*;
import scene.Scene;

import java.util.List;

/**
 * The type Ray tracer base.
 */
public abstract class RayTracerBase {
    /**
     * The Scene.
     */
    protected Scene scene;

    /**
     * Instantiates a new Ray tracer base.
     *
     * @param scene the scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * the abstract method traces the ray to the point it hits in the scene,
     *
     * @param ray - the traced ray
     * @return - the discovered color
     */
    public abstract Color traceRay(List<Ray> ray);
}
