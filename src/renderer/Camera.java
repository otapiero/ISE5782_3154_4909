package renderer;

import primitives.*;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Camera {
    Point point;
    Vector Vright,Vup,Vto;
    double height, distance,width;

    public Point getPoint() {
        return point;
    }

    public Vector getVright() {
        return Vright;
    }

    public Vector getVup() {
        return Vup;
    }

    public Vector getVto() {
        return Vto;
    }

    public double getHeight() {
        return height;
    }

    public double getDistance() {
        return distance;
    }
    public double getWidth() {
        return width;
    }

    public Camera(Point point, Vector vto,Vector vup) {

        if (!isZero(vto.dotProduct(vup))) throw new IllegalArgumentException("the vectors are not orthogonal");
        Vup = vup.normalize();
        Vto = vto.normalize();
        Vright=Vto.crossProduct(Vup).normalize();
        this.point=point;

    }
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }
    public Camera setVPDistance(double distance){
        this.distance=distance;
        return this;
    }
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


}
