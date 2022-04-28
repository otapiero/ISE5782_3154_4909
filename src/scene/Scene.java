package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.*;


/**
 * The type Scene.
 */
public class Scene {
    /**
     * The Name.
     */
    public String name;
    /**
     * The Background.
     */
    public Color background=Color.BLACK;
    /**
     * The Ambient light.
     */
    public AmbientLight ambientLight = new AmbientLight();
    /**
     * The Geometries.
     */
    public Geometries geometries;
    /**
     * The Lights.
     */
    public List<LightSource> lights =new LinkedList<>();

    /**
     * Sets lights.
     *
     * @param lights the lights
     * @return the lights
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    /**
     * Instantiates a new Scene.
     *
     * @param name the name
     */
    public Scene(String name) {
        this.name = name;
        geometries=new Geometries();
    }


    /**
     * set the Background color for the scene
     *
     * @param background the background
     * @return the scene object
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }


    /**
     * set the geometry model - a list of geometries
     *
     * @param geometries the geometries
     * @return the scene object
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * set the Ambient Light for the scene
     *
     * @param ambientLight the ambient light
     * @return the scene object
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    @Override
    public String toString() {
        return "Scene{" +
                    "name='" + name + '\'' +
                ", background=" + background +
                ", ambientLight=" + ambientLight +
                ", geometries=" + geometries +
                '}';
    }
}
