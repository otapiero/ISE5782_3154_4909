package unittests.imagesTests;

import geometries.EiphelTower;
import geometries.Plane;
import lighting.*;

import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.*;
import static java.awt.Color.*;

public class Eiphel {
    private Scene scene = new Scene("Test scene") //
            .setAmbientLight(new AmbientLight(new Color(GRAY), new Double3(0.2)));
    private Camera camera = new Camera(new Point(5,20, 300), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setVPSize(150, 150) //
            .setVPDistance(1000);
    private Point[] p = {
            new Point(0, 1, -160),
            new Point(-20, 1, -160),
            new Point(-20, 1, -140),
            new Point(0, 1, -140)};




    @Test
    void Eiphel() {
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

        scene.lights.add(new SpotLight(new Color(800, 250, 125), new Point(0,20,-167),new Vector(0,-1,17)).setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(250, 250, 250), new Point(10,50,-150)).setKl(4E-5).setKq(2E-7));
        scene.geometries.add(new EiphelTower(p[0],p[1],p[2],p[3]).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                new Plane(new Point(1,1,0),new Point(0,1,1),new Point(0,1,0)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)));

        ImageWriter imageWriter = new ImageWriter("Eiphel", 1000, 1000);
        camera.moveCameraAndPointWiev(new Point(-50,25,-330),new Point(-10,16,-150),0).zoomCamera(0.4);

        camera.setImageWriter(imageWriter).setNumRays(2) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
    }
}
/*
public class Eiphel {
    private Scene scene = new Scene("Test scene") //
            .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.2)));
    private Camera camera = new Camera(new Point(5,20, 300), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setVPSize(150, 150) //
            .setVPDistance(1000);
    Point[] p = { // The Triangles' vertices:
            new Point(10,1,-21),
            new Point(-10,1,-21),
            new Point(-10,1,-1),
            new Point(10,1,-1)};
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
}*/