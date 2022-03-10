package unittests.primitives;

import geometries.Polygon;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PointTest {

    @Test
    public void testConstructor() {
        try {
            new Point(1,2,3 );
            new Point(0,0,0 );
            new Point(-1,-5,3 );
            new Point(1,2,-4 );
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct point");
        }
    }
    @Test
    void testSubtract() {
        Point p1 = new Point(1, 2, 3);
        assertEquals(new Vector(1, 1, 1),new Point(2, 3, 4).subtract(p1), "bad vector to subtract");
    }

    @Test
    void testDistanceSquared() {
        Point p1 = new Point(1, 2, 3);
        double distance= Math.pow( 2-1,2)+Math.pow(4-2,2)+Math.pow(6-3,2);
        assertEquals(distance,p1.distanceSquared(new Point(2,4,6)),0.00001,"bad distance squared");
    }

    @Test
    void testDistance() {
        Point p1 = new Point(1, 2, 3);
        double distance= Math.sqrt(Math.pow( 2-1,2)+Math.pow(4-2,2)+Math.pow(6-3,2));
        assertEquals(distance,p1.distance(new Point(2,4,6)),0.00001,"bad distance");

    }

    @Test
    void testAdd() {
        Point p1 = new Point(1, 2, 3);
        assertEquals(new Point(0, 0, 0),p1.add(new Vector(-1, -2, -3)), "bad vector to add");
    }

}