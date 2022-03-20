package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{
    List<Intersectable> geometriesLinkedList;

    public Geometries() {
        geometriesLinkedList=new LinkedList();
    }

    public Geometries(Intersectable... geometries){
        this();
        for (var i: geometries) {
            geometriesLinkedList.add(i);
        }
    }

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
