package primitives;

/**
 * The type Material.
 */
public class Material {
    /**
     * Sets kd.
     *
     * @param kD the k d
     * @return the kd
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Sets ks.
     *
     * @param kS the k s
     * @return the ks
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Sets kd.
     *
     * @param kD the k d
     * @return the kd
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Sets ks.
     *
     * @param kS the k s
     * @return the ks
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Sets r.
     *
     * @param kR the k r
     * @return the r
     */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * Sets t.
     *
     * @param kT the k t
     * @return the t
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * Sets shininess.
     *
     * @param nShininess the n shininess
     * @return the shininess
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     * The K d.
     */
    public Double3 kD=Double3.ZERO, /**
     * The K s.
     */
    kS=Double3.ZERO;
    /**
     * The K r.
     */
    public Double3 kR= Double3.ZERO, /**
     * The K t.
     */
    kT=Double3.ZERO;

    public Material setDiffusedAndGlossy(double diffusedAndGlossy) {
        this.diffusedAndGlossy = diffusedAndGlossy;
        return this;
    }

    public double getDiffusedAndGlossy() {
        return diffusedAndGlossy;
    }
    public double diffusedAndGlossy=0;

    /**
     * The N shininess.
     */
    public int nShininess=0;


}
