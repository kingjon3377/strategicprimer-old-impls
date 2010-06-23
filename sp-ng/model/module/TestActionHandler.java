package model.module;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import model.building.SimpleBuilding;
import model.map.TerrainObject;
import model.map.Tile;
import model.unit.Harvester;
import model.unit.SimpleUnit;
import model.unit.UnitAction;

import org.junit.Before;
import org.junit.Test;

/**
 * A class to test action handling.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class TestActionHandler {
	/**
	 * Constructor, to make warnings go away.
	 */
	public TestActionHandler() {
		setUp();
	}

	/**
	 * The object we're testing.
	 */
	private ActionHandler handler;
	/**
	 * A first Tile for testing.
	 */
	private Tile tileOne;
	/**
	 * A second Tile for testing.
	 */
	private Tile tileTwo;
	/**
	 * Set up for the tests.
	 */
	@Before
	public void setUp() {
		handler = new ActionHandler();
	}
	/**
	 * Tests that movement works properly. TODO: pathfinding
	 */
	@Test
	public void testMovement() {
		tileOne = new Tile();
		tileTwo = new Tile();
		tileOne.setModule(new SimpleUnit(0));
		handler.runAction(UnitAction.Move, tileOne, null);
		assertTrue(tileOne.getModule() instanceof SimpleUnit);
		handler.runAction(UnitAction.Move, tileTwo, tileOne);
		assertTrue(tileOne.getModule() instanceof SimpleUnit);
		assertEquals(null, tileTwo.getModule());
		handler.runAction(UnitAction.Move, tileOne, tileTwo);
		assertTrue(tileTwo.getModule() instanceof SimpleUnit);
		assertEquals(null, tileOne.getModule());
	}
	/**
	 * Test harvesting.
	 */
	@Test
	public void testHarvest() {
		tileOne = new Tile();
		tileTwo = new Tile();
		Harvester harv = new Harvester(0);
		harv.setBurden(3);
		tileOne.setModule(harv);
		handler.runAction(UnitAction.Harvest, tileOne, null);
		assertEquals(3, harv.getBurden());
		handler.runAction(UnitAction.Harvest, tileOne, tileTwo);
		assertEquals(3, harv.getBurden());
		tileTwo.setObject(TerrainObject.TREE);
		handler.runAction(UnitAction.Harvest, tileOne, tileTwo);
		assertEquals(4, harv.getBurden());
		assertEquals(TerrainObject.NOTHING, tileTwo.getObject());
	}
	/**
	 * Test unloading.
	 */
	@Test
	public void testUnload() {
		tileOne = new Tile();
		tileTwo = new Tile();
		Harvester harv = new Harvester(0);
		harv.setBurden(3);
		tileOne.setModule(harv);
		handler.runAction(UnitAction.Unload, tileOne, null);
		assertEquals(3, harv.getBurden());
		handler.runAction(UnitAction.Unload, tileOne, tileTwo);
		assertEquals(3, harv.getBurden());
		tileTwo.setModule(new SimpleBuilding(0));
		handler.runAction(UnitAction.Unload, tileOne, tileTwo);
		assertEquals(2, harv.getBurden());
	}
}
