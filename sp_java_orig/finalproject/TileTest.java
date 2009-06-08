package finalproject;

import junit.framework.TestCase;

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
	public void setUp() throws Exception {
		super.setUp();
		tile = new Tile(1, 2, Tile.TERRAIN_FOREST);
		tile2 = new Tile(3, 5, Tile.TERRAIN_OCEAN);
		tile3 = new Tile(6, 4, Tile.TERRAIN_PLAIN);
		tile4 = new Tile(7, 12, 2);
	}
	/**
	 * Test the Tile class's constructor.
	 */
	public void testConstructor() {
		try {
			// ESCA-JAVA0076:
			tile = new Tile(-1, 2, 10);
			fail("Tile() didn't catch negative X coordinate");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		try {
			// ESCA-JAVA0076:
			tile = new Tile(3, -4, 9);
			fail("Tile() didn't catch negative Y coordinate");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		try {
			// ESCA-JAVA0076:
			tile = new Tile(5, 8, 18);
			fail("Tile() didn't catch too-high terrain type");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		try {
			tile = new Tile(10, 9, -2);
			fail("Tile() didn't catch negative terrain type");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		try {
			tile = new Tile(8, 5, 14);
		} catch (IllegalArgumentException e) {
			fail("Tile() objected to unobjectionable input");
		}
	}
	/**
	 * Test the defense-bonus stat of various terrains.
	 */
	public void testGetDefenseBonus() {
		assertEquals("A forest tile has a defense bonus", 5, tile.getTerrainDefenseBonus());
		assertEquals("An ocean tile has no defense bonus", 0, tile2.getTerrainDefenseBonus());
		assertEquals("A plains tile has no defense bonus", 0, tile3.getTerrainDefenseBonus());
		assertEquals("A coastal tile has no defense bonus", 0, tile4.getTerrainDefenseBonus());
	}
	/**
	 * Test the movement cost of various terrains
	 */
	public void testGetMovementCost() {
		assertEquals("A forest tile's movement cost", 2, tile.getMovementCost());
		assertEquals("An ocean tile's movement cost", 3, tile2.getMovementCost());
		assertEquals("A plains tile's movement cost", 1, tile3.getMovementCost());
		assertEquals("A coastal tile's movement cost", 1, tile4.getMovementCost());
	}
	/**
	 * Test  the cover bonus of various terrains
	 */
	public void testGetCoverBonus() {
		assertEquals("A forest tile has cover", 0.2, tile.getCoverBonus(), NEGLIGIBLE);
		assertEquals("An ocean tile has no cover", 0.0, tile2.getCoverBonus(), NEGLIGIBLE);
		assertEquals("A plains tile has no cover", 0.0, tile3.getCoverBonus(), NEGLIGIBLE);
		assertEquals("A coastal tile has no cover", 0.0, tile4.getCoverBonus(), NEGLIGIBLE);
	}
	/**
	 * Test setting and getting the amount of resources on a tile.
	 */
	public void testResourcesOnTile() {
		try {
			tile.setResourceOnTile(-2);
			fail("setResourceOnTile() didn't catch negative resource value");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
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
