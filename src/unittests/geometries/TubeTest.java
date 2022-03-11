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

        assertEquals(new Vector(1,0,0),tb1.getNormal(new Point(2,0,0)),"bad norma" );
        Ray ray2 = new Ray(new Point(0,0,0), new Vector(0,1,0));
        Tube tb2 = new Tube(ray2,1);
        assertThrows(IllegalArgumentException.class,()->tb2.getNormal(new Point(1,0,0)));
    }
}