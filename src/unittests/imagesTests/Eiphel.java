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
    void EiphelSceneProject1() {
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));
        scene.setBackground(new Color (56, 40, 92));

        scene.lights.add(new SpotLight(new Color(800, 250, 125), new Point(0,20,-167),new Vector(0,-1,17)).setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(50, 100, 100), new Point(-10,100,-150)).setKl(4E-5).setKq(2E-7));
        scene.lights.add(new SpotLight(new Color(250,250,250),new Point(-50, 1.5, -100),
                new Point(-9.39,39.62,-150.6).subtract(new Point(-50, 1.5, -100))));
        scene.geometries.add(new EiphelTower(p[0],p[1],p[2],p[3]).setEmission(new Color(152,140,129)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30)),
                new Plane(new Point(1,1,0),new Point(0,1,1),new Point(0,1,0)).setMaterial(
                        new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(new Double3(0.5))
                                .setDiffusedAndGlossy(0.9)));
        ImageWriter imageWriter1 = new ImageWriter("EiphelProjectScene1", 1000, 1000);
        ImageWriter imageWriter2 = new ImageWriter("EiphelProjectScene2", 1000, 1000);
        ImageWriter imageWriter3 = new ImageWriter("EiphelProjectScene3", 1000, 1000);

      //  camera.moveCameraAndPointWiev(new Point(-50,20,-330),new Point(-10,9,-150),0).zoomCamera(0.3);
        camera.moveCameraAndPointWiev(new Point(245,7,-188),new Point(-10,9,-150),0).zoomCamera(0.3);


      /*  camera.setImageWriter(imageWriter2).setNumRays(2) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //*/
       /* camera.moveCameraAndPointWiev(new Point(-330,20,-50)
                ,new Point(-10,9,-150),0).zoomCamera(1.2);**/

        camera.setImageWriter(imageWriter2).setNumRays(1).setAdaptiveSuperSplmingFlag(false).setMultithreading(3) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
        /*
        private Point[] pointsForAnimation = {
            new Point(-239, 20, -84.32),
            new Point(-243.93, 20, -129.05),
            new Point(-245.7, 20, -167.22),
            new Point(-239.32, 20, -217.72),
            new Point(-224.33, 20, -261.59),
            new Point(-197, 20, -308),
            new Point(-159.26, 20, -347.91),
            new Point(-122.1, 20, -374.2),
            new Point(-71.58, 20, -395.77),
            new Point(-11.68, 20, -406.17),
            new Point(44.93, 20, -402.4),
            new Point(103.62, 20, -383.67),
            new Point(154.4, 20, -352.25),
            new Point(196.83, 20, -308.54),
            new Point(221.22, 20, -269.15),
            new Point(245.07, 20, -188.29),
            new Point(246.36, 20, -150.01),
            new Point(226.94, 20, -63.35),
            new Point(203.21, 20, -20.13),
            new Point(160.53, 20, 27.25),
            new Point(116.57, 20, 57.24),
            new Point(71.56, 20, 75.93),
            new Point(20.19, 20, 85.87),
            new Point(-32.42, 20, 84.56),
            new Point(-79.1, 20, 73.66),
            new Point(-140.24, 20, 42.61),
            new Point(-172.68, 20, 16.19),
            new Point(-203.42, 20, -20.42),
            new Point(215.91, 20, -40.66),
            new Point(237.07, 20, -228.17),
            new Point(239.22, 20, -99.87),};
        int i = 0;
*/
        /*        for (Point point : pointsForAnimation)
        {
            //if (i > 0) Thread.sleep((long) 10000);
            i++;
            camera.moveCameraAndPointWiev(point,new Point(-10,9,-150),0);
            ImageWriter imageWriter = new ImageWriter("EiphelProjectSceneAnimation"+i, 800, 800);
            camera.setImageWriter(imageWriter).setNumRays(1).setAdaptiveSuperSplmingFlag(false).setMultithreading(3)
                    .setDebugPrint(1)//
                    .setRayTracer(new RayTracerBasic(scene)) //
                    .renderImage() //
                    .writeToImage(); //

        }
        camera.moveCameraAndPointWiev(new Point(-239, 20, -84.32),new Point(-10,9,-150),0).zoomCamera(0.34);

        ImageWriter imageWriter = new ImageWriter("EiphelAfterProject1", 1000, 1000);
        camera.setImageWriter(imageWriter).setNumRays(1).setAdaptiveSuperSplmingFlag(false).setMultithreading(3)
                .setDebugPrint(1)//
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //*/

    }




    @Test
    void Eiphel() {
         Scene scene = new Scene("Test scene") //
                .setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.2)));
         Camera camera = new Camera(new Point(5,20, 300), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150) //
                .setVPDistance(1000);
        Point[] p = { // The Triangles' vertices:
                new Point(10,1,-21),
                new Point(-10,1,-21),
                new Point(-10,1,-1),
                new Point(10,1,-1)};
        scene.setBackground(new Color(BLACK));
        scene.lights.add(new DirectionalLight(new Color(255, 255, 255), new Vector(0, -1, 1)));
        scene.geometries.add(new EiphelTower(p[0],p[1],p[2],p[3]).setEmission(new Color(WHITE)));

        ImageWriter imageWriter = new ImageWriter("EiphelSimple", 1000, 1000);
        camera.moveCameraAndPointWiev(new Point(0,20, 290),new Point(0,20,0),0);

        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
    }
}