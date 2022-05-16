package unittests.imagesTests;

import geometries.Pyramid;
import geometries.Plane;
import geometries.Sphere;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import static java.awt.Color.*;


public class SceneTargil7 {
    private Scene scene = new Scene("Test scene") //
            .setAmbientLight(new AmbientLight(new Color(RED), new Double3(0.2)));
    private Camera camera = new Camera(new Point(50,30, 40), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setVPSize(150, 150) //
            .setVPDistance(1000);
    private Material materialPlane = new Material().setKd(0.5).setKs(0.5).setShininess(1000);
    private Material materialPyramid = new Material().setKd(0.6).setKs(1).setShininess(300);
    private Material materialSphere = new Material().setKd(0.2).setKs(0.2).setKt(new Double3(0.6)).setShininess(1000);

    private Plane plane = new Plane(new Point(1, 1, 0),new Point(0,1,0),new Point(0,1,1));
    private Plane skyPlane = new Plane(new Point(0,0,-190),new Vector(0,0,1));
    private Sphere sphere = new Sphere(new Point(-12, 10, -140),6);
    private Point[] p = { // The Triangles' vertices:
            new Point(-25, 1, -165), // the shared left-bottom
            new Point(0, 1, -165), // the shared right-top
            new Point(0, 1, -140), // the right-bottom
            new Point(-25, 1, -140)};
    Pyramid pyramid = new Pyramid(p[0], p[1], p[2], p[3]);



    @Test
    public void Scene() {
        materialPlane.setKr(new Double3(0.3));
       plane.setMaterial(materialPlane).setEmission(new Color(113,88,54));
         pyramid.setMaterial(materialPyramid).setEmission(new Color(148,84,42));
       scene.setBackground(new Color(38,91,133));
       scene.geometries.add(plane,pyramid,new Sphere(new Point(-10, 27, -120), 5).setEmission(new Color(255,50,0) )//
               .setMaterial(new Material().setKd(0.5).setKs(1).setShininess(30).setKt(new Double3(1))));
        scene.lights.add(new PointLight(new Color(125, 125, 15), new Point(-12.5,50,-100)));
       scene.lights.add(new PointLight(new Color(100, 50, 150), new Point(-10,10,-152.5)));
        scene.lights.add(new PointLight(new Color(100, 50, 150), new Point(25,10,-152.5)));
      scene.lights.add(new PointLight(new Color(300, 100, 10),new Point(-10.2, 27.5, -121)));

        scene.lights.add(new DirectionalLight(new Color(100, 100, 100), new Vector(-1, -2.5, -10)));
        scene.lights.add(new SpotLight(new Color(800, 250, 125), new Point(0,5,-167),new Vector(0,-1,17)));



        ImageWriter imageWriter = new ImageWriter("SceneTargil7_1", 1000, 1000);
        ImageWriter imageWriter2 = new ImageWriter("SceneTargil7_2", 1000, 1000);
        ImageWriter imageWriter3 = new ImageWriter("SceneTargil7_3", 1000, 1000);
        ImageWriter imageWriter4 = new ImageWriter("SceneTargil7_4", 1000, 1000);

        camera.moveCamera(new Vector(1,0,0.5),30)
                .moveCameraAndPointWiev(camera.getPoint(),new Point(-10,12,-150),-6).zoomCamera(0.9);

        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //

        camera.moveCameraAndPointWiev(new Point(-50,12,0),new Point(-10,16,-150),0).zoomCamera(0.7);


        camera.setImageWriter(imageWriter2) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //


    }

}
