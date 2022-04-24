package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;

    public Scene(String name) {
        this.name = name;
        geometries=new Geometries();
    }



    /**
     * set the Background color for the scene
     * @return the scene object
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }


    /**
     * set the geometry model - a list of geometries
     * @return the scene object
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * set the Ambient Light for the scene
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
