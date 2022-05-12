package unittests.imagesTests;

import geometries.Eiphel.Cube;
import geometries.Eiphel.Pyramid;
import geometries.Geometry;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;
import org.junit.jupiter.api.Test;

import static java.awt.Color.*;
import static org.junit.jupiter.api.Assertions.*;

 class CubeTest {


        private Scene scene2 = new Scene("Test scene") //
                .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));
        private Camera camera1 = new Camera(new Point(0,0, 100), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150) //
                .setVPDistance(1000);


        private Point[] p = { // The Triangles' vertices:
                new Point(1, 1, 1), // the shared left-bottom
                new Point(5, 1, 1), // the shared right-top
                new Point(5, 1, 5), // the right-bottom
                new Point(1, 1, 5),
                new Point(-1,-1,-5),
                new Point(-5,-1,-5),
                new Point(-5,-5,-5),
                new Point(-1,-5,-5)}; // the left-top
     private Point trPL = new Point(30, 10, -100); // Triangles test Position of Light
     private Point spPL = new Point(-50, -50, 25); // Sphere test Position of Light
     private Color trCL = new Color(200, 200, 250); // Triangles test Color of Light
     private Color spCL = new Color(800, 500, 0); // Sphere test Color of Light
     private Vector trDL = new Vector(-2, -2, -2); // Triangles test Direction of Light
        private Material material = new Material().setKd(0.5).setKs(0.5).setShininess(300);
      @Test
     void testCube() {
        Pyramid cube = new Pyramid(p[0], p[1], p[2], p[3]);
        cube.setEmission(new Color(BLUE).reduce(2)) //
                .setEmission(new Color(BLUE).reduce(2)) //
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
        scene2.setBackground(new Color(GRAY));

        scene2.geometries.add(cube);
          scene2.lights.add(new PointLight(trCL,new Point(2,2,8)));
         // scene2.lights.add(new DirectionalLight(trCL, new Vector(0,1,-10)));
          // scene2.lights.add(new SpotLight(spCL, new Point(0,0,0), new Vector(1,1,1)).setKl(0.001).setKq(0.0002));
         ImageWriter imageWriter = new ImageWriter("CUbedirectionalLhigt", 900, 900);
         camera1.moveCameraAndPointWiew(new Vector(1,0,0),60,new Point(-2,-2,-8),0);
         camera1.setImageWriter(imageWriter) //
                 .setRayTracer(new RayTracerBasic(scene2)) //
                 .renderImage() //
                 .writeToImage(); //


     }
}