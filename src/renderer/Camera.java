package renderer;

import primitives.*;
import primitives.Vector;

import java.util.*;
import java.util.stream.IntStream;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The type Camera.
 */
public class Camera {
    /**
     * The Vup.
     */
    static Vector VUP = new Vector(0, 1, 0);
    /**
     * The Vright.
     */
    static Vector VRIGHT = new Vector(1, 0, 0);
    /**
     * The Vto.
     */
    static Vector VTO = new Vector(0, 0, 1);
    /**
     * The Point.
     */
    Point point;
    /**
     * The Vright.
     */
    Vector Vright,
    /**
     * The Vup.
     */
    Vup,
    /**
     * The Vto.
     */
    Vto;
    /**
     * The Height.
     */
    double height,
    /**
     * The Distance.
     */
    distance,
    /**
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

    private int numRays=1, threadsCount = 3;
    /**
     * The Print interval.
     */
    double printInterval = 1;

    /**
     * Sets adaptive super splming flag.
     *
     * @param adaptiveSuperSplmingFlag the adaptive super splming flag
     * @return the adaptive super splming flag
     */
    public Camera setAdaptiveSuperSplmingFlag(boolean adaptiveSuperSplmingFlag) {
        this.adaptiveSuperSplmingFlag = adaptiveSuperSplmingFlag;
        return this;
    }

    private boolean adaptiveSuperSplmingFlag= true;
    /**
     * The Super spalming level.
     */
    double superSpalmingLevel =3;


    /**
     * Sets num rays.
     *
     * @param numRays the num rays
     * @return the num rays
     */
    public Camera setNumRays(int numRays) {
        this.numRays = numRays;
        return this;
    }

    /**
     * Gets num rays.
     *
     * @return the num rays
     */
    public int getNumRays() {
        return this.numRays;
    }


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
     * @return point point
     */
    public Point getPoint() {
        return point;
    }

    /**
     * Returns the direction vector to the right
     *
     * @return vright vright
     */
    public Vector getVright() {
        return Vright;
    }

    /**
     * Returns the direction vector up
     *
     * @return vup vup
     */
    public Vector getVup() {
        return Vup;
    }

    /**
     * Returns the direction vector straight
     *
     * @return vto vto
     */
    public Vector getVto() {
        return Vto;
    }

