package controller.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import model.map.SPMap;
import model.map.Tile;
import model.map.TileType;

/**
 * A class to read a map from file.
 * 
 * @author Jonathan Lovelace.
 * 
 *         Stream-handling code based vaguely on the EasyIO framework from
 *         <i>Java: An Introduction to Computing</i> by Adams, Nyhoff, and
 *         Nyhoff.
 * 
 */
public class MapReader {
	/**
	 * Read a map from a file.
	 * 
	 * @param filename
	 *            the file to read from
	 * @return the map in that file
	 * @throws FileNotFoundException if file not found
	 * @throws IOException on EOF or other I/O errors
	 */
	public SPMap readMap(final String filename) throws FileNotFoundException, IOException {
		return readMap(new BufferedReader(new FileReader(filename)));
	}

	/**
	 * Read the map from an input stream
	 * 
	 * @param istream
	 *            the stream we're reading from
	 * @throws IOException
	 *             on EOF or other I/O error
	 * @return the map described by the input
	 */
	public SPMap readMap(final BufferedReader istream)
			throws IOException {
		int rows = readValue(istream);
		if (rows < 1) {
			throw new IllegalArgumentException("Non-positive number of rows in the map");
		}
		int cols = readValue(istream);
		if (cols < 1) {
			throw new IllegalArgumentException("Non-positive number of columns in the map");
		}
		return new SPMap(createTiles(readArray(istream, rows, cols),readArray(istream,rows,cols), readArray(istream,rows,cols)));
	}

	/**
	 * Position the cursor before the next relevant character.
	 * 
	 * @param istream
	 *            the stream we're reading from
	 * @throws IOException
	 *             on EOF or other I/O error
	 */
	private static void prepare(final BufferedReader istream)
			throws IOException {
		while (true) {
			istream.mark(2);
			final int ch = istream.read();
			if (ch < 1) {
				break;
			} else if (!Character.isWhitespace((char) ch)) {
				istream.reset();
				break;
			}
		}
	}

	/**
	 * Read the next value. If our map file format adds something more than
	 * integers, we'll need to modularize this.
	 * 
	 * @param istream
	 *            the stream we're reading from
	 * @return the next (integer) value in it, which we've then passed by.
	 * @throws IOException
	 *             on EOF or other I/O error
	 */
	private static int readValue(final BufferedReader istream)
			throws IOException {
		prepare(istream);
		StringBuilder builder = new StringBuilder();
		while (true) {
			istream.mark(4);
			final int ch = istream.read();
			if (ch < 1 || Character.isWhitespace((char) ch)) {
				istream.reset();
				break;
			} else {
				builder.append((char) ch);
			}
		}
		return Integer.parseInt(builder.toString());
	}

	/**
	 * Read a two-dimensional array of integers.
	 * 
	 * @param istream
	 *            the stream we're reading from
	 * @param rows
	 *            how many rows there are to read
	 * @param cols
	 *            how many columns there are to read
	 * @throws IOException
	 *             on EOF or other I/O error
	 * @return the array
	 */
	private static int[][] readArray(final BufferedReader istream,
			final int rows, final int cols) throws IOException {
		final int[][] array = new int[rows][cols];
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				array[row][col] = readValue(istream);
			}
		}
		return array;
	}
	/**
	 * Turn arrays of ints into an array of TileTypes.
	 * @param terrain an array of ints representing terrain
	 * @param elevation an array of ints representing elevation
	 * @param waterLevels an array of ints representing water table levels
	 * @return the equivalent array of TileTypes
	 */
	public static Tile[][] createTiles(final int[][] terrain, final int[][] elevation, final int[][] waterLevels) {
		final Tile[][] tiles = new Tile[terrain.length][terrain[0].length];
		for (int i = 0; i < terrain.length; i++) {
			for (int j = 0; j < terrain[0].length; j++) {
				tiles[i][j] = new Tile(TileType.values()[terrain[i][j]],elevation[i][j], waterLevels[i][j]);
			}
		}
		return tiles;
	}
}
