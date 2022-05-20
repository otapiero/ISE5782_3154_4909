package geometries;


import primitives.*;
import primitives.Vector;

import java.util.*;
import java.util.List;

public class EiphelTower extends Geometry {
    private List<Geometry> geometries;

    private double distance;
    private static final double LEGS_DISTANCE = 0.14;
    private static final double LEGS_HEIGHT = 0.2d;
    private static final double BASE_HEIGHT = 1/12d;
    private static final double HEAD_HEIGHT = 1.2;
    private Vector diagonal1, diagonal2,depth,height,width;
    public EiphelTower(Point p0, Point p1, Point p2, Point p3) {
        distance = p1.distance(p0);
        diagonal1 =p2.subtract(p0).normalize();
        diagonal2 =p3.subtract(p1).normalize();
        width =p1.subtract(p0).normalize();
        depth =p3.subtract(p0).normalize();
        height = depth.crossProduct(width);

        geometries = new ArrayList<>();
        var p=baseTower(p0, p1, p2, p3);
        headTower(p.get(0),p.get(1),p.get(2),p.get(3));
    }

    private List<Point> baseTower(Point p0, Point p1, Point p2, Point p3){

       Point p4,p5,p6,p7;
       double baseHeight = distance*BASE_HEIGHT;

       var p= legsTower(p0, p1, p2, p3,LEGS_DISTANCE,LEGS_HEIGHT,1.3d);
       p4=p.get(0).add(height.scale(baseHeight)).add(diagonal1.scale(0.7));
       p5=p.get(1).add(height.scale(baseHeight)).add(diagonal2.scale(0.7));
       p6=p.get(2).add(height.scale(baseHeight)).add(diagonal1.scale(-0.7));
       p7=p.get(3).add(height.scale(baseHeight)).add(diagonal2.scale(-0.7));
       geometries.add(new Cube(p.get(0), p.get(1), p.get(2), p.get(3),p4,p5,p6,p7));



        p= legsTower(p4, p5, p6, p7,LEGS_DISTANCE*0.7,LEGS_HEIGHT*0.8,0.8);
        p4=p.get(0).add(diagonal1.scale(-0.02*distance));
        p5=p.get(1).add(diagonal2.scale(-0.02*distance));
        p6=p.get(2).add(diagonal1.scale(0.02*distance));
        p7=p.get(3).add(diagonal2.scale(0.02*distance));
        geometries.add(new Cube(p4, p5, p6, p7,
                p4.add(height.scale(baseHeight*0.7)),p5.add(height.scale(BASE_HEIGHT*distance*0.7)),
                p6.add(height.scale(baseHeight*0.7)), p7.add(height.scale(BASE_HEIGHT*distance*0.7))));
        p4=p.get(0).add(height.scale(baseHeight*2)).add(diagonal1.scale(1.5));
        p5=p.get(1).add(height.scale(baseHeight*2)).add(diagonal2.scale(1.5));
        p6=p.get(2).add(height.scale(baseHeight*2)).add(diagonal1.scale(-1.5));
        p7=p.get(3).add(height.scale(baseHeight*2)).add(diagonal2.scale(-1.5));
        geometries.add(new Cube(p.get(0).add(height.scale(baseHeight*0.5)), p.get(1).add(height.scale(baseHeight*0.5)),
                p.get(2).add(height.scale(baseHeight*0.5)), p.get(3).add(height.scale(baseHeight*0.5)),p4,p5,p6,p7));
        return List.of(p4,p5,p6,p7);
    }
    private void headTower(Point p0, Point p1, Point p2, Point p3){
        Point p4,p5,p6,p7;
        p4=p0.add(height.scale(HEAD_HEIGHT*distance*0.07)).add(diagonal1.scale(0.5));
        p5=p1.add(height.scale(HEAD_HEIGHT*distance*0.07)).add(diagonal2.scale(0.5));
        p6=p2.add(height.scale(HEAD_HEIGHT*distance*0.07)).add(diagonal1.scale(-0.5));
        p7=p3.add(height.scale(HEAD_HEIGHT*distance*0.07)).add(diagonal2.scale(-0.5));
        geometries.add(new Cube(p0, p1, p2, p3,p4,p5,p6,p7));
        p0=p4;
        p1=p5;
        p2=p6;
        p3=p7;

        p4=p0.add(height.scale(HEAD_HEIGHT*distance*0.09)).add(diagonal1.scale(0.25));
        p5=p1.add(height.scale(HEAD_HEIGHT*distance*0.09)).add(diagonal2.scale(0.25));
        p6=p2.add(height.scale(HEAD_HEIGHT*distance*0.09)).add(diagonal1.scale(-0.25));
        p7=p3.add(height.scale(HEAD_HEIGHT*distance*0.09)).add(diagonal2.scale(-0.25));
        geometries.add(new Cube(p0, p1, p2, p3,p4,p5,p6,p7));
        p0=p4;
        p1=p5;
        p2=p6;
        p3=p7;
        p4=p0.add(height.scale(HEAD_HEIGHT*distance*0.09)).add(diagonal1.scale(0.1));
        p5=p1.add(height.scale(HEAD_HEIGHT*distance*0.09)).add(diagonal2.scale(0.1));
        p6=p2.add(height.scale(HEAD_HEIGHT*distance*0.09)).add(diagonal1.scale(-0.1));
        p7=p3.add(height.scale(HEAD_HEIGHT*distance*0.09)).add(diagonal2.scale(-0.1));
        geometries.add(new Cube(p0, p1, p2, p3,p4,p5,p6,p7));
        p0=p4;
        p1=p5;
        p2=p6;
        p3=p7;

        p4=p0.add(height.scale(HEAD_HEIGHT*distance*0.5)).add(diagonal1.scale(1.2));
        p5=p1.add(height.scale(HEAD_HEIGHT*distance*0.5)).add(diagonal2.scale(1.2));
        p6=p2.add(height.scale(HEAD_HEIGHT*distance*0.5)).add(diagonal1.scale(-1.2));
        p7=p3.add(height.scale(HEAD_HEIGHT*distance*0.5)).add(diagonal2.scale(-1.2));
        geometries.add(new Cube(p0, p1, p2, p3,p4,p5,p6,p7));
    }
    private List<Point> legsTower(Point p00, Point p11, Point p22, Point p33,double legDistance,double legHeight,double factorDiagonal){
        Point center = p00.add(diagonal1.scale(p00.distance(p22)));
        Point p01, p02, p03, p10, p12, p13, p20, p21, p23, p30, p31, p32,p04,p05,p06,p07,p14,p15,p16,p17,p24,p25,p26,p27,p34,p35,p36,p37;
        legHeight=legHeight*distance;
        legDistance=legDistance*distance;

        p01 =p00.add(width.scale(legDistance));
        p03 =p00.add(depth.scale(legDistance));
        p02= p03.add(width.scale(legDistance));

        p10 =p11.add(width.scale(-legDistance));
        p12 =p11.add(depth.scale(legDistance));
        p13 =p12.add(width.scale(-legDistance));

        p21 =p22.add(depth.scale(-legDistance));
        p23 =p22.add(width.scale(-legDistance));
        p20 =p21.add(width.scale(-legDistance));

        p32 =p33.add(width.scale(legDistance));
        p30=p33.add(depth.scale(-legDistance));
        p31=p30.add(width.scale(legDistance));

        factorDiagonal=factorDiagonal*p00.distance(p01);

        p04 =p00.add(height.scale(legHeight)).add(diagonal1.scale(factorDiagonal));
        p05 =p01.add(height.scale(legHeight)).add(diagonal1.scale(factorDiagonal));
        p06 =p02.add(height.scale(legHeight)).add(diagonal1.scale(factorDiagonal));
        p07 =p03.add(height.scale(legHeight)).add(diagonal1.scale(factorDiagonal));

        p14 =p10.add(height.scale(legHeight)).add(diagonal2.scale(factorDiagonal));
        p15 =p11.add(height.scale(legHeight)).add(diagonal2.scale(factorDiagonal));
        p16 =p12.add(height.scale(legHeight)).add(diagonal2.scale(factorDiagonal));
        p17 =p13.add(height.scale(legHeight)).add(diagonal2.scale(factorDiagonal));

        p24 =p20.add(height.scale(legHeight)).add(diagonal1.scale(-factorDiagonal));
        p25 =p21.add(height.scale(legHeight)).add(diagonal1.scale(-factorDiagonal));
        p26 =p22.add(height.scale(legHeight)).add(diagonal1.scale(-factorDiagonal));
        p27 =p23.add(height.scale(legHeight)).add(diagonal1.scale(-factorDiagonal));


        p34 =p30.add(height.scale(legHeight)).add(diagonal2.scale(-factorDiagonal));
        p35 =p31.add(height.scale(legHeight)).add(diagonal2.scale(-factorDiagonal));
        p36 =p32.add(height.scale(legHeight)).add(diagonal2.scale(-factorDiagonal));
        p37 =p33.add(height.scale(legHeight)).add(diagonal2.scale(-factorDiagonal));


        geometries.add(new Cube(p00,p01,p02,p03,p04,p05,p06,p07));
        geometries.add(new Cube(p10,p11,p12,p13,p14,p15,p16,p17));
        geometries.add(new Cube(p20,p21,p22,p23,p24,p25,p26,p27));
        geometries.add(new Cube(p30,p31,p32,p33,p34,p35,p36,p37));
        double distanceBetweenLegs = p01.distance(p10);

        geometries.add(new Triangle(p07,p07.add(p03.subtract(p07).normalize().scale(1.5)),
               p05.add(depth.scale(distanceBetweenLegs/8))));
       geometries.add(new Triangle(p05,p05.add(p01.subtract(p05).normalize().scale(1.5)),
             p05.add(width.scale(distanceBetweenLegs/8))));

        geometries.add(new Triangle(p14,p14.add(p10.subtract(p14).normalize().scale(1.5)),
                p14.add(width.scale(-distanceBetweenLegs/8))));
        geometries.add(new Triangle(p16,p16.add(p12.subtract(p16).normalize().scale(1.5)),
                p16.add(depth.scale(distanceBetweenLegs/8))));

        geometries.add(new Triangle(p25,p25.add(p21.subtract(p25).normalize().scale(1.5)),
               p25.add(depth.scale(-distanceBetweenLegs/8))));
        geometries.add(new Triangle(p27,p27.add(p23.subtract(p27).normalize().scale(1.5)),
                p27.add(width.scale(-distanceBetweenLegs/8))));

        geometries.add(new Triangle(p36,p36.add(p32.subtract(p36).normalize().scale(1.5)),
               p36.add(width.scale(distanceBetweenLegs/8))));
        geometries.add(new Triangle(p34,p34.add(p30.subtract(p34).normalize().scale(1.5)),
               p36.add(depth.scale(-distanceBetweenLegs/8))));
        return List.of(p04,p15,p26,p37);
    }
    @Override
    public Vector getNormal(Point point) {
        List<Vector> normals = new ArrayList<>();
        Vector normal = null;
        for (var geometrie : geometries) {
            normal=geometrie.getNormal(point);
            if (normal != null) {
                normals.add(normal);
            }
        }
        if(normals.size()==0){
            return null;
        }
        else {
            return normals.get(0);
        }

    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> tempResult= null ;
        for (var i: geometries) {
            List<GeoPoint> points=i.findGeoIntersectionsHelper(ray,maxDistance);
            if (points!=null){
                if (tempResult==null){
                    tempResult=new LinkedList();
                }
                tempResult.addAll(points);
            }
        }

        List<GeoPoint> result=null;
        if (tempResult!= null) {
            result = new LinkedList();
            for (var i : tempResult) {
                result.add(new GeoPoint(this, i.point));

            }
        }
        return result;    }
}
