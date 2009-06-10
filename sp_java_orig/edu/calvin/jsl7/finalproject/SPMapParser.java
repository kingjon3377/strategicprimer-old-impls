package edu.calvin.jsl7.finalproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser for map description files. Basically a wrapper class around one
 * function, which if not for naming-convention-etc. ought to be a static
 * function in SPMap.
 * 
 * @author Jonathan Lovelace
 * @assignment Final Project
 * @course CS108A
 * @semester FA06
 * 
 */
public final class SPMapParser { // NOPMD
	/**
	 * This class should only be used statically.
	 */
	private SPMapParser() {
		// Do not instantiate
	}

	/**
	 * Parses files describing a map and returns such a map (with no modules in
	 * it).
	 * 
	 * @param filename
	 *            The name of a file describing the size and terrain types of
	 *            the map.
	 * @param resourcesFilename
	 *            The name of a file describing the concentrations of resources
	 *            on the map.
	 * @return A map described by these files
	 *@throws IOException
	 *             on I/O error
	 */
	public static SPMap createBoard(final URL filename, // NOPMD
			final URL resourcesFilename) throws IOException {
		final BufferedReader readerOne = new BufferedReader(
				new InputStreamReader(filename.openStream()));
		final BufferedReader readerTwo = new BufferedReader(
				new InputStreamReader(resourcesFilename.openStream()));
		final String rowToken = readerOne.readLine();
		final String colToken = readerOne.readLine();
		if (rowToken == null || colToken == null) {
			throw new IOException("EOF in map header");
		}
		final int rows = Integer.parseInt(rowToken);
		final int columns = Integer.parseInt(rowToken);
		final List<List<Tile>> tiles = new ArrayList<List<Tile>>();
		for (int rowCount = 0; rowCount < rows; rowCount++) {
			final String lineOne = readerOne.readLine();
			if (lineOne == null) {
				throw new IOException("Wrong number of rows in: " + filename);
			}
			final String[] resultOne = lineOne.split("\\s");
			if (resultOne.length != columns) {
				throw new IOException("Wrong sized line " + rowCount + " in: "
						+ filename);
			}
			final String lineTwo = readerTwo.readLine();
			if (lineTwo == null) {
				throw new IOException("Wrong number of rows in: "
						+ resourcesFilename);
			}
			final String[] resultTwo = lineTwo.split("\\s");
			if (resultTwo.length != columns) {
				throw new IOException("Wrong sized line " + rowCount + " in "
						+ resourcesFilename);
			}
			tiles.set(rowCount, new ArrayList<Tile>()); // NOPMD
			for (int col = 0; col < resultOne.length; col++) {
				if (Integer.parseInt(resultOne[col]) >= SPMap.MAX_TERRAIN_TYPE) {
					throw new IOException("Value too large: " + resultOne[col]);
				}
				tiles.get(rowCount).set(col, new Tile(rowCount, col, Integer // NOPMD
						.parseInt(resultOne[col])));
				tiles.get(rowCount).get(col).setResourceOnTile(
						Integer.parseInt(resultTwo[col]));
			}
		}
		readerOne.close();
		readerTwo.close();
		return new SPMap(rows, columns, tiles);
	}

}
