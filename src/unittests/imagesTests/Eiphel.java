package unittests.imagesTests;

import geometries.EiphelTower;
import lighting.*;

import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.*;
import static java.awt.Color.*;

public class Eiphel {
    private Scene scene = new Scene("Test scene") //
            .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.2)));
    private Camera camera = new Camera(new Point(5,20, 300), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setVPSize(150, 150) //
            .setVPDistance(1000);
    Point[] p = { // The Triangles' vertices:
           new Point(10,1,-1),
              new Point(-10,1,-1),
                new Point(-10,1,-11),
                new Point(10,1,-11)};
     // the left-top




    @Test
    void Eiphel() {
        scene.setBackground(new Color(BLACK));
        scene.lights.add(new DirectionalLight(new Color(255, 255, 255), new Vector(0, -1, 1)));
        scene.geometries.add(new EiphelTower(p[0],p[1],p[2],p[3]).setEmission(new Color(WHITE)));

        ImageWriter imageWriter = new ImageWriter("Eiphel", 1000, 1000);
        camera.moveCameraAndPointWiev(new Point(0,20, 290),new Point(0,20,0),0);

        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
    }
}
