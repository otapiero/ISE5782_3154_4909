package unittests.geometries;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;

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
}