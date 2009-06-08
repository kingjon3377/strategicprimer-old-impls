package finalproject;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import finalproject.astar.Location;
import finalproject.astar.Tile;

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
public class MapTest extends TestCase { // NOPMD
	/**
	 * The second dimension of the test map; to make a "magic number" warning go
	 * away.
	 */
	private static final int TESTMAP_2D_DIM = 6;
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
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		final List<List<Tile>> tiles = new ArrayList<List<Tile>>();
		for (int i = 0; i < 5; i++) {
			tiles.set(i, new ArrayList<Tile>());// NOPMD
			for (int j = 0; j < TESTMAP_2D_DIM; j++) {
				tiles.get(i).set(j, new Tile(new Location(i, j),// NOPMD
						((i * TESTMAP_2D_DIM) + j) % SPMap.MAX_TERRAIN_TYPE));
			}
		}
		map = new SPMap(5, TESTMAP_2D_DIM, tiles);
		for (int j = 0; j < TESTMAP_2D_DIM; j++) {
			map.getTile(new Location(2, j)).setModuleOnTile(new SimpleUnit());//NOPMD
			map.getTile(new Location(2, j)).getModuleOnTile().setLocation(//NOPMD
					map.getTile(new Location(2, j)));//NOPMD
		}
		map.getTile(new Location(3, 3)).setModuleOnTile(new SimpleUnit());
		map.getTile(new Location(3, 3)).getModuleOnTile().setLocation(
				map.getTile(new Location(3, 3)));
		map.getTile(new Location(3, 3)).getModuleOnTile().setSpeed(5);
	}
	/**
	 * Test the constructor: negative X
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNegativeX() {
		map = new SPMap(-2, 3, new ArrayList<List<Tile>>());
		fail("SPMap() accepted negative X dimension");
	}
	/**
	 * Test the constructor: negative Y
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNegativeY() {
		map = new SPMap(1, -4, new ArrayList<List<Tile>>());
		fail("SPMap() accepted negative Y dimension");
	}
	/**
	 * Test the constructor: null map
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullMap() {
		map = new SPMap(5, TESTMAP_2D_DIM, null);
		fail("SPMap() accepted null map");
	}
	/**
	 * Test the constructor: different X
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorDifferentX() {
		map = new SPMap(2, 3, SPMap.createArray(1, 3));
		fail("SPMap() accepted map with different X dimension than specified");
	}
	/**
	 * Test the constructor: negative Y
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorDifferentY() {
		map = new SPMap(2, 3, SPMap.createArray(2, 4));
		fail("SPMap() accepted map with different Y dimension than specified");
	}
	/**
	 * Test the constructor
	 */
	@Test
	public void testConstructor() {
		try {
			map = new SPMap(2, 3, SPMap.createArray(2, 3));
		} catch (IllegalArgumentException e) {
			fail("SPMap() choked on legal input");
		}
	}
	/**
	 * Tests the getTile() method: negative X
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testGetTileNegativeX() {
		map.getTile(new Location(-1, 2));
		fail("getTile() accepted negative X coordinate");
	}
	/**
	 * Tests the getTile() method: too high X
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testGetTileTooHighX() {
		map.getTile(new Location(10, 2));
		fail("getTile() accepted too-high X coordinate");
	}
	/**
	 * Tests the getTile() method: negative Y
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testGetTileNegativeY() {
		map.getTile(new Location(3, -2));
		fail("getTile() accepted negative Y coordinate");
	}
	/**
	 * Tests the getTile() method: too high Y
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testGetTileTooHighY() {
		map.getTile(new Location(4, 20));
		fail("getTile() accepted too-high Y coordinate");
	}
	/**
	 * Tests the getTile() method
	 */
	@Test
	public void testGetTile() {
		try {
			map.getTile(new Location(3, 5));
		} catch (IllegalArgumentException e) {
			fail("getTile() choked on legal input");
		}
	}

	/**
	 * Tests the checkPath() method.
	 */
	@Test
	public void testCheckPath() {
		assertFalse("Can't go to occupied tile", map.checkPath(map
				.getTile(new Location(3, 3)), map.getTile(new Location(1, 1)),
				map.getTile(new Location(3, 3)).getModuleOnTile().getSpeed(),
				map.getTile(new Location(3, 3)).getModuleOnTile()));
		assertFalse("Can't go to own tile", map.checkPath(map
				.getTile(new Location(3, 3)), map.getTile(new Location(3, 3)),
				map.getTile(new Location(3, 3)).getModuleOnTile().getSpeed(),
				map.getTile(new Location(3, 3)).getModuleOnTile()));
		assertTrue("Can go to nearby unoccupied tile", map.checkPath(map
				.getTile(new Location(3, 3)), map.getTile(new Location(4, 5)),
				map.getTile(new Location(3, 3)).getModuleOnTile().getSpeed(),
				map.getTile(new Location(3, 3)).getModuleOnTile()));
		assertFalse("Why can't go here?", map.checkPath(map
				.getTile(new Location(3, 3)), map.getTile(new Location(2, 4)),
				map.getTile(new Location(3, 3)).getModuleOnTile().getSpeed(),
				map.getTile(new Location(3, 3)).getModuleOnTile()));
	}
}
