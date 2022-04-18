package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.*;

import java.util.LinkedList;
import java.util.List;

public class RayTracerBasic extends RayTracerBase {

    public RayTracerBasic(Scene scene) {
        super(scene);
    }
    Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections= scene.geometries.findIntersections(ray);
        if (intersections==null)
            return scene.background;
        return calcColor(ray.findClosestPoint(intersections));
    }

}
