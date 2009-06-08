package finalproject;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

// ESCA-JAVA0137:
/**
 * Test class for the Strategic Primer/Yudexen model's Map class, SPMap.
 * 
 * @author Jonathan Lovelace
 * @assignment Final Project
 * @course CS108A
 * @semester FA06
 * 
 */
public class MapTest extends TestCase {
	/**
	 * The second dimension of the test map; to make a "magic number" warning go
	 * away.
	 */
	private static final int TESTMAP_SECOND_DIM = 6;
	/**
	 * The map we're testing
	 */
	private SPMap map;

	/**
	 * Set up for the tests
	 * 
	 * @throws Exception
	 *             when the superclass method does.
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		final List<List<Tile>> tiles = new ArrayList<List<Tile>>();
		for (int i = 0; i < 5; i++) {
			tiles.set(i, new ArrayList<Tile>());
			for (int j = 0; j < TESTMAP_SECOND_DIM; j++) {
				tiles.get(i).set(
						j,
						new Tile(i, j, ((i * TESTMAP_SECOND_DIM) + j)
								% SPMap.MAX_TERRAIN_TYPE));
			}
		}
		map = new SPMap(5, TESTMAP_SECOND_DIM, tiles);
		for (int j = 0; j < TESTMAP_SECOND_DIM; j++) {
			map.getTile(2, j).setModuleOnTile(new SimpleUnit());
			map.getTile(2, j).getModuleOnTile().setLocation(map.getTile(2, j));
		}
		map.getTile(3, 3).setModuleOnTile(new SimpleUnit());
		map.getTile(3, 3).getModuleOnTile().setLocation(map.getTile(3, 3));
		map.getTile(3, 3).getModuleOnTile().setSpeed(5);
	}

	/**
	 * Test the constructor
	 */
	public void testConstructor() {
		try {
			map = new SPMap(-2, 3, new ArrayList<List<Tile>>());
			fail("SPMap() accepted negative X dimension");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		try {
			map = new SPMap(1, -4, new ArrayList<List<Tile>>());
			fail("SPMap() accepted negative Y dimension");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		try {
			map = new SPMap(5, TESTMAP_SECOND_DIM, null);
			fail("SPMap() accepted null map");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		try {
			map = new SPMap(2, 3, SPMap.createArray(1, 3));
			fail("SPMap() accepted map with different X dimension than specified");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		try {
			map = new SPMap(2, 3, SPMap.createArray(2, 4));
			fail("SPMap() accepted map with different Y dimension than specified");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		try {
			map = new SPMap(2, 3, SPMap.createArray(2, 3));
		} catch (IllegalArgumentException e) {
			fail("SPMap() choked on legal input");
		}
	}

	/**
	 * Tests the getTile() method
	 */
	public void testGetTile() {
		try {
			map.getTile(-1, 2);
			fail("getTile() accepted negative X coordinate");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		try {
			map.getTile(10, 2);
			fail("getTile() accepted too-high X coordinate");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		try {
			map.getTile(3, -2);
			fail("getTile() accepted negative Y coordinate");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		try {
			map.getTile(4, 20);
			fail("getTile() accepted too-high Y coordinate");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		try {
			map.getTile(3, 5);
		} catch (IllegalArgumentException e) {
			fail("getTile() choked on legal input");
		}
	}

	/**
	 * Tests the checkPath() method.
	 */
	public void testCheckPath() {
		assertFalse("Can't go to occupied tile", map.checkPath(map
				.getTile(3, 3), map.getTile(1, 1), map.getTile(3, 3)
				.getModuleOnTile().getSpeed(), map.getTile(3, 3)
				.getModuleOnTile()));
		assertFalse("Can't go to own tile", map.checkPath(map.getTile(3, 3),
				map.getTile(3, 3), map.getTile(3, 3).getModuleOnTile()
						.getSpeed(), map.getTile(3, 3).getModuleOnTile()));
		assertTrue("Can go to nearby unoccupied tile", map.checkPath(map
				.getTile(3, 3), map.getTile(4, 5), map.getTile(3, 3)
				.getModuleOnTile().getSpeed(), map.getTile(3, 3)
				.getModuleOnTile()));
		assertFalse("Why can't go here?", map.checkPath(map.getTile(3, 3), map
				.getTile(2, 4), map.getTile(3, 3).getModuleOnTile().getSpeed(),
				map.getTile(3, 3).getModuleOnTile()));
	}
}
