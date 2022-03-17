package unittests.geometries;

import geometries.*;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.*;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle tr = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector( sqrt3, sqrt3, sqrt3), tr.getNormal(new Point(0, 0, 1)), "Bad normal to trinagle");
    }

    @Test
    void findIntersections() {
        Triangle triangle= new Triangle(new Point(0,1,1),new Point(1,0,1),new Point(0,0,1));
        Point p1 =new Point(0.23,0.64,1);
        List<Point> result = triangle.findIntersections(new Ray(new Point(0, 0.5, 0),new Vector(0.23,0.14,1)));
        //EP three cases
        //TC:01 inside
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result, "Ray crosses triangle");
        //TC:02 outside against the edge
        assertNull(triangle.findIntersections(new Ray(new Point(0, 0.5, 0), new Vector(0.47, 0.63, 1))),
                "Ray's line out of triangle");
        //TC:03 outside against the vertex
        assertNull(triangle.findIntersections(new Ray(new Point(0, 0.5, 0), new Vector(-0.07, 0.57, 1))),
                "Ray's line out of triangle");
        //
        //TC:04 on the edge
        assertNull(triangle.findIntersections(new Ray(new Point(0, 0.5, 0), new Vector(0, 0.09, 1))),
                "Ray's line out of triangle");
        //TC:05 in vertex
        assertNull(triangle.findIntersections(new Ray(new Point(0, 0.5, 0), new Vector(0, 0.5, 1))),
                "Ray's line out of triangle");
        //TC:06 on edg continuation
        assertNull(triangle.findIntersections(new Ray(new Point(0, 0.5, 0), new Vector(0, 2, 1))),
                "Ray's line out of triangle");

    }
}