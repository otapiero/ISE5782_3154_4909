package xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lighting.AmbientLight;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import geometries.*;
import primitives.*;
import scene.*;

public class XNLDOM {

    public static Scene ReadSceneFromXMLFile(String sceneName, String Path)
    {
        Scene scene = new Scene(sceneName);
        try {
            File file = new File(Path);
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            Node sceneNode = document.getElementsByTagName("scene").item(0); // צריך להיות רק 1
            Element elementOfScen = (Element) sceneNode;

            // Background
            scene.setBackground(getBackgroundFromeFile(elementOfScen, "background-color"))
                    //AmbientLight
                    .setAmbientLight(getAmbientLightFromeFile(elementOfScen,"color","Ka"))
                    .setGeometries(getGeometriesFromeFile((Element)(elementOfScen.getElementsByTagName("geometries").item(0))));


        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return scene;
    }

    public static Point getPoint(Element elementWithPoint, String pointName)
    {
        String[] points = elementWithPoint.getAttribute(pointName).split(" ");
        return new Point(Integer.parseInt(points[0]), Integer.parseInt(points[1]), Integer.parseInt(points[2]));

    }

    public static Color getBackgroundFromeFile(Element elementOfScen, String attributeName )
    {
        String [] colorRGB = elementOfScen.getAttribute(attributeName).split(" ");
        return new Color(Integer.parseInt(colorRGB[0]),Integer.parseInt(colorRGB[1]),Integer.parseInt(colorRGB[2]));
    }

    public static AmbientLight getAmbientLightFromeFile(Element elementOfScen, String attributeName1, String attributeName2 )
    {
        Element elementAmb= (Element) elementOfScen.getElementsByTagName("ambient-light").item(0);//
        String [] colorRGB = elementAmb.getAttribute(attributeName1).split(" ");
        double Ka = Double.parseDouble(elementAmb.getAttribute(attributeName2));
        return new AmbientLight(new Color(Integer.parseInt(colorRGB[0]),Integer.parseInt(colorRGB[1]),Integer.parseInt(colorRGB[2])),
                new Double3(Ka));
    }

    public static Geometries getGeometriesFromeFile(Element GeometriesElementList)
    {
        Geometries geometries = new Geometries();

        NodeList sphereList = GeometriesElementList.getElementsByTagName("sphere");
        for (int i = 0; i < sphereList.getLength();i++) {
            Element sphere = (Element) sphereList.item(i);
            Point center = getPoint(sphere, "center");
            double radius = Integer.parseInt(sphere.getAttribute("radius"));
            geometries.add(new Sphere(center, radius));
        }

        NodeList planeList = GeometriesElementList.getElementsByTagName("plane");
        for (int i = 0; i < planeList.getLength();i++) {
            Element plane = (Element) planeList.item(i);
            Point q0 = getPoint(plane, "p0");
            Point xyz = getPoint(plane,"normal");
            Vector normal = new Vector(xyz.getX(),xyz.getY(), xyz.getZ());
            geometries.add(new Plane(q0, normal));
        }

        NodeList triangleList = GeometriesElementList.getElementsByTagName("triangle");
        for (int i = 0; i < triangleList.getLength();i++) {
            Element triangle = (Element) triangleList.item(i);
            Point p1 = getPoint(triangle, "p0");
            Point p2 = getPoint(triangle, "p1");
            Point p3 = getPoint(triangle, "p2");
            geometries.add(new Triangle(p1, p2, p3));
        }

        return geometries;
    }


}
