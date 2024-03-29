package sp.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import sp.model.Game;
import sp.model.IModule;
import sp.model.Module;
import sp.model.SPMap;
import sp.model.SimpleUnit;
import sp.model.Weapon;
import sp.model.astar.Tile;

// ESCA-JAVA0137:
/**
 * Test class for the Strategic Primer/Yudexen model's main Game class. TODO: Extend this to test it more
 * thoroughly. FIXME: Actually write tests.
 * 
 * @author Jonathan Lovelace
 * @assignment Final Project
 * @course CS108A
 * @semester FA06
 * 
 */
public final class GameTest extends TestCase implements Serializable { // NOPMD
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = -8607900239504581848L;
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
	 * @throws Exception Thrown by super.setUp()
	 */
	@Override
	@Before
	public void setUp() throws Exception {
			super.setUp();
		final List<List<Tile>> tiles = SPMap.createArray(5, TESTMAP_2D_DIM, 2);

		game = Game.getGame(new SPMap(5, TESTMAP_2D_DIM, tiles),
				new ArrayList<IModule>(), 2);
		for (int i = 0; i < TESTMAP_2D_DIM; i++) {
			tiles.get(2).get(i).setModuleOnTile(new SimpleUnit()); // NOPMD
			tiles.get(2).get(i).getModuleOnTile().setLocation(tiles.get(2).get(i));
			game.addModule(tiles.get(2).get(i).getModuleOnTile());
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
			game.setMode(Game.Mode.NO_MODE);
			game.setMode(Game.Mode.ATTACK_MODE);
			game.setMode(Game.Mode.INFO_MODE);
			game.setMode(Game.Mode.MOVE_MODE);
			game.setMode(Game.Mode.BUILD_MODE);
			game.setMode(Game.Mode.RANGED_ATTACK_MODE);
		} catch (IllegalArgumentException e) {
			fail("setMode() choked on legal input");
		}
	}
	/**
	 * Tests endTurn()
	 */
	@Test
	public void testEndTurn() {
		final int modulesCount = game.getModules().size();
		final Module module = ((Module) game.getModules().get(0)); 
		final Module moduleTwo = ((Module) game.getModules().get(1));
		module.setHasAttacked(true);
		module.setHasMoved(true);
		moduleTwo.setHitPoints(0);
		moduleTwo.takeAttack((Weapon)module);
		game.setMode(Game.Mode.BUILD_MODE);
		game.endTurn();
		assertEquals("Current player increments",2, game.getPlayer());
		assertEquals("Starts in no mode",Game.Mode.NO_MODE, game.getMode());
		assertEquals("Deleted modules are pruned",modulesCount - 1, game.getModules().size());
		assertFalse("hasAttacked is reset",((Module)game.getModules().get(0)).isHasAttacked());
		assertFalse("hasMoved is reset",((Module)game.getModules().get(0)).isHasMoved());
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
