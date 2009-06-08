package finalproject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import finalproject.astar.Location;
import finalproject.astar.Tile;

// ESCA-JAVA0137:
/**
 * Test class for the Strategic Primer/Yudexen model's main Game class.
 * 
 * @author Jonathan Lovelace
 * @assignment Final Project
 * @course CS108A
 * @semester FA06
 * 
 */
public class GameTest extends TestCase {
	/**
	 * A logger to replace printStackTrace().
	 */
	private static final Logger LOGGER = Logger.getLogger(GameTest.class.getName());
	/**
	 * The second dimension of the test map; to make a "magic number" warning go
	 * away.
	 */
	private static final int TESTMAP_2D_DIM = 6;
	/**
	 * The Game object we're testing
	 */
	private Game game;
	/**
	 * Set up for tests.
	 */
	@Override
	@Before
	public void setUp() {
		// ESCA-JAVA0166:
		try {
			super.setUp();
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Exception from super.setUp()", e);
		}
		final List<List<Tile>> tiles = new ArrayList<List<Tile>>();
		for (int i = 0; i < 5; i++) {
			tiles.set(i, new ArrayList<Tile>()); // NOPMD
			for (int j = 0; j < TESTMAP_2D_DIM; j++) {
				tiles.get(i).set(j, new Tile(new Location(i,j),2)); // NOPMD
			}
		}

		game = Game.getGame(new SPMap(5, TESTMAP_2D_DIM, tiles),
				new ArrayList<SimpleModule>(), 2);
		for (int i = 0; i < TESTMAP_2D_DIM; i++) {
			tiles.get(2).get(i).setModuleOnTile(new SimpleUnit()); // NOPMD
			tiles.get(2).get(i).getModuleOnTile().setLocation(tiles.get(2).get(i));
			game.getModules().add(tiles.get(2).get(i).getModuleOnTile());
			tiles.get(2).get(i).getModuleOnTile().setOwner(1);
			tiles.get(2).get(i).setResourceOnTile(1);
		}
	}
	/**
	 * Tests setMode()
	 */
	@Test
	public void testSetModeLegal() {
		try {
			game.setMode(Game.NO_MODE);
			game.setMode(Game.ATTACK_MODE);
			game.setMode(Game.INFO_MODE);
			game.setMode(Game.MOVE_MODE);
			game.setMode(Game.BUILD_MODE);
			game.setMode(Game.RANGED_ATK_MODE);
		} catch (IllegalArgumentException e) {
			fail("setMode() choked on legal input");
		}
	}
	/**
	 * Tests setMode()'s handling of illegal input
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetModeIllegal() {
		game.setMode(15);
		fail("Didn't catch illegal mode");
	}
	/**
	 * Tests endTurn()
	 */
	@Test
	public void testEndTurn() {
		final int modulesCount = game.getModules().size();
		final SimpleModule module = game.getModules().get(0); 
		final SimpleModule moduleTwo = game.getModules().get(1);
		module.setHasAttacked(true);
		module.setHasMoved(true);
		moduleTwo.setHP(0);
		moduleTwo.takeAttack(game.getModules().get(0));
		game.setMode(Game.BUILD_MODE);
		game.endTurn();
		assertEquals("Current player increments",2, game.getPlayer());
		assertEquals("Starts in no mode",Game.NO_MODE, game.getMode());
		assertEquals("Deleted modules are pruned",modulesCount - 1, game.getModules().size());
		assertFalse("hasAttacked is reset",game.getModules().get(0).isHasAttacked());
		assertFalse("hasMoved is reset",game.getModules().get(0).isHasMoved());
		game.endTurn();
		assertEquals("resources increment",4, game.getPlayerResources(1));
	}
	/**
	 * Tests setPlayerResources()'s handling of negative amounts.
	 */
	@Test(expected=IllegalArgumentException.class) 
	public void testSetPlayerResourcesNegative() {
		game.setPlayerResources(-1, 1);
		fail("setPlayerResources() didn't catch negative amount");
	}
	/**
	 * Tests setPlayerResources()'s handling of zero playerID.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetPlayerResourcesZeroID() {
		game.setPlayerResources(1, 0);
		fail("setPlayerResources() didn't catch zero playerID");
	}
	/**
	 * Tests setPlayerResources()'s handling of too-high playerID
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetPlayerResourcesTooHighID() {
		game.setPlayerResources(1, 3);
		fail("setPlayerResources() didn't catch too-high playerID");
	}
	/**
	 * Tests setPlayerResources
	 */
	@Test
	public void testSetPlayerResources() {
		try {
			game.setPlayerResources(1, 1);
		} catch (IllegalArgumentException e) {
			fail("setPlayerResources() choked on legal input");
		}
	}
}
