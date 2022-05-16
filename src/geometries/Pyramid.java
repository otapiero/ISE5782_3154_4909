package geometries;

import geometries.Cube;
import geometries.Geometry;
import geometries.Polygon;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Pyramid.
 */
public class Pyramid extends Geometry {//private class please  dont look at it!!!!!!!!
    /**
     * The Cubes.
     */
    List<geometries.Cube> cubes;
    /**
     * The Distance.
     */
    double distance;
    /**
     * The Relative height.
     */
    static final double RELATIVE_HEIGHT = 1/10d;
    /**
     * The Relative width.
     */
    static final double RELATIVE_WIDTH = 1/7d;
    /**
     * The Minimum distance.
     */
    static final double MINIMUM_DISTANCE = 1/10d;

    /**
     * Instantiates a new Pyramid.
     *
     * @param p0 the p 0
     * @param p1 the p 1
     * @param p2 the p 2
     * @param p3 the p 3
     */
    public Pyramid(Point p0, Point p1, Point p2, Point p3) {
        distance = p0.distance(p1);
        if(distance != p1.distance(p2)||distance != p2.distance(p3)||distance != p3.distance(p0)){
            throw new IllegalArgumentException("The points must be a square");
        }
        cubes = new LinkedList<>();
        recruisiveCubeGenerator(p0, p1, p2, p3, distance);
    }

    /**
     * Recruisive cube generator. create cubes one on the other in recrusive way
     *
     * @param p0       the p 0
     * @param p1       the p 1
     * @param p2       the p 2
     * @param p3       the p 3
     * @param distance the distance
     */
    void recruisiveCubeGenerator(Point p0, Point p1, Point p2, Point p3, double distance){
        if(cubes.size()==5){
            return;
        }
        Vector height = p3.subtract(p0).crossProduct(p1.subtract(p0)).normalize();// vector height of next cube
        Vector width = p2.subtract(p0).normalize();
        Vector width2 = p3.subtract(p1).normalize();
        Point p4,p5,p6,p7;
        p4 = p0.add(height.scale(RELATIVE_HEIGHT*this.distance));
        p5 = p1.add(height.scale(RELATIVE_HEIGHT*this.distance));
        p6 = p2.add(height.scale(RELATIVE_HEIGHT*this.distance));
        p7 = p3.add(height.scale(RELATIVE_HEIGHT*this.distance));
        cubes.add(new geometries.Cube(p0, p1, p2, p3, p4, p5, p6, p7));
        // find points of next cube
        p4 = p4.add(width.scale(RELATIVE_WIDTH*this.distance));
        p5 = p5.add(width2.scale(RELATIVE_WIDTH*this.distance));
        p6 = p6.add(width.scale(RELATIVE_WIDTH*this.distance*-1));
        p7 =p7.add(width2.scale(RELATIVE_WIDTH*this.distance*-1));
        recruisiveCubeGenerator(p4,p5,p6,p7,p4.distance(p5));
    }

    @Override
    public Vector getNormal(Point point) {
        List<Vector> normals = new ArrayList<>();
        Vector normal = null;
        for (Cube cube : cubes) {
            normal=cube.getNormal(point);
            if (normal != null) {
                normals.add(normal);
            }
        }
        if(normals.size()==0){
            return null;
        }
        else {
            return normals.get(0);
        }

    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> tempResult= null ;
        for (var i: cubes) {
            List<GeoPoint> points=i.findGeoIntersectionsHelper(ray,maxDistance);
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
        return result;    }
}