    /**
     * Returns the height of the viewo plane
     *
     * @return height height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the distance of the view plane from the camera
     *
     * @return distance distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Returns the width of the viewo plane
     *
     * @return width width
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
    public Camera(Point point, Vector vto, Vector vup) {

        if (!isZero(vto.dotProduct(vup))) throw new IllegalArgumentException("the vectors are not orthogonal");
        Vup = vup.normalize();
        Vto = vto.normalize();
        Vright = Vto.crossProduct(Vup).normalize();
        this.point = point;

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
     * @return camera vp distance
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
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
     * @return ray ray
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        if (distance == 0) {
            throw new IllegalArgumentException("distance is 0");
        }
        Point Pc = point.add(Vto.scale(distance));
        double Ry = alignZero(height / nY);
        double Rx = alignZero(width / nX);
        // Xj = (j - (Nx -1)/2) * Rx
        double Xj = alignZero((j - ((nX - 1d) / 2d)) * Rx);
        // Yi = -(i - (Ny - 1)/2) * Ry
        double Yi = alignZero(-(i - ((nY - 1d) / 2d)) * Ry);
        Point Pij = Pc;
        if (Xj != 0d) Pij = Pij.add(Vright.scale(Xj));
        if (Yi != 0d) Pij = Pij.add(Vup.scale(Yi));
        if (Pij.equals(point)) throw new IllegalArgumentException("the point is the same as the camera");

        return new Ray(point, Pij.subtract(point).normalize());
    }

    /**
     * The function gets the number of pixels there are, and also the i and j of a specific pixel in vieow plane
     * and returns ray through this pixel
     * use
     *
     * @param nX the n x
     * @param nY the n y
     * @param j  the j
     * @param i  the
     * @return ray ray
     */
    public List<Ray> constructRays(int nX, int nY, int i, int j) {
        if (numRays== 0) {
            throw new IllegalArgumentException("num of rays is 0");
        }
        if (numRays == 1) {
            return List.of(constructRay(nX, nY, j, i));
        }
        else {
         List<Ray> rays = new LinkedList<>();
            if (distance == 0) {
                throw new IllegalArgumentException("distance is 0");
            }
            Point Pc = point.add(Vto.scale(distance));
            double Ry = alignZero(height / nY);
            double Rx = alignZero(width / nX);
            double Xj = alignZero((j - ((nX - 1d) / 2d)) * Rx);
            double Yi = alignZero(-(i - ((nY - 1d) / 2d)) * Ry);
            Point Pij = Pc;
            if (Xj != 0d) Pij = Pij.add(Vright.scale(Xj));
            if (Yi != 0d) Pij = Pij.add(Vup.scale(Yi));
            if (Pij.equals(point)) throw new IllegalArgumentException("the point is the same as the camera");
            //Ray(point, Pij.subtract(point).normalize();
            double pY = alignZero(Ry / numRays);
            double pX = alignZero(Rx / numRays);
            Pij = Pij.add(Vright.scale(-Rx/2 )).add(Vup.scale(-Ry/2));;
            Point Pij1 = Pij;
            for (int k = 1; k < numRays; k++) {
                for (int l = 1; l < numRays; l++) {
                    Pij1 = Pij.add(Vright.scale(pX * l)).add(Vup.scale(pY * k));
                    rays.add(new Ray(point, Pij1.subtract(point).normalize()));
                }
            }


         return rays;
        }

    }

    /**
     * The function writes the pixels to the image
     *
     * @return the camera
     */
    /*public Camera renderImage() {
        if (imageWriter == null || this.rayTracer == null || distance == 0 || this.width == 0 || this.height == 0) {
            throw new MissingResourceException("the image writer or the ray tracer or the distance or the width or the height is not set", "Camera", "Camera");
        }
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {

                imageWriter.writePixel(i, j, castRay(i, j));
            }
        }
        return this;
    }*/

    /**
     * The function writes the pixels to the image
     *
     * @return the camera
     */
    /*public Camera renderImage() {
        if (imageWriter == null || this.rayTracer == null || distance == 0 || this.width == 0 || this.height == 0) {
            throw new MissingResourceException("the image writer or the ray tracer or the distance or the width or the height is not set", "Camera", "Camera");
        }
        IntStream.range(0, imageWriter.getNx()).parallel().forEach(i -> {
            IntStream.range(0, imageWriter.getNy()).parallel().forEach(j -> {


                imageWriter.writePixel(i, j, castRay(i, j));


            });
        });
        return this;
    }*/

    /**
     * The function writes the pixels to the image
     *
     * @return the camera
     */
    public Camera renderImage() {
        if (imageWriter == null || this.rayTracer == null || distance == 0 || this.width == 0 || this.height == 0) {
            throw new MissingResourceException("the image writer or the ray tracer or the distance or the width or the height is not set", "Camera", "Camera");
        }

        Pixel.initialize(imageWriter.getNx(),imageWriter.getNy() , printInterval);
        while (threadsCount-- > 0) {
            new Thread(() -> {
                for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone())
                    imageWriter.writePixel(pixel.row, pixel.col, castRay(pixel.row, pixel.col));

            }).start();
        }
        Pixel.waitToFinish();

