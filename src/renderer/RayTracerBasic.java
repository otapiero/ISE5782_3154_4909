package renderer;
import geometries.Intersectable.GeoPoint;
import lighting.*;
import primitives.*;
import primitives.Vector;
import scene.*;

import java.util.*;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase {

    private static final double DELTA = 0.1;

    /**
     * ctor - initializing the scene parameter
     * @param
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }
    Color calcColor(GeoPoint GeoPoint,Ray ray) {
        return scene.ambientLight.getIntensity().add(calcLocalEffects(GeoPoint,ray));
    }

    private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nv, LightSource lightSource)
    {

        Vector lightDirection = l.scale(-1);
        Vector deltaVector = n.scale(nv < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(deltaVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null  ) return true;

        double lightDistance = lightSource.getDistance(gp.point);
        for (GeoPoint g : intersections){
            if ( g.point.distance(gp.point) < lightDistance ) return false;
        }
        return true;
    }

    /**
     * implementation of super class trace ray method
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections= scene.geometries.findGeoIntersections(ray);
        if (intersections==null)
            return scene.background;
        return calcColor(ray.findClosestGeoPoint(intersections),ray);
    }
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir ();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                if(unshaded(gp,l,n,nv,lightSource))
                {
                    Color iL = lightSource.getIntensity(gp.point);
                    color = color.add(iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }

            }
        }
        return color;
    }
    private Double3  calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = calcVectorR(l, n);
        return material.kS.scale(Math.pow(Math.max(0, v.scale(-1).dotProduct(r)), material.nShininess));
    }
    private Vector calcVectorR(Vector v, Vector n) {
        double a = v.dotProduct(n);
        Vector b = n.scale(a);
        Vector c = v.subtract(b);
        return v.subtract(n.scale(2*v.dotProduct(n))).normalize();
    }


}
