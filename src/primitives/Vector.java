package primitives;

public class Vector extends Point{
    /**
     *constructor of vector by a given double3
     * @param xyz a double3 for the vector
     */
    public Vector(Double3 xyz) {
        super(xyz.d1, xyz.d2, xyz.d3);
        if (this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("wrong input format ,Vector Zero.");
    }

    /**
     *constructor of vector by 3 given double
     * @param x x coordinate
     * @param y y coordinate
     * @param z z coordinate
     */
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

    /**
     * add a vector to this vector to creat a new vector
     * @param other a second vector
     * @return new vector result of this+other
     */
    public Vector add(Vector other){
        return new Vector(this.xyz.add(other.xyz));
    }

    /**
     * divide the vector by a scalar to creat a new vector
     * @param rhs a scalar to divide the vector by it
     * @return new vector result of this/rhs
     */
    public Vector scale(double rhs){
        return new Vector(xyz.scale(rhs));
    }

    /**
     * dot product of this vector by an other vector
     * @param other second vector
     * @return new vector result of other*this
     */
    public double dotProduct(Vector other){
        Double3 temp=this.xyz.product(other.xyz);
        return temp.d1+temp.d2+temp.d3;
    }

    /**
     * cross product of this vector by an other vector
     * @param other second vector
     * @return new vector result of other*this
     */
    public Vector crossProduct(Vector other) {
        return new Vector(
                this.xyz.d2 * other.xyz.d3 - this.xyz.d3 * other.xyz.d2,
                this.xyz.d3 * other.xyz.d1 - this.xyz.d1 * other.xyz.d3,
                this.xyz.d1 * other.xyz.d2 - this.xyz.d2 * other.xyz.d1);

    }

    /**
     * the length of the vector squared
     * @return length of the vector squared
     */
    public double lengthSquared(){
        return this.distanceSquared(new Point(xyz.ZERO));
    }

    /**
     *  the length of the vector
     * @return length of the vector
     */
    public double length(){
        return Math.sqrt( this.lengthSquared());
    }

    /**
     * find the normal vector of this vector
     * @return new vector normalize
     */
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
