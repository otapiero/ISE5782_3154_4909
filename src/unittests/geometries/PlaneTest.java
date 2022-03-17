package unittests.geometries;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    @Test
    void testConstractor() {
        try {//test a build a correct Plane
            new Plane(
                    new Point(0, 1, 0),
                    new Point(1, 0, 0),
                    new Point(0, 0, 1));


        }
        catch (IllegalArgumentException e) {
            fail("Failed constructing a correct Plane");
        }
        // test build a plane with 3 points in the same row
        assertThrows(IllegalArgumentException.class,()-> new Plane(
                new Point(1, 1, 1),
                new Point(2, 2, 2),
                new Point(4, 4, 4)),
                "Constructed a plane with 3 points in the same row!");
        // test build a plane with  2 points  the same
        assertThrows(IllegalArgumentException.class,()-> new Plane(
                new Point(1, 0, 0),
                new Point(1, 0, 0),
                new Point(0, 0, 1)),
                "Constructed a plane with 2 points  the same!");

    }

    @Test
    void testGetNormal() {
        Plane p = new Plane(
                new Point(0,1,0),
                new Point(1,0,0),
                new Point(0,0,1));
            assertEquals(1, p.getNormal(null).length());
    }

    @Test
    void findIntersections() {
        Plane plane =new Plane(new Point(0,1,1),new Point(1,0,1),new Point(0,0,1));
        //TC:01 intercts
        Point p1 =new Point(-1.85,-0.49,1);
        List<Point> result = plane.findIntersections(new Ray(new Point(1, 0, 0),new Vector(-2.85,-0.49,1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result, "Ray crosses plane");
        //TC:02 not intercts
        assertNull(plane.findIntersections(new Ray(new Point(1, 1, 0), new Vector(1, 1, -1))),
                "Ray's line out of plane");
        //BVA CASE
        //parallel to the case
        //TC:03 not include
        assertNull(plane.findIntersections(new Ray(new Point(1, 1, 2), new Vector(0, 1, 0))),
                "Ray's line out of plane");
        //TC:04  included
        assertNull(plane.findIntersections(new Ray(new Point(1, 1, 2), new Vector(0, 1, 0))),
                "Ray's line out of plane");
        //ortogonal
        //TC:05 before the plane
        p1 =new Point(1,0,1);
        result = plane.findIntersections(new Ray(new Point(1, 0, 0),new Vector(0,0,1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result, "Ray crosses plane");
        //TC:06 in the plane
        assertNull(plane.findIntersections(new Ray(new Point(1, 1,1 ), new Vector(0, 0, 1))),
                "Ray's line out of plane");
        //TC:07 after the plane
        assertNull(plane.findIntersections(new Ray(new Point(1, 1,2 ), new Vector(0, 0, 1))),
                "Ray's line out of plane");
        //TC:08 not orthogonal or parallel and start in the plane
        assertNull(plane.findIntersections(new Ray(new Point(1, 1,1 ), new Vector(1, 1, 1))),
                "Ray's line out of plane");
        //TC:09 not orthogonal or parallel and start in the plane with point of plane
        assertNull(plane.findIntersections(new Ray(new Point(0, 0,1 ), new Vector(1, 1, 1))),
                "Ray's line out of plane");
    }
}

