package edu.calvin.jsl7.finalproject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

// ESCA-JAVA0137:
/**
 * Test class for the Strategic Primer model.
 * 
 * FIXME: Add tests
 * @author Jonathan Lovelace
 * @assignment Final Project
 * @course CS108A
 * @semester FA06
 * 
 */
public class GameTest extends TestCase { // NOPMD
	/**
	 * A logger to replace printStackTrace().
	 */
	private static final Logger LOGGER = Logger.getLogger(GameTest.class.getName());
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
		final List<List<Tile> > tiles = new ArrayList<List<Tile> >();
		tiles.set(0, new ArrayList<Tile>());
		tiles.get(0).set(0, new Tile(0,0,1));
		tiles.get(0).set(1, new Tile(0,1,12));
		tiles.get(0).set(2, new Tile(0,2,4));
		tiles.get(1).set(0, new Tile(1,0,14));
		tiles.get(1).set(1, new Tile(1,1,16));
		tiles.get(1).set(2, new Tile(1,2,10));

		game = Game.getGame(new SPMap(2, 3, tiles), new ArrayList<SimpleModule>(), 3);
		game.getNumPlayers();
	}

	/**
	 * Tests the getNumPlayers method.
	 */
    @Test
    public void testGetNumPlayers() {
        assertEquals("A simple test to satisfy PMD", game.getNumPlayers(), 3);
    }
}
