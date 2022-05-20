package renderer;
import geometries.Intersectable.GeoPoint;
import lighting.*;
import primitives.*;
import primitives.Vector;
import scene.*;

import java.util.*;

import static java.awt.Color.BLACK;
import static primitives.Util.alignZero;

/**
 * The type Ray tracer basic.
 */
public class RayTracerBasic extends RayTracerBase {


    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;


    /**
     * ctor - initializing the scene parameter
     *
     * @param scene the scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    private Double3 transparency(GeoPoint gp, Vector l, Vector n, double nv, LightSource lightSource)
    {
        Vector lightDirection = l.scale(-1);

        Ray lightRay = new Ray(gp.point, lightDirection,n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);;
        if (intersections == null )return Double3.ONE;

        Double3 ktr = Double3.ONE;
        double lightDistance = lightSource.getDistance(gp.point);
        for (GeoPoint g : intersections){
            if ( alignZero( g.point.distance(gp.point) - lightDistance) <= 0 )
                ktr = ktr.product(g.geometry.getMaterial().kT);
            if(ktr.lowerThan(MIN_CALC_COLOR_K )) return Double3.ZERO;
        }
        return ktr;

    }



    private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nv, LightSource lightSource)
    {

        Vector lightDirection = l.scale(-1);

        Ray lightRay = new Ray(gp.point, lightDirection,n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null  ) return true;

        double lightDistance = lightSource.getDistance(gp.point);
        for (GeoPoint g : intersections){
            if (g.geometry.getMaterial().kT == Double3.ZERO) {
                if (g.point.distance(gp.point) < lightDistance) return false;
            }
        }
        return true;
    }

    /**
     * implementation of super class trace ray method
     */
    @Override
    public Color traceRay(List<Ray> rays) {
        Color color=new Color(BLACK);
        for (Ray r : rays) {
            GeoPoint closeIntersections= findClosestIntersection(r);
            if (closeIntersections==null)
             color= color.add( scene.background);
            else {
             color= color.add(calcColor(closeIntersections,r));
            }

        }
        return color.reduce(rays.size());
    }

    /**
     * calc local effects
     * @param gp the geo point
     * @param ray
     * @return
     */
   /* private Color calcLocalEffects(GeoPoint gp, Ray ray) {
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
    }*/

    private Color calcLocalEffects(GeoPoint gp, Ray ray,Double3 k) {

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
                Double3 ktr = transparency(gp,l, n, nv,lightSource);
                if ((! (ktr.product(k)).lowerThan(MIN_CALC_COLOR_K)) && (!(ktr.product(k)).equals(new Double3(MIN_CALC_COLOR_K)))) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }

            }
        }
        return color;
    }

    private Ray constructReflectedRay(Vector n, Point geoPoint, Ray inRay)
    {
        Vector v = inRay.getDir();
        double vn = v.dotProduct(n);
        Vector reflected = v.subtract(n.scale(2*v.dotProduct(n))).normalize();
        return new Ray(geoPoint, reflected, n);
    }
    private Ray constructRefractedRay(Vector n, Point geoPoint, Ray inRay)
    {
        return new Ray(geoPoint, inRay.getDir(), n);
    }
    private GeoPoint findClosestIntersection(Ray ray)
    {
        List<GeoPoint> intersections= scene.geometries.findGeoIntersections(ray);
        if (intersections == null) return null;
        return ray.findClosestGeoPoint(intersections);
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

    private Color calcColor(GeoPoint gp,Ray ray,int level, Double3 k ) {
        Color color = calcLocalEffects(gp,ray,k);
        //Color color = calcLocalEffects(gp,ray,k).scale(Double3.ONE.subtract(gp.geometry.getMaterial().kT));
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    private Color calcColor(GeoPoint gp, Ray ray)
    {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, new Double3(1d)).add(scene.ambientLight.getIntensity());
    }

    private Color calcGlobalEffects(GeoPoint gp,primitives.Ray ray, int level, Double3 k)
    {
        Color color = Color.BLACK;
        Double3 kr = gp.geometry.getMaterial().kR, kkr = k.product(kr);
        if (! kkr.lowerThan(MIN_CALC_COLOR_K))
        {
            Ray reflectedRay = constructReflectedRay(gp.geometry.getNormal(gp.point), gp.point,ray );
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if(reflectedPoint != null)color = color.add(calcColor(reflectedPoint, reflectedRay, level-1, kkr).scale(kr));

        }

        Double3 kt = gp.geometry.getMaterial().kT, kkt = k.product(kt);
        if (! kkt.lowerThan(MIN_CALC_COLOR_K))
        {
            Ray refractedRay = constructRefractedRay(gp.geometry.getNormal(gp.point),gp.point, ray);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if(refractedPoint != null)color = color.add(calcColor(refractedPoint, refractedRay, level-1,kkt).scale(kt));

        }

        return color;
    }

}
