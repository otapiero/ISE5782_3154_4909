package unittests.geometries;

import geometries.Cube;
import geometries.Intersectable;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import renderer.Camera;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CubeTest {

    @Test
    void getNormal() {
    }

    @Test
    void findGeoIntersectionsHelper() {
        Cube cube = new Cube(new Point(1,0,0),new Point(2,0,0), new Point(2,1,0),new Point(1,1,0),
                new Point(1,0,1),new Point(2,0,1),new Point(2,1,1),new Point(1,1,1));

         //TC01 Ray starts before and crosses the cube (2 points)
        assertCountIntersections(2, cube, new Ray(new Point(0,1,0),new Vector(2,-0.25,0.66)));

        //TC02 straight ray (2 points)
        assertCountIntersections(2, cube, new Ray(new Point(0,0.5,0.5),new Vector(1,0,0)));

        // TC03: Ray starts inside the sphere (1 point)
        assertCountIntersections(1, cube, new Ray(new Point(1.5,0.5,0.5),new Vector(1,0,0)));

        //TC04 Ray's line is outside the cube (0 points)
        assertCountIntersections(0, cube, new Ray(new Point(2,0.5,0.5),new Vector(1,0,0)));

        //TC05  Ray starts after the cube (0 points)
        assertCountIntersections(0, cube, new Ray(new Point(0,2,0.5),new Vector(1,0,0)));

        //TC0  Ray crosses the Vertices (0 points)
        assertCountIntersections(0, cube, new Ray(new Point(0,0,1),new Vector(1,0,0)));



    }


    private void assertCountIntersections(int expected, Cube cube, Ray ray) {
        int count =0;
        List<Intersectable.GeoPoint> list = cube.findGeoIntersections(ray);
        if(list != null)count = list.size();
        assertEquals(expected, count, "The number of intersections found was incorrect");
    }
}