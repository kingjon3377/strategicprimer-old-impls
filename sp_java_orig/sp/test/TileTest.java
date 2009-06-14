package sp.test;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import sp.model.astar.Location;
import sp.model.astar.Tile;


// ESCA-JAVA0137:
/**
 * Test class for the Strategic Primer/Yudexen model's Tile class.
 * 
 * @author Jonathan Lovelace
 * @assignment Final Project
 * @course CS108A
 * @semester FA06
 * 
 */
public class TileTest extends TestCase {
	/**
	 * The amount two "equal" doubles can differ
	 */
	private static final double NEGLIGIBLE = 1e-8;
	/**
	 * The first tile used for the test
	 */
	private Tile tile;
	/**
	 * The second tile used for the test
	 */
	private Tile tile2;
	/**
	 * The third tile used for the test
	 */
	private Tile tile3;
	/**
	 * The fourth tile used for the test
	 */
	private Tile tile4;

	/**
	 * Set up for each test
	 * @throws Exception Thrown by the superclass version
	 */
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		tile = new Tile(new Location(1, 2), Tile.TERRAIN_FOREST);
		tile2 = new Tile(new Location(3, 5), Tile.TERRAIN_OCEAN);
		tile3 = new Tile(new Location(6, 4), Tile.TERRAIN_PLAIN);
		tile4 = new Tile(new Location(7, 12), 2);
	}
	/**
	 * Test the Tile class's constructor: negative X
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNegativeX() {
		tile = new Tile(new Location(-1, 2), 5);
		fail("Tile() didn't catch negative X coordinate");
	}
	/**
	 * Test the Tile class's constructor: negative Y
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNegativeY() {
		tile = new Tile(new Location(3, -4), 4);
		fail("Tile() didn't catch negative Y coordinate");
	}
	/**
	 * Test the Tile class's constructor: too high terrain type
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorTooHighTerrain() {
		tile = new Tile(new Location(5, 4), 18);
		fail("Tile() didn't catch too-high terrain type");
	}
	/**
	 * Test the Tile class's constructor: negative terrain type
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNegativeTerrain() {
		tile = new Tile(new Location(10, 9), -2);
		fail("Tile() didn't catch negative terrain type");
	}
	/**
	 * Test the Tile class's constructor.
	 */
	@Test
	public void testConstructor() {
		try {
			tile = new Tile(new Location(8, 5), 14);
		} catch (IllegalArgumentException e) {
			fail("Tile() objected to unobjectionable input");
		}
	}
	/**
	 * Test the defense-bonus stat of various terrains.
	 */
	@Test
	public void testGetDefenseBonus() {
		assertEquals("A forest tile has a defense bonus", 5, tile.defenseBonus());
		assertEquals("An ocean tile has no defense bonus", 0, tile2.defenseBonus());
		assertEquals("A plains tile has no defense bonus", 0, tile3.defenseBonus());
		assertEquals("A coastal tile has no defense bonus", 0, tile4.defenseBonus());
	}
	/**
	 * Test the movement cost of various terrains
	 */
	@Test
	public void testGetMovementCost() {
		assertEquals("A forest tile's movement cost", 2, tile.getMovementCost());
		assertEquals("An ocean tile's movement cost", 3, tile2.getMovementCost());
		assertEquals("A plains tile's movement cost", 1, tile3.getMovementCost());
		assertEquals("A coastal tile's movement cost", 1, tile4.getMovementCost());
	}
	/**
	 * Test  the cover bonus of various terrains
	 */
	@Test
	public void testGetCoverBonus() {
		assertEquals("A forest tile has cover", 0.2, tile.coverBonus(), NEGLIGIBLE);
		assertEquals("An ocean tile has no cover", 0.0, tile2.coverBonus(), NEGLIGIBLE);
		assertEquals("A plains tile has no cover", 0.0, tile3.coverBonus(), NEGLIGIBLE);
		assertEquals("A coastal tile has no cover", 0.0, tile4.coverBonus(), NEGLIGIBLE);
	}
	/**
	 * Test setting the resources on a tile: negative resource value
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testResourcesOnTileNegative() {
		tile.setResourceOnTile(-2);
		fail("setResourceOnTile() didn't catch negative resource value");
	}
	/**
	 * Test setting and getting the amount of resources on a tile.
	 */
	@Test
	public void testResourcesOnTile() {
		try {
			tile.setResourceOnTile(0);
		} catch (IllegalArgumentException e) {
			fail("setResourceOnTile() didn't accept zero resource value");
		} try {
			tile.setResourceOnTile(2);
		} catch (IllegalArgumentException e) {
			fail("SetResourceOnTile() didn't accept positive resource value");
		}
		assertEquals("Getting the stored resource value", 2,tile.getResourceOnTile());

	}
}
