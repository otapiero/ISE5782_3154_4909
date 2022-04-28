package geometries;

import primitives.*;

import java.util.*;

/**
 * The type Intersectable.
 */
public abstract class Intersectable {
    /**
     * Find intersections list.
     *
     * @param ray the ray
     * @return the list
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }


    /**
     * Find geo intersections list.
     *
     * @param ray the ray
     * @return the list
     */
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * Find geo intersections helper list.
     *
     * @param ray the ray
     * @return the list
     */
    protected abstract   List<GeoPoint> findGeoIntersectionsHelper(Ray ray) ;

    /**
     * The type Geo point.
     */
    public static class GeoPoint {
        /**
         * The Geometry.
         */
        public  Geometry geometry;
        /**
         * The Point.
         */
        public  Point point;

        /**
         * Instantiates a new Geo point.
         *
         * @param geometry the geometry
         * @param point    the point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (!(obj instanceof GeoPoint)) return false;
            GeoPoint geoPoint = (GeoPoint) obj;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

}
