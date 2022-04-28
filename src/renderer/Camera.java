package renderer;

import primitives.*;

import java.util.MissingResourceException;
import java.util.concurrent.ExecutionException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The type Camera.
 */
public class Camera {
    /**
     * The Point.
     */
    Point point;
    /**
     * The Vright.
     */
    Vector Vright, /**
     * The Vup.
     */
    Vup, /**
     * The Vto.
     */
    Vto;
    /**
     * The Height.
     */
    double height, /**
     * The Distance.
     */
    distance, /**
     * The Width.
     */
    width;
    /**
     * The Image writer.
     */
    ImageWriter imageWriter;
    /**
     * The Ray tracer.
     */
    RayTracerBase rayTracer;

    /**
     * Gets image writer.
     *
     * @return the image writer
     */
    public ImageWriter getImageWriter() {
        return imageWriter;
    }

    /**
     * Gets ray tracer.
     *
     * @return the ray tracer
     */
    public RayTracerBase getRayTracer() {
        return rayTracer;
    }


    /**
     * Sets image writer.
     *
     * @param imageWriter the image writer
     * @return the image writer
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Sets ray tracer.
     *
     * @param rayTracer the ray tracer
     * @return the ray tracer
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }



    /**
     * Returns the point 0
     *
     * @return point
     */
    public Point getPoint() {
        return point;
    }

    /**
     * Returns the direction vector to the right
     *
     * @return vright
     */
    public Vector getVright() {
        return Vright;
    }

    /**
     * Returns the direction vector up
     *
     * @return vup
     */
    public Vector getVup() {
        return Vup;
    }

    /**
     * Returns the direction vector straight
     *
     * @return vto
     */
    public Vector getVto() {
        return Vto;
    }

    /**
     * Returns the height of the viewo plane
     *
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the distance of the view plane from the camera
     *
     * @return distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Returns the width of the viewo plane
     *
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * A builder that gets the vectors up and straight and builds the vector to the right and saves all the values
     *
     * @param point the point
     * @param vto   the vto
     * @param vup   the vup
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
     *
     * @param width  the width
     * @param height the height
     * @return vp size
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * set to the value of the distance from the camera
     *
     * @param distance the distance
     * @return camera
     */
    public Camera setVPDistance(double distance){
        this.distance=distance;
        return this;
    }

    /**
     * The function gets the number of pixels there are, and also the i and j of a specific pixel in vieow plane
     * and returns ray through this pixel
     *
     * @param nX the n x
     * @param nY the n y
     * @param j  the j
     * @param i  the
     * @return ray
     */
    public Ray constructRay(int nX, int nY, int j, int i){
        if ( distance == 0) {
            throw new IllegalArgumentException("distance is 0");
        }
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


    /**
     * The function writes the pixels to the image
     */
    public Camera renderImage() {
        if (imageWriter == null || this.rayTracer == null || distance == 0 || this.width == 0 || this.height == 0) {
            throw new MissingResourceException("the image writer or the ray tracer or the distance or the width or the height is not set", "Camera", "Camera");
        }
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {

                imageWriter.writePixel(i, j, castRay(i, j));
            }
        }
        return this;
    }

    private Color castRay(int i, int j) {
        return rayTracer.traceRay(this.constructRay(imageWriter.getNx(), imageWriter.getNy(),  i, j));
    }


    /**
     * Creates a grid of lines
     * gives color only to the grid defects but not to the rest of the pixels.
     *
     * @param interval - the size of each square in the grid (height and width)
     * @param color    - the color for the grid
     */
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

    /**
     * The function calls the function "writeToImage" in class "ImageWriter"
     */
    public Camera writeToImage() {
        if (imageWriter == null) {
            throw new MissingResourceException("the image writer is not set", "Camera", "Camera");
        }
        imageWriter.writeToImage();
        return this;
    }
}
