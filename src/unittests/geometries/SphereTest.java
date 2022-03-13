package unittests.geometries;

import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    @Test
    void testGetNormal() {
        //test of getNormal function of sphere
        Sphere s=new Sphere(new Point(0,0,0),1);
        assertEquals(new Vector(0,0,1),s.getNormal(new Point(0,0,1)), "Wrong normal to trinagle"  );
    }
}