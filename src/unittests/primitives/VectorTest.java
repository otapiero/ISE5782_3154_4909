package unittests.primitives;

import org.junit.jupiter.api.Test;
import primitives.Vector;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class VectorTest {

    @Test
    void testAdd() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        assertEquals(new Vector(1-2,2-4,3-6),v1.add(v2),"wrong vector from add");
    }

    @Test
    void testScale() {
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(new Vector(1*3,2*3,3*3),v1.scale(3),"wrong vector from Scale");
    }

    @Test
    void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        assertEquals(-2 * 1 + -4 * 2 - 6 * 3,  v1.dotProduct(v2), 0.00001, "dotProduct() wrong result");
        assertEquals( 1*0+2*3+3*-2,  v1.dotProduct(v3), 0.00001, "dotProduct() for orthogonal vectors is not zero");
    }

    @Test
    void testCrossProduct() {

        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(
                IllegalArgumentException.class, () -> v1.crossProduct(v3), "crossProduct() for parallel vectors does not throw an exception");

    }

    @Test
    void testLengthSquared() {
        assertEquals(14,new Vector(1, 2, 3).lengthSquared(),0.0001,"bad LengthSquared");
    }


    @Test
    void testLength() {
        assertEquals(5,new Vector(0, 3, 4).length(),0.00001, "length() wrong value");
    }

    @Test
    void testNormalize() {

        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();
        assertEquals(1, u.length(), 0.00001, "the normalized vector is not a unit vector");
        assertThrows(IllegalArgumentException.class,
                () -> v.crossProduct(u), "the normalized vector is not parallel to the original one");
        assertFalse(v.dotProduct(u) < 0," the normalized vector is opposite to the original one");
        }

    }