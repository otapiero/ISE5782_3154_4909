package primitives;

public class Vector extends Point{

    public Vector(Double3 xyz) {
        super(xyz);
        if (this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("wrong input format ,Vector Zero.");
    }

    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("wrong input format ,Vector Zero.");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Vector)) return false;
        return super.equals(obj);
    }


    public Vector add(Vector other){
        return new Vector(this.xyz.add(other.xyz));
    }
    public Vector scale(double rhs){
        return new Vector(xyz.scale(rhs));
    }
    public double dotProduct(Vector other){
        Double3 temp=this.xyz.product(other.xyz);
        return temp.d1+temp.d2+temp.d3;
    }
    public Vector crossProduct(Vector other) {
        return new Vector(
                this.xyz.d2 * other.xyz.d3 - this.xyz.d3 * other.xyz.d2,
                this.xyz.d3 * other.xyz.d1 - this.xyz.d1 * other.xyz.d3,
                this.xyz.d1 * other.xyz.d2 - this.xyz.d2 * other.xyz.d1);

    }
    public double lengthSquared(){
        return this.distanceSquared(new Point(xyz.ZERO));
    }
    public double length(){
        return Math.sqrt( this.lengthSquared());
    }
    public Vector normalize() {
        return new Vector(this.xyz.reduce(length()));
    }

    @Override
    public String toString() {
        return "Vector{" +
                "xyz=" + xyz +
                '}';
    }
}
