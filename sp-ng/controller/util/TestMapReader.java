package controller.util;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import model.map.SPMap;

import org.junit.Before;
import org.junit.Test;

public class TestMapReader {
	private MapReader reader;
	@Before
	public void setUp() {
		reader = new MapReader();
	}
	@Test(expected=IllegalArgumentException.class)
	public void testRejectsZeroHeightMap() throws IOException {
		reader.readMap(new BufferedReader(new StringReader("0 1")));
	}
	@Test(expected=IllegalArgumentException.class)
	public void testRejectsZeroWidthMap() throws IOException {
		reader.readMap(new BufferedReader(new StringReader("2 0")));
	}
	@Test(expected=Exception.class)
	public void testRejectsZeroLengthInput() throws IOException {
		reader.readMap(new BufferedReader(new StringReader("")));
	}
	@Test(expected=Exception.class)
	public void testRejectsTooShortMap() throws IOException {
		reader.readMap(new BufferedReader(new StringReader("1 1")));
	}
	@Test
	public void testReturnsProperResult() throws IOException {
		SPMap map = new SPMap(new int[][] {{1,2},{3,4}});
		assertTrue(map.equals(reader.readMap(new BufferedReader(new StringReader("2 2 1 2 3 4")))));
		assertEquals(map,reader.readMap(new BufferedReader(new StringReader("2 2 1 2 3 4"))));
	}
}
