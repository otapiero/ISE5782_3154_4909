package unittests.geometries;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {


    @Test
    void testGetNormal() {
        Cylinder cylinder = new Cylinder(new Ray(new Point(1,0,0), new Vector(0,0,1)),1 ,5);
        // test of case of the center of the base cylinder
        assertEquals(cylinder.getAxisRay().getDir(), cylinder.getNormal(new Point(1, 0,5)));
        // test of case of the center of the base cylinder
        assertThrows(IllegalArgumentException.class, ()->cylinder.getNormal(new Point(1,0,0)),"Bad normal");
    }
}