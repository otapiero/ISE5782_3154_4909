/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Testing Polygons
 * 
 * @author Dan
 *
 */
public class PolygonTests {

	/**
	 * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
	 */
	@Test
	public void testConstructor() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct concave quadrangular with vertices in correct order
		try {
			new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct polygon");
		}

		// TC02: Wrong vertices order
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
				"Constructed a polygon with wrong order of vertices");

		// TC03: Not in the same plane
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
				"Constructed a polygon with vertices that are not in the same plane");

		// TC04: Concave quadrangular
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
						new Point(0.5, 0.25, 0.5)), //
				"Constructed a concave polygon");

		// =============== Boundary Values Tests ==================

		// TC10: Vertex on a side of a quadrangular
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0.5, 0.5)),
				"Constructed a polygon with vertix on a side");

		// TC11: Last point = first point
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
				"Constructed a polygon with vertice on a side");

		// TC12: Co-located points
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
				"Constructed a polygon with vertice on a side");

	}

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here
		Polygon pl = new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
		double sqrt3 = Math.sqrt(1d / 3);
		assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point(0, 0, 1)), "Bad normal to trinagle");
	}

	@Test
	void findGeoIntersectionsHelper() {
		// ============ Equivalence Partitions Tests ==============
		Polygon p = new Polygon(new Point(4, 4, 0), new Point(4, 4, 4),
        		new Point(-4, 4, 4), new Point(-4, 4, 0));
		// TC01: Ray intersects the polygon
		Ray r = new Ray(new Point(1, -5, 3), new Vector(0, 3, 0));
        List<Intersectable.GeoPoint> l = p.findGeoIntersections(r);
        List<Point> expectList = new ArrayList<Point>();
        expectList.add(new Point(1, 4, 3));
        assertEquals(expectList,List.of(l.get(0).point));

		// TC02: Ray does not intersect the polygon but intersects the plane against the edge
		r = new Ray(new Point(6, 3,2), new Vector(0, 1, 0));
	    assertNull(p.findGeoIntersections(r), "Ray intersects the plane but does not intersect the polygon");

		// TC03: Ray does not intersect the polygon but intersect the plane against the vertex
		r = new Ray(new Point(5, 3, 0), new Vector(0, 1, 0));
	    assertNull(p.findGeoIntersections(r), "Ray intersects the plane but does not intersect the polygon");

		// TC04: Ray does intersect the polygon in the vertex
		r = new Ray(new Point(4, 3, 0), new Vector(0, 1, 0));
		assertNull(p.findGeoIntersections(r), "Ray intersects the polygon on the vertex");

		// TC05: Ray does intersect the polygon in the edge
		r = new Ray(new Point(4, 3, 2), new Vector(0, 1, 0));
		assertNull(p.findGeoIntersections(r), "Ray intersects the polygon on the edge");

		// TC06: Ray does intersect the polygon in ege continuation
		r = new Ray(new Point(4, 3, 5), new Vector(0, 1, 0));
		assertNull(p.findGeoIntersections(r), "Ray intersects the polygon on the edge continuation");




	}
}
