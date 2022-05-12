package geometries.Eiphel;

import geometries.Geometries;
import geometries.Geometry;
import geometries.Polygon;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Cube.
 */
public class Cube extends Geometry {//private class please  dont look at it!!!!!!!!
    /**
     * The Polygons.
     */
    List<Polygon> polygons;

    /**
     * Instantiates a new Cube.
     *
     * @param p0 the p 0
     * @param p1 the p 1
     * @param p2 the p 2
     * @param p3 the p 3
     * @param p4 the p 4
     * @param p5 the p 5
     * @param p6 the p 6
     * @param p7 the p 7
     */
    public Cube(Point p0, Point p1, Point p2, Point p3, Point p4, Point p5, Point p6, Point p7) {
        polygons=new ArrayList<>(6) {};
        polygons.add( new Polygon(p0, p1, p2, p3));
        polygons.add( new Polygon(p4, p5, p6, p7));
        polygons. add( new Polygon(p0, p4, p7, p3));
        polygons.add( new Polygon(p1, p5, p6, p2));
        polygons.add( new Polygon(p2, p3, p7, p6));
        polygons.add(new Polygon(p0, p1, p5, p4));

    }


    @Override
    public Vector getNormal(Point point) {
        Vector normal = null;
        for (Polygon polygon : polygons) {
            normal = polygon.getNormal(point);
            if (normal != null) {
                break;
            }
        }
        return normal;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> tempResult= null ;
        for (var i: polygons) {
            List<GeoPoint> points=i.findGeoIntersectionsHelper(ray);
            if (points!=null){
                if (tempResult==null){
                    tempResult=new LinkedList();
                }
                tempResult.addAll(points);
            }
        }

        List<GeoPoint> result=null;
        if (tempResult!= null) {
            result = new LinkedList();
            for (var i : tempResult) {
                result.add(new GeoPoint(this, i.point));

            }
        }
        return result;

    }
}
