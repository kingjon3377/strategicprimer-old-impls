package controller.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

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
}
