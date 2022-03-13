package unittests.geometries;

import geometries.Tube;
import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    @Test
    void getNormal() {

        Ray ray1 = new Ray(new Point(0,1,0), new Vector(0,2,0));
        Tube tb1 = new Tube(ray1,2);
        //test a simple case of getNormal of tube
        assertEquals(new Vector(1,0,0),tb1.getNormal(new Point(2,0,0)),"bad normal of tube" );
        Ray ray2 = new Ray(new Point(0,0,0), new Vector(0,1,0));
        Tube tb2 = new Tube(ray2,1);
        //test of case of getNormal of tube with "point in front of head of the ray"
        assertThrows(IllegalArgumentException.class,()->tb2.getNormal(new Point(1,0,0)));
    }
}