        return this;
    }

    private Color castRay(int i, int j) {
        if (!adaptiveSuperSplmingFlag) {
            return rayTracer.traceRay(this.constructRays(imageWriter.getNx(), imageWriter.getNy(), j, i));
        }
        return AdaptiveSuperSampling(imageWriter.getNx(), imageWriter.getNy(), i, j);

    }
    private Color AdaptiveSuperSampling(int nX, int nY, int j, int i){


        if(numRays == 1)
            return rayTracer.traceRay(this.constructRays(imageWriter.getNx(), imageWriter.getNy(), i, j));
        if (distance == 0)
            throw new IllegalArgumentException("distance is 0");
        Point Pc = point.add(Vto.scale(distance));
        double Ry = alignZero(height / nY);
        double Rx = alignZero(width / nX);
        double Xj = alignZero((j - ((nX - 1d) / 2d)) * Rx);
        double Yi = alignZero(-(i - ((nY - 1d) / 2d)) * Ry);
        Point Pij = Pc;
        if(Xj != 0d) Pij = Pij.add(Vright.scale(Xj)) ;
        if(Yi != 0d) Pij = Pij.add(Vup.scale(Yi)) ;
        if (Pij.equals(point)) throw new IllegalArgumentException("the point is the same as the camera");

        double pY = alignZero(Ry / numRays);
        double pX = alignZero(Rx / numRays);
        return rayTracer.AdaptiveSuperSamplingRec(Pij, Rx, Ry, pX, pY,point,Vright, Vup,null);
    }


    /**
     * Creates a grid of lines
     * gives color only to the grid defects but not to the rest of the pixels.
     *
     * @param interval - the size of each square in the grid (height and width)
     * @param color    - the color for the grid
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null) {
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
     *
     * @return the camera
     */
    public Camera writeToImage() {
        if (imageWriter == null) {
            throw new MissingResourceException("the image writer is not set", "Camera", "Camera");
        }
        imageWriter.writeToImage();
        return this;
    }

    /**
     * Move camera camera.
     *
     * @param direction the direction
     * @param distance  the distance
     * @return the camera
     */
    public Camera moveCamera(Vector direction, double distance) {
        this.point = this.point.add(direction.scale(distance));
        return this;
    }

    /**
     * Move camera and point wiew camera.
     *
     * @param newPosition  the new position
     * @param newWiewPoint the new wiew point
     * @param angle        the angle
     * @return the camera
     */
    public Camera moveCameraAndPointWiev(Point newPosition, Point newWiewPoint, double angle) {
        this.point =newPosition;

        if (!newWiewPoint.equals(point)) {

            this.Vto = newWiewPoint.subtract(this.point).normalize();
            this.Vup = VUP;
            this.Vright = Vto.crossProduct(Vup).normalize();
            rotateCameraByVto(angle);
        }
        return this;
    }
    // rotate the camera by the angle around the vector Vto (the vector from the camera to the point of view)
    private void rotateCameraByVto(double angle) {
        double radians = Math.toRadians(angle);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        this.Vup = new Vector(Vup.getX() * cos - Vup.getY() * sin, Vup.getX() * sin + Vup.getY() * cos,
                Vup.getZ() * cos - Vup.getZ() * sin).normalize();
        this.Vright = this.Vto.crossProduct(this.Vup).normalize();

    }

    /**
     * Rotate camera camera.
     *
     * @param angle the angle
     * @return the camera
     */
    public Camera rotateCamera(double angle) {
        rotateCameraByVto(angle);
        return this;
    }

    /**
     * Zoom camera camera.
     *
     * @param factor the factor
     * @return the camera
     */
// zoom in or out the camera by the factor
    public Camera zoomCamera(double factor) {
        this.distance *= factor;
        return this;

    }

    /**
     * Sets multithreading.
     *
     * @param i the
     * @return the multithreading
     */
    public Camera setMultithreading(int i) {
        this.threadsCount = i;
        return this;
    }

    /**
     * Sets debug print.
     *
     * @param v the v
     * @return the debug print
     */
    public Camera setDebugPrint(double v) {
        printInterval = v;
        return this;
    }
}




