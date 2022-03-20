package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{
    List<Intersectable> geometriesLinkedList;
//class for set of geometries object

    /**
     * default constructor of Geometries to do a new list
     */
    public Geometries() {
        geometriesLinkedList=new LinkedList();
    }

    /**
     *  constructor of Geometries to do a new list with some geometries shapes
     * @param geometries some geometries shapes
     */
    public Geometries(Intersectable... geometries){
        this();
        for (var i: geometries) {
            geometriesLinkedList.add(i);
        }
    }

    /**
     * find intersection with all the shapes in the set
     * @param ray for finding intersections with all the shapes in the set
     * @return list of point that intersect with one of the shapes in the set
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> result= null ;
        for (var i: geometriesLinkedList) {
            List<Point> points=i.findIntersections(ray);
            if (points!=null){
                if (result==null){
                    result=new LinkedList();
                }
                result.addAll(points);
            }
        }
        return result;
    }
    public void add(Intersectable... geometries){
        for (var i:
             geometries) {
            geometriesLinkedList.add(i);
        }
    }
}
