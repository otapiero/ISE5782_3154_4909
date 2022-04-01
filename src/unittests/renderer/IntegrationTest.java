package unittests.renderer;
 import geometries.*;
 import primitives.*;
 import renderer.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationTest {
    @Test
    void testSphereIntergration() {
        Camera cam1 = new Camera(
                new Point(0,0,0),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0));
        Camera cam2 = new Camera(
                new Point(0, 0, 0.5),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0));
        //TC:01 - Sphere in the middle of the screen
        assertCountIntersections(cam1, new Sphere(new Point(0,0,-3), 1), 2);
        //TC:02 - screen is in the sphere
        assertCountIntersections(cam2, new Sphere(new Point(0,0,-2.5), 2.5), 18);
        //TC:03 - medium sphere 10 points
        assertCountIntersections(cam2, new Sphere( new Point(0, 0, -2),2), 10);
        //TC:04 -  screen and camera are in the sphere
        assertCountIntersections(cam2, new Sphere( new Point(0, 0, -1),4), 9);
        //TC:05 -  sphere beyound the camera
        assertCountIntersections(cam1, new Sphere( new Point(0, 0, 1),0.5), 0);

    }
    @Test
    void testTriangleIntegration() {
        Camera cam = new Camera(
                new Point(0,0,0),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0));
        //TC:01 - triangle in front of the screen 1 point
        assertCountIntersections(cam, new Triangle(new Point(0,1,-2), new Point(1,-1,-2), new Point(-1,-1,-2)), 1);
        //TC:02 - triangle in front of the screen 2 points
        assertCountIntersections(cam, new Triangle(new Point(0,20,-2), new Point(1,-1,-2), new Point(-1,-1,-2)), 2);
        //TC:03 - triangle behind the screen 0 points
        assertCountIntersections(cam, new Triangle(new Point(0,1,5), new Point(1,-1,5), new Point(-1,-1,5)), 0);
    }
    @Test
    void testPlaneIntergration() {
        Camera cam = new Camera(
                new Point(0,0,0),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0));
        //TC:01 - plane Parallel to the screen 9 points
        assertCountIntersections(cam, new Plane(new Point(0,0,-2), new Vector(0,0,1)), 9);
        //TC:02 - plane  9 points
        assertCountIntersections(cam, new Plane(new Point(0, 0, -5), new Vector(0, 1, 2)), 9);
        //TC:03 - plane  6 points
        assertCountIntersections(cam, new Plane(new Point(0, 0, -5), new Vector(0, 1, 1)), 6);

    }
    private void assertCountIntersections(Camera cam, Intersectable geo, int expected) {
        cam.setVPSize(3,3).setVPDistance(1);

        int nX = 3, nY = 3, count = 0;

        for(int i = 0; i < nX; i++){
            for(int j = 0; j < nY; j++){
                var ray = cam.constructRay(nX, nY, j, i); // create ray in the view plane
                var intersections = geo.findIntersections(ray);

                if(intersections != null){
                    count += intersections.size(); // count the total number of point
                }
            }
        }
        assertEquals(expected, count, "The number of intersections found was incorrect");
    }

}