package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;


/**
 * The type Geometry.
 */
public abstract class  Geometry extends Intersectable {
    /**
     * Gets emission.
     *
     * @return the emission
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets emission.
     *
     * @param emission the emission
     * @return the emission
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * The Emission.
     */
    protected Color emission=Color.BLACK;
     Material material= new Material();

    public Material getMaterial() {
        return material;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Gets normal.
     *
     * @param point the point
     * @return the normal
     */
    public abstract Vector getNormal(Point point);
}
