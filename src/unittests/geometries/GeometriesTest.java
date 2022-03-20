package unittests.geometries;

import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class GeometriesTest {


    @BeforeEach
    void setUp() {
    }


    @Test
    void testFindIntersections() {
        Geometries lst=new Geometries();
        //TC:01 empty set
        assertNull(lst.findIntersections(new Ray(new Point(1,1,1),new Vector(5,4,6))),
                "the set are empty");
        try{
            lst.add(new Sphere(new Point(2,0,0),1),
                    new Plane(new Point(0,0,1),new Point(0,1,1),new Point(1,0,1)),
                    new Triangle(new Point(0,0,2),new Point(4,0,2),new Point(0,4,2)));
        }
        catch (Exception ex){
            fail("Failed adding geometries to the set");
        }

        //TC:02 no intersections
        assertNull(lst.findIntersections(new Ray(new Point(0,0,3),new Vector(1,0,0))),
                "there is 0 intersections");
        //TC:03 one intersection
        assertEquals(lst.findIntersections(new Ray(new Point(5,0,0),new Vector(0,0,1))).size(), 1,
                "there is only one intersection");

        //TC:04 multiple intersections but not all
        assertEquals(lst.findIntersections(new Ray(new Point(2,-0.5,0),new Vector(0,0,1))).size(),2,
                "there is some intersections");
        //TC:05 all of them intersect
        assertEquals(lst.findIntersections(new Ray(new Point(2,0.5,0),new Vector(0,0,1))).size(),3,
                "there is intersections with all of them");

    }
}