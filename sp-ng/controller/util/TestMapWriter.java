package controller.util;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import model.building.SimpleBuilding;
import model.map.SPMap;
import model.map.TerrainObject;
import model.map.Tile;
import model.map.TileType;
import model.unit.Harvester;
import model.unit.SimpleUnit;

import org.junit.Before;
import org.junit.Test;

/**
 * A test class for the MapWriter class.
 * 
 * @author Jonathan Lovelace
 */
public class TestMapWriter {
	/**
	 * The object we're testing
	 */
	private MapWriter writer;
	/**
	 * What we want it to write to.
	 */
	private PrintWriter ostream;
	/**
	 * The underlying writer, which we'll need to get the data out of
	 */
	private StringWriter cstream;

	/**
	 * Set up for the tests
	 */
	@Before
	public void setUp() {
		writer = new MapWriter();
		cstream = new StringWriter();
		ostream = new PrintWriter(cstream);
	}

	/**
	 * To avoid warnings
	 */
	public TestMapWriter() {
		setUp();
	}

	/**
	 * Tests that a null map can't be written.
	 */
	@Test(expected = NullPointerException.class)
	public void testRejectsNullInput() {
		writer.writeMap(ostream, null);
	}

	/**
	 * Tests that a rather complex map is writable to a readable format.
	 * 
	 * @throws IOException
	 *             Might be thrown by the reading operation.
	 */
	@Test
	public void testWritingWorks() throws IOException {
		final SPMap map = new SPMap(
				new Tile[][] {
						{ new Tile(), new Tile(TileType.WATER) },
						{ new Tile(TileType.DESERT, 2),
								new Tile(TileType.PLAINS, 3, 1) } });
		map.terrainAt(0, 0).setObject(TerrainObject.TREE);
		map.terrainAt(0, 1).setModule(new SimpleUnit(0));
		map.terrainAt(1, 0).setModule(new SimpleBuilding(1));
		writer.writeMap(ostream, map);
		assertEquals(map, new MapReader().readMap(new BufferedReader(
				new StringReader(cstream.getBuffer().toString()))));
	}

	/**
	 * Tests that writing a simple map works.
	 * 
	 * @throws IOException
	 *             Might be thrown by the reading operation.
	 */
	public void testSimpleWritingWorks() throws IOException {
		final SPMap map = new SPMap(
				new Tile[][] {
						{ new Tile(), new Tile(TileType.WATER) },
						{ new Tile(TileType.DESERT, 2),
								new Tile(TileType.PLAINS, 3, 1) } });
		writer.writeMap(ostream, map);
		assertEquals(map, new MapReader().readMap(new BufferedReader(
				new StringReader(cstream.getBuffer().toString()))));
	}

	/**
	 * Tests that terrain objects are properly written.
	 * 
	 * @throws IOException
	 *             Might be thrown by the reading operation.
	 */
	@Test
	public void testWritingObjectsWorks() throws IOException {
		final SPMap map = new SPMap(
				new Tile[][] {
						{ new Tile(), new Tile(TileType.WATER) },
						{ new Tile(TileType.DESERT, 2),
								new Tile(TileType.PLAINS, 3, 1) } });
		map.terrainAt(0, 0).setObject(TerrainObject.TREE);
		writer.writeMap(ostream, map);
		assertEquals(map, new MapReader().readMap(new BufferedReader(
				new StringReader(cstream.getBuffer().toString()))));
	}

	/**
	 * Tests that writing units works
	 * 
	 * @throws IOException
	 *             Might be thrown by the reading operation.
	 */
	@Test
	public void testWritingUnitsWorks() throws IOException {
		final SPMap map = new SPMap(
				new Tile[][] {
						{ new Tile(), new Tile(TileType.WATER) },
						{ new Tile(TileType.DESERT, 2),
								new Tile(TileType.PLAINS, 3, 1) } });
		map.terrainAt(0, 1).setModule(new SimpleUnit(0));
		writer.writeMap(ostream, map);
		assertEquals(map, new MapReader().readMap(new BufferedReader(
				new StringReader(cstream.getBuffer().toString()))));
	}

	/**
	 * Tests that writing buildings works.
	 * 
	 * @throws IOException
	 *             Might be thrown by the reading operation.
	 */
	@Test
	public void testWritingBuildingsWorks() throws IOException {
		final SPMap map = new SPMap(
				new Tile[][] {
						{ new Tile(), new Tile(TileType.WATER) },
						{ new Tile(TileType.DESERT, 2),
								new Tile(TileType.PLAINS, 3, 1) } });
		map.terrainAt(1, 0).setModule(new SimpleBuilding(1));
		writer.writeMap(ostream, map);
		assertEquals(map, new MapReader().readMap(new BufferedReader(
				new StringReader(cstream.getBuffer().toString()))));
	}

	/**
	 * Test that writing Harvester units works.
	 * 
	 * @throws IOException
	 *             Might be thrown by the reading operation.
	 */
	@Test
	public void testWritingHarvesterWorks() throws IOException {
		final SPMap map = new SPMap(
				new Tile[][] {
						{ new Tile(), new Tile(TileType.WATER) },
						{ new Tile(TileType.DESERT, 2),
								new Tile(TileType.PLAINS, 3, 1) } });
		Harvester harv = new Harvester(1);
		harv.setBurden(3);
		map.terrainAt(0, 1).setModule(harv);
		writer.writeMap(ostream, map);
		assertEquals(map, new MapReader().readMap(new BufferedReader(
				new StringReader(cstream.getBuffer().toString()))));
	}
}
