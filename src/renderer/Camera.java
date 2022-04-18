package renderer;

import primitives.*;

import java.util.MissingResourceException;
import java.util.concurrent.ExecutionException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Camera {
    Point point;
    Vector Vright,Vup,Vto;
    double height, distance,width;

    public ImageWriter getImageWriter() {
        return imageWriter;
    }

    public RayTracerBase getRayTracer() {
        return rayTracer;
    }

    ImageWriter imageWriter;

    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    RayTracerBase rayTracer;


    /**
     *
     * Returns the point 0
     * @return
     */
    public Point getPoint() {
        return point;
    }

    /**
     *Returns the direction vector to the right
     * @return
     */
    public Vector getVright() {
        return Vright;
    }

    /**
     * Returns the direction vector up
     * @return
     */
    public Vector getVup() {
        return Vup;
    }

    /**
     * Returns the direction vector straight
     * @return
     */
    public Vector getVto() {
        return Vto;
    }

    /**
     * Returns the height of the viewo plane
     * @return
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the distance of the view plane from the camera
     * @return
     */
    public double getDistance() {
        return distance;
    }

    /**
     *Returns the width of the viewo plane
     * @return
     */
    public double getWidth() {
        return width;
    }

    /**
     * A builder that gets the vectors up and straight and builds the vector to the right and saves all the values
     * @param point
     * @param vto
     * @param vup
     */
    public Camera(Point point, Vector vto,Vector vup) {

        if (!isZero(vto.dotProduct(vup))) throw new IllegalArgumentException("the vectors are not orthogonal");
        Vup = vup.normalize();
        Vto = vto.normalize();
        Vright=Vto.crossProduct(Vup).normalize();
        this.point=point;

    }

    /**
     * set to the width and height values
     * @param width
     * @param height
     * @return
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * set to the value of the distance from the camera
     * @param distance
     * @return
     */
    public Camera setVPDistance(double distance){
        this.distance=distance;
        return this;
    }

    /**
     * The function gets the number of pixels there are, and also the i and j of a specific pixel in vieow plane
     * and returns ray through this pixel
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return
     */
    public Ray constructRay(int nX, int nY, int j, int i){
        Point Pc=point.add(Vto.scale(distance));
       double Ry=alignZero(height/nY);
       double Rx=alignZero(width/nX);
        // Xj = (j - (Nx -1)/2) * Rx
        double Xj = alignZero((j - ((nX - 1d) / 2d)) * Rx);
        // Yi = -(i - (Ny - 1)/2) * Ry
        double Yi = alignZero(- (i - ((nY - 1d) / 2d)) * Ry);
       Point Pij=Pc;
        if (Xj != 0d) Pij = Pij.add(Vright.scale(Xj));
        if (Yi != 0d) Pij = Pij.add(Vup.scale(Yi));
        if(Pij.equals(point)) throw new IllegalArgumentException("the point is the same as the camera");

        return new Ray(point,Pij.subtract(point).normalize());
    }


    public void renderImage() {
        if (imageWriter == null || this.rayTracer == null || distance == 0 || this.width == 0 || this.height == 0) {
            throw new MissingResourceException("the image writer or the ray tracer or the distance or the width or the height is not set", "Camera", "Camera");
        }
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {

                imageWriter.writePixel(i, j, castRay(i, j));
            }
        }
    }
    private Color castRay(int i, int j) {
        return rayTracer.traceRay(this.constructRay(imageWriter.getNx(), imageWriter.getNy(),  i, j));
    }

    public void printGrid(int interval, Color color){
        if (imageWriter == null ) {
            throw new MissingResourceException("the image writer is not set", "Camera", "Camera");
        }
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);

                }
            }
        }
    }

    public void writeToImage() {
        if (imageWriter == null) {
            throw new MissingResourceException("the image writer is not set", "Camera", "Camera");
        }
        imageWriter.writeToImage();
    }
}
