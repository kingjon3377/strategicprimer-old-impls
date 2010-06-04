package controller.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import model.building.SimpleBuilding;
import model.map.SPMap;
import model.map.TerrainObject;
import model.map.Tile;
import model.map.TileType;
import model.unit.Harvester;
import model.unit.SimpleUnit;

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
	 * @throws FileNotFoundException
	 *             if file not found
	 * @throws IOException
	 *             on EOF or other I/O errors
	 */
	public SPMap readMap(final String filename) throws FileNotFoundException,
			IOException {
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
	public SPMap readMap(final BufferedReader istream) throws IOException {
		int rows = readValue(istream);
		if (rows < 1) {
			throw new IllegalArgumentException(
					"Non-positive number of rows in the map");
		}
		int cols = readValue(istream);
		if (cols < 1) {
			throw new IllegalArgumentException(
					"Non-positive number of columns in the map");
		}
		return addObjs(
				new SPMap(createTiles(readArray(istream, rows, cols),
						readArray(istream, rows, cols), readArray(istream,
								rows, cols))), istream);
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
	 * 
	 * @param terrain
	 *            an array of ints representing terrain
	 * @param elevation
	 *            an array of ints representing elevation
	 * @param waterLevels
	 *            an array of ints representing water table levels
	 * @return the equivalent array of TileTypes
	 */
	public static Tile[][] createTiles(final int[][] terrain,
			final int[][] elevation, final int[][] waterLevels) {
		final Tile[][] tiles = new Tile[terrain.length][terrain[0].length];
		for (int i = 0; i < terrain.length; i++) {
			for (int j = 0; j < terrain[0].length; j++) {
				tiles[i][j] = new Tile(TileType.values()[terrain[i][j]],
						elevation[i][j], waterLevels[i][j]);
			}
		}
		return tiles;
	}

	/**
	 * Read objects and units from the stream and add them to the map. There
	 * must be at least 1 value on the stream; it is the number of objects. Each
	 * object consists of three ints: its object type and the row and column of
	 * its tile. These coordinates must denote a valid tile.
	 * 
	 * @param map
	 *            the map we're dealing with
	 * @param istream
	 *            the stream we're reading from
	 * @throws IOException
	 *             on I/O error
	 * @return the map with the objects added.
	 */
	private static SPMap addObjs(final SPMap map, final BufferedReader istream)
			throws IOException {
		int num = readValue(istream);
		for (int i = 0; i < num; i++) {
			int obj = readValue(istream);
			if (obj > TerrainObject.values().length) {
				throw new IllegalArgumentException("Unknown terrain object");
			}
			map.terrainAt(readValue(istream), readValue(istream)).setObject(
					TerrainObject.values()[obj]);
		}
		return addBuildings(addUnits(map, istream), istream);
	}

	/**
	 * Read units from the stream and add them to the map. There must be at
	 * least 1 value on the stream; it is the number of units. Each unit begins
	 * with which version of the reading code it is targeted for; at present
	 * only 0 is supported. For version 0 units, that version number is followed
	 * by the coordinates of the tile the unit is on, followed by the owner of
	 * the unit. The coordinates must denote a valid tile; if another unit is on
	 * the tile already it is silently overwritten.
	 * 
	 * @param map
	 *            the map we're dealing with
	 * @param istream
	 *            the stream we're reading from
	 * @throws IOException
	 *             on I/O error
	 * @return the map with the units added.
	 */
	private static SPMap addUnits(final SPMap map, final BufferedReader istream)
			throws IOException {
		int num = readValue(istream);
		for (int i = 0; i < num; i++) {
			int api = readValue(istream);
			if (api == 0) {
				map.terrainAt(readValue(istream), readValue(istream))
						.setModule(new SimpleUnit(readValue(istream)));
			} else if (api == 1) {
				int row = readValue(istream);
				int col = readValue(istream);
				int owner = readValue(istream);
				int burden = readValue(istream);
				Harvester harv = new Harvester(owner);
				harv.setBurden(burden);
				map.terrainAt(row, col).setModule(harv);
			} else {
				throw new IOException(new IllegalStateException(
						"Unsupported unit API"));
			}
		}
		return map;
	}

	/**
	 * Read buildings from the stream and add them to the map. The format is the
	 * same as MapReader#addUnits().
	 * 
	 * @param map
	 *            the map we're dealing with
	 * @param istream
	 *            the stream we're reading from
	 * @throws IOException
	 *             on I/O error
	 * @return the map with the buildings added.
	 */
	private static SPMap addBuildings(final SPMap map,
			final BufferedReader istream) throws IOException {
		int num = readValue(istream);
		for (int i = 0; i < num; i++) {
			int api = readValue(istream);
			if (api == 0) {
				map.terrainAt(readValue(istream), readValue(istream))
						.setModule(new SimpleBuilding(readValue(istream)));
			} else {
				throw new IOException(new IllegalStateException(
						"Unsupported building API"));
			}
		}
		return map;
	}
}
