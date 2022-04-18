package unittests.primitives;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void findClosestPoint() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Point(0.5, 0, 0), new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)).findClosestPoint(List.of(new Point(1, 0, 0), new Point(0.5, 0, 0), new Point(2, 0, 0))));
        // =============== Boundary Values Tests ==================
        //TC01 - List of points is empty
        assertNull(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)).findClosestPoint(List.of()));
        //TC02 - first point is the closest point
        assertEquals(new Point(0, 0, 0), new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)).findClosestPoint(List.of(new Point(0, 0, 0), new Point(1, 0, 0))));
        //TC03 - last point is the closest point
        assertEquals(new Point(1, 0, 0), new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)).findClosestPoint(List.of( new Point(2, 0, 0), new Point(1, 0, 0))));
    }
}