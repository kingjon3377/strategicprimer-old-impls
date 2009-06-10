package sp.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import finalproject.astar.Location;
import finalproject.astar.Tile;

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
public final class SPMapParser {
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
	public static SPMap createBoard(final URL filename, final URL resourcesFilename)
			throws IOException {
		final BufferedReader readerOne = new BufferedReader(
				new InputStreamReader(filename.openStream()));
		final BufferedReader readerTwo = new BufferedReader(
				new InputStreamReader(resourcesFilename.openStream()));
		final String tokenRows = readerOne.readLine();
		final String tokenCols = readerOne.readLine();
		if (tokenRows == null || tokenCols == null) {
			throw new IOException("EOF in header of map file");
		}
		final int rows = Integer.parseInt(tokenRows);
		final int columns = Integer.parseInt(tokenCols);
		final List<List<Tile>> tiles = new ArrayList<List<Tile>>(); // NOPMD by
		for (int rowCount = 0; rowCount < rows; rowCount++) {
			final String lineOne = readerOne.readLine();
			final String lineTwo = readerTwo.readLine();
			if (lineOne == null || lineTwo == null) {
				throw new IOException("Unexpected EOF while creating board");
			}
			final String[] resultOne = lineOne.split("\\s");
			if (resultOne.length != columns) {
				throw new IOException("Wrong sized line " + rowCount + " in: "
						+ filename);
			}
			final String[] resultTwo = lineTwo.split("\\s");
			if (resultTwo.length != columns) {
				throw new IOException("Wrong sized line " + rowCount + " in "
						+ resourcesFilename);
			}
			// TODO: Use a proper array to get rid of most of these
			// static-analysis
			// warnings properly.
			tiles.set(rowCount, new ArrayList<Tile>()); // NOPMD
			for (int col = 0; col < resultOne.length; col++) {
				tiles.get(rowCount).set(col,
						new Tile(new Location(rowCount, col), Integer // NOPMD
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
