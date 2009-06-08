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
	private static final int TESTMAP_SECOND_DIM = 6;
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
			tiles.set(i, new ArrayList<Tile>());
			for (int j = 0; j < TESTMAP_SECOND_DIM; j++) {
				tiles.get(i).set(j, new Tile(i,j,2));
			}
		}

		game = new Game(new SPMap(5, TESTMAP_SECOND_DIM, tiles),
				new ArrayList<SimpleModule>(), 2);
		for (int i = 0; i < TESTMAP_SECOND_DIM; i++) {
			tiles.get(2).get(i).setModuleOnTile(new SimpleUnit());
			tiles.get(2).get(i).getModuleOnTile().setLocation(tiles.get(2).get(i));
			game.getModules().add(tiles.get(2).get(i).getModuleOnTile());
			tiles.get(2).get(i).getModuleOnTile().setOwner(1);
			tiles.get(2).get(i).setResourceOnTile(1);
		}
	}
	/**
	 * Tests setMode()
	 */
	public void testSetMode() {
		try {
			game.setMode(Game.NO_MODE);
			game.setMode(Game.ATTACK_MODE);
			game.setMode(Game.INFO_MODE);
			game.setMode(Game.MOVE_MODE);
			game.setMode(Game.BUILD_MODE);
			game.setMode(Game.RANGED_ATTACK_MODE);
		} catch (IllegalArgumentException e) {
			fail("setMode() choked on legal input");
		}
		try {
			game.setMode(15);
			fail("setMode() didn't catch invalid mode");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
	}
	/**
	 * Tests endTurn()
	 */
	public void testEndTurn() {
		int modulesCount = game.getModules().size();
		game.getModules().get(0).setHasAttacked(true);
		game.getModules().get(0).setHasMoved(true);
		game.getModules().get(1).setHP(0);
		game.getModules().get(1).takeAttack(game.getModules().get(0));
		game.setMode(Game.BUILD_MODE);
		game.endTurn();
		assertEquals("Current player increments",2, game.getPlayer());
		assertEquals("Starts in no mode",Game.NO_MODE, game.getMode());
		assertEquals("Deleted modules are pruned",modulesCount - 1, game.getModules().size());
		assertFalse("hasAttacked is reset",game.getModules().get(0).getHasAttacked());
		assertFalse("hasMoved is reset",game.getModules().get(0).getHasMoved());
		game.endTurn();
		assertEquals("resources increment",4, game.getPlayerResources(1));
	}
	/**
	 * Tests setPlayerResources
	 */
	public void testSetPlayerResources() {
		try {
			game.setPlayerResources(-1, 1);
			fail("setPlayerResources() didn't catch negative amount");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		try {
			game.setPlayerResources(1, 0);
			fail("setPlayerResources() didn't catch zero playerID");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		try {
			game.setPlayerResources(1, 3);
			fail("setPlayerResources() didn't catch too-high playerID");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		try {
			game.setPlayerResources(1, 1);
		} catch (IllegalArgumentException e) {
			fail("setPlayerResources() choked on legal input");
		}
	}
}
