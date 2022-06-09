package renderer;
import geometries.Intersectable.GeoPoint;
import lighting.*;
import primitives.*;
import primitives.Vector;
import scene.*;

import java.util.*;

import static java.awt.Color.BLACK;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The type Ray tracer basic.
 */
public class RayTracerBasic extends RayTracerBase {


    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    private double distanceGlossinessAndReflectionGrid = 50;
    private double sizeOfGrid=4;
    private int glossinessAndReflectionRaysNum = 36;


    /**
     * ctor - initializing the scene parameter
     *
     * @param scene the scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     *  calculat the transparency of the geometry object
     * @param gp geoPoint of the geometry object
     * @param l  light direction
     * @param n normal of the geometry object
     * @param lightSource light source
     * @return the transparency
     */

    private Double3 transparency(GeoPoint gp, Vector l, Vector n, LightSource lightSource)
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


    /**
     *  check if the point is in the shadow
     * @param gp geoPoint of the geometry object
     * @param l light direction
     * @param n normal of the geometry object
     * @param lightSource light source
     * @return true if the point is not the shadow
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource)
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

    @Override
    protected Color superSamplingAdaptiveRecursive(Point centerP, double Width, double Height, double minWidth, double minHeight, Point cameraLoc, Vector Vright, Vector Vup, List<Point> prePoints) {

        if (Width < minWidth * 2 || Height < minHeight * 2) {
            return traceRay(List.of(new Ray(cameraLoc, centerP.subtract(cameraLoc))));
        }
        List<Point> nextCenterPList = new LinkedList<>();
        List<Point> cornersList = new LinkedList<>();
        List<primitives.Color> colorList = new LinkedList<>();
        Point tempCorner;
        Ray tempRay;
        for (int i = -1; i <= 1; i += 2){
            for (int j = -1; j <= 1; j += 2) {
                tempCorner = centerP.add(Vright.scale(i * Width / 2)).add(Vup.scale(j * Height / 2));
                cornersList.add(tempCorner);
                if (prePoints == null || !isInList(prePoints, tempCorner)) {
                    tempRay = new Ray(cameraLoc, tempCorner.subtract(cameraLoc));
                    nextCenterPList.add(centerP.add(Vright.scale(i * Width / 4)).add(Vup.scale(j * Height / 4)));
                    colorList.add(traceRay(List.of(tempRay)));
                }
            }
        }

        if (nextCenterPList == null || nextCenterPList.size() == 0) {
            return primitives.Color.BLACK;
        }

        boolean isAllEquals = true;
        primitives.Color tempColor = colorList.get(0);
        for (primitives.Color color : colorList) {
            if (!tempColor.isAlmostEquals(color))
                isAllEquals = false;
        }
        if (isAllEquals && colorList.size() > 1)
            return tempColor;

        tempColor = primitives.Color.BLACK;
        for (Point center : nextCenterPList) {
            tempColor = tempColor.add(superSamplingAdaptiveRecursive(center, Width/2,  Height/2,  minWidth,  minHeight ,  cameraLoc, Vright, Vup, cornersList));
        }
        return tempColor.reduce(nextCenterPList.size());
    }

    /**
     *  check if the point is in the list
     * @param pointsList list of points
     * @param point point
     * @return true if the point is in the list
     */
    private boolean isInList(List<Point> pointsList, Point point) {
        for (Point tempPoint : pointsList) {
            if(point.equals(tempPoint))
                return true;
        }
        return false;
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

    /**
     *  calc the local effects
     * @param gp geoPoint of the geometry object
     * @param ray ray
     * @param k the k of the geometry object
     * @return color of the local effects
     */
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
            if (nl * nv > 0) {
                Double3 ktr = transparency(gp,l, n,lightSource);
                if ((! (ktr.product(k)).lowerThan(MIN_CALC_COLOR_K)) && (!(ktr.product(k)).equals(new Double3(MIN_CALC_COLOR_K)))) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, v)));
                }

            }
        }
        return color;
    }

    /**
     * calc rays for reflection effect
     * @param n the normal of the geometry object
     * @param geoPoint geoPoint of the geometry object
     * @param inRay the ray of the geometry object
     * @return the reflection color
     */
    private List<Ray> constructReflectedRays(Vector n, GeoPoint geoPoint, Ray inRay)
    {
        Vector v = inRay.getDir();
        double vn = v.dotProduct(n);
        Vector reflected = v.subtract(n.scale(2*v.dotProduct(n))).normalize();
        return raysGrid( new Ray(geoPoint.point,reflected,n),1,geoPoint.geometry.getMaterial().getDiffusedAndGlossy(), n);
    }

    /**
     *  calc rays for refraction effect
     * @param n the normal of the geometry object
     * @param geoPoint geoPoint of the geometry object
     * @param inRay the ray of the geometry object
     * @return the refraction color
     */
    private List<Ray> constructRefractedRays(Vector n, GeoPoint geoPoint, Ray inRay)
    {
        return raysGrid(new Ray(geoPoint.point, inRay.getDir(), n),-1,geoPoint.geometry.getMaterial().getDiffusedAndGlossy(), n);
    }

    /**
     * calc closest point of the intersection between the ray and geometrys object
     * @param ray the ray
     * @return the closest point of the intersection
     */
    private GeoPoint findClosestIntersection(Ray ray)
    {
        List<GeoPoint> intersections= scene.geometries.findGeoIntersections(ray);
        if (intersections == null) return null;
        return ray.findClosestGeoPoint(intersections);
    }

    /**
     * calc the diffuse color
     * @param material the material of the geometry object
     * @param nl the dot product of the normal and the light vector
     * @return the diffuse color
     */
    private Double3  calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * calc the specular color
     * @param material the material of the geometry object
     * @param n the normal of the geometry object
     * @param l the light vector
     * @param v the view vector
     * @return the specular color
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, Vector v) {
        Vector r = calcVectorR(l, n);
        return material.kS.scale(Math.pow(Math.max(0, v.scale(-1).dotProduct(r)), material.nShininess));
    }

    /**
     *  calc the vector r
     * @param v the view vector
     * @param n the normal of the geometry object
     * @return the vector r
     */
    private Vector calcVectorR(Vector v, Vector n) {
        double a = v.dotProduct(n);
        Vector b = n.scale(a);
        return v.subtract(n.scale(2*v.dotProduct(n))).normalize();
    }

    /**
     * calc  color of a pixel by the ray recrusively
     * @param gp the geoPoint of the geometry object
     * @param ray   the ray
     * @param level the level of the ray
     * @param k the k of the geometry object
     * @return  the color of the pixel
     */

    private Color calcColor(GeoPoint gp,Ray ray,int level, Double3 k ) {
        Color color = calcLocalEffects(gp,ray,k);
        //Color color = calcLocalEffects(gp,ray,k).scale(Double3.ONE.subtract(gp.geometry.getMaterial().kT));
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * calc  color of a pixel by the ray
     * @param gp    the geoPoint of the geometry object
     * @param ray   the ray
     * @return  the global effects
     */
    private Color calcColor(GeoPoint gp, Ray ray)
    {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, new Double3(1d)).add(scene.ambientLight.getIntensity());
    }

    /**
     * calc the  global effects
     * @param gp    the geoPoint of the geometry object
     * @param ray   the ray
     * @param level the level of the recursion
     * @param k the k of the geometry object
     * @return the color of global effects
     */
    private Color calcGlobalEffects(GeoPoint gp,primitives.Ray ray, int level, Double3 k)
    {
        Color color = Color.BLACK;
        Double3 kr = gp.geometry.getMaterial().kR, kkr = k.product(kr);
        Color tempColor = new Color(BLACK);
        if (! kkr.lowerThan(MIN_CALC_COLOR_K))
        {
            List<Ray> reflectedRays = constructReflectedRays(gp.geometry.getNormal(gp.point), gp,ray );
            if (reflectedRays.size()!=0) {
                for (Ray reflectedRay : reflectedRays) {
                    GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
                        tempColor = tempColor.add(reflectedPoint == null ?primitives.Color.BLACK:
                                calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
                }
                color = color.add(tempColor.reduce(reflectedRays.size()));
            }
        }

        Double3 kt = gp.geometry.getMaterial().kT, kkt = k.product(kt);
        if (! kkt.lowerThan(MIN_CALC_COLOR_K)) {
            tempColor = new Color(BLACK);
            List<Ray> refractedRays = constructRefractedRays(gp.geometry.getNormal(gp.point), gp, ray);
            if (refractedRays.size() != 0) {
                for (Ray refractedRay : refractedRays) {
                    GeoPoint refractedPoint = findClosestIntersection(refractedRay);
                    tempColor = tempColor.add(refractedPoint == null ? primitives.Color.BLACK :
                            calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
                }
                color = color.add(tempColor.reduce(refractedRays.size()));
            }
        }


        return color;
    }

    /**
     * list of rays for a grid around the ray
     *
     * @param ray the ray
     * @param direction  the direction of the ray(reflection or refraction)
     * @param glossynessAndDiffuseness the glossyness and diffuseness
     * @param n  the normal of the geometry object
     * @return the list of rays
     */
    List<Ray> raysGrid(Ray ray, int direction, double glossynessAndDiffuseness, Vector n){
        int numOfRowCol = isZero(glossynessAndDiffuseness)? 1: (int)Math.ceil(Math.sqrt(glossinessAndReflectionRaysNum));
        if (numOfRowCol == 1) return List.of(ray);
        Vector Vup ;
        double Ax= Math.abs(ray.getDir().getX()), Ay= Math.abs(ray.getDir().getY()), Az= Math.abs(ray.getDir().getZ());
        if (Ax < Ay)
            Vup= Ax < Az ?  new Vector(0, -ray.getDir().getZ(), ray.getDir().getY()) :
                    new Vector(-ray.getDir().getY(), ray.getDir().getX(), 0);
        else
            Vup= Ay < Az ?  new Vector(ray.getDir().getZ(), 0, -ray.getDir().getX()) :
                    new Vector(-ray.getDir().getY(), ray.getDir().getX(), 0);
        Vector Vright = Vup.crossProduct(ray.getDir()).normalize();
        Point pc=ray.getPoint(distanceGlossinessAndReflectionGrid);
        double step = glossynessAndDiffuseness/sizeOfGrid;
        Point pij=pc.add(Vright.scale(numOfRowCol/2*-step)).add(Vup.scale(numOfRowCol/2*-step));
        Vector tempRayVector;
        Point tempPij;

        List<Ray> rays = new ArrayList<>();
        rays.add(ray);
        for (int i = 1; i < numOfRowCol; i++) {
            for (int j = 1; j < numOfRowCol; j++) {
                tempPij=pij.add(Vright.scale(i*step)).add(Vup.scale(j*step));
                tempRayVector =  tempPij.subtract(ray.getP0());
                if(n.dotProduct(tempRayVector) < 0 && direction == 1) //refraction
                    rays.add(new Ray(ray.getP0(), tempRayVector));
                if(n.dotProduct(tempRayVector) > 0 && direction == -1) //reflection
                    rays.add(new Ray(ray.getP0(), tempRayVector));
            }
        }

        return rays;
    }

}

