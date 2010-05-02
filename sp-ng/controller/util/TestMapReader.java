package controller.util;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import model.map.SPMap;

import org.junit.Before;
import org.junit.Test;
/**
 * A class to test the MapReader class.
 * @author Jonathan Lovelace
 */
public final class TestMapReader {
	/**
	 * Constructor, to make a warning go away.
	 */
	public TestMapReader() {
		setUp();
	}
	/**
	 * The object we're testing.
	 */
	private MapReader reader;
	/**
	 * Set up for the tests.
	 */
	@Before
	public void setUp() {
		reader = new MapReader();
	}
	/**
	 * Tests that a map of zero height will be rejected. 
	 * @throws IOException "Thrown" by the method we're testing
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testRejectsZeroHeightMap() throws IOException {
		reader.readMap(new BufferedReader(new StringReader("0 1")));
	}
	/**
	 * Tests that a map of zero width will be rejected.
	 * @throws IOException "Thrown" by the method we're testing
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testRejectsZeroWidthMap() throws IOException {
		reader.readMap(new BufferedReader(new StringReader("2 0")));
	}
	/**
	 * Tests that an empty string input will be rejected.
	 * @throws IOException "Thrown" by the method we're testing
	 */
	@Test(expected=Exception.class)
	public void testRejectsZeroLengthInput() throws IOException {
		reader.readMap(new BufferedReader(new StringReader("")));
	}
	/**
	 * Tests that a too-short map will be rejected.
	 * @throws IOException "Thrown" by the method we're testing
	 */
	@Test(expected=Exception.class)
	public void testRejectsTooShortMap() throws IOException {
		reader.readMap(new BufferedReader(new StringReader("1 1")));
	}
	/**
	 * Tests that the reader actually produces the proper result.
	 * @throws IOException "Thrown" by the method we're testing
	 */
	@Test
	public void testReturnsProperResult() throws IOException {
		SPMap map = new SPMap(MapReader.createTiles(new int[][] { { 1, 2 },
				{ 3, 4 } }, new int[][] { { 0, 0 }, { 0, 0 } }, new int[][] {
				{ -1, -1 }, { -1, -1 } }));
		map.terrainAt(0, 0).setObject(0);
		assertEquals(map,reader.readMap(new BufferedReader(new StringReader("2 2 1 2 3 4 0 0 0 0 -1 -1 -1 -1 1 0 0 0"))));
	}
	/**
	 * Tests that the reader rejects a map without elevation data
	 * @throws IOException "Thrown" by the method we're testing
	 */
	@Test(expected=Exception.class)
	public void testRejectsWithoutElevation() throws IOException {
		reader.readMap(new BufferedReader(new StringReader("2 2 1 2 3 4")));
	}
	/**
	 * Tests that the reader rejects a map without water-table data
	 * @throws IOException "Thrown" by the method we're testing
	 */
	@Test(expected=Exception.class)
	public void testRejectsWithoutWaterTable() throws IOException {
		reader.readMap(new BufferedReader(new StringReader("2 2 1 2 3 4 0 0 0 0")));
	}
	/**
	 * Tests that the reader rejects a map without terrain-object data
	 * @throws IOException "Thrown" by the method we're testing
	 */
	@Test(expected=Exception.class)
	public void testRejectsWithoutObjects() throws IOException {
		reader.readMap(new BufferedReader(new StringReader("2 2 1 2 3 4 0 0 0 0 -1 -1 -1 -1")));
	}
	/**
	 * Tests that the reader rejects a map with fewer objects than it specifies
	 * @throws IOException "Thrown" by the method we're testing
	 */
	@Test(expected=Exception.class)
	public void testRejectsTooFewObjects() throws IOException {
		reader.readMap(new BufferedReader(new StringReader("2 2 1 2 3 4 0 0 0 0 -1 -1 -1 -1 1")));
	}
}
