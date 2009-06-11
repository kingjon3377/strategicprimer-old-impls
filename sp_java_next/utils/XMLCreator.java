package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class to create a map XML file from the intermediate format.
 * 
 * @author Jonathan Lovelace
 */
public class XMLCreator {
	/**
	 * A logger to replace printStackTrace() calls.
	 */
	private static final Logger LOGGER = Logger.getLogger(XMLCreator.class.getName());
	/**
	 * The map in its intermediate format.
	 */
	private final List<List<Character>> tiles;

	/**
	 * Entry point for the driver.
	 * 
	 * @param args
	 *            Input and output files, in that order.
	 */
	public static void main(final String[] args) {
		try {
			new XMLCreator(args[0], false).create(args[1]);
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, "File not found", e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "I/O error", e);
		}
	}

	/**
	 * Constructor: Get the intermediate format.
	 * 
	 * @param filename
	 *            The name of the file holding either the intermediate format or
	 *            the image.
	 * @param image
	 *            Whether this is the image or the intermediate format.
	 * @throws IOException
	 *             on input error
	 * 
	 */
	public XMLCreator(final String filename, final boolean image)
			throws IOException {
		if (image) {
			tiles = new MapReaderDriver(filename).createMap();
		} else {
			final BufferedReader reader = new BufferedReader(new FileReader(filename));
			tiles = new ArrayList<List<Character>>();
			String line = reader.readLine();
			while (line != null) {
				final List<Character> list = new ArrayList<Character>(); // NOPMD
				for (int i = 0; i < line.length(); i++) {
					list.add(new Character(line.charAt(i))); // NOPMD
				}
				tiles.add(list);
				line = reader.readLine();
			}
			reader.close();
		}
	}

	/**
	 * Create the XML file from the intermediate format
	 * 
	 * @param filename
	 *            The file to write to
	 * @throws IOException
	 *             on output error
	 */
	public void create(final String filename) throws IOException {
		final BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		writer.append("<map>\n");
		for (int i = 0; i < tiles.size(); i++) {
			writer.append("\t<row index=\"" + i + "\">\n");
			final List<Character> list = tiles.get(i);
			for (int j = 0; j < list.size(); j++) {
				writer.append("\t\t<tile row=\"" + i + "\" column =\"" + j
						+ "\" type=\"" + getTileType(list.get(j))
						+ "\"></tile>\n");
			}
			writer.append("\t</row>\n");
		}
		writer.append("</map>");
		writer.close();
	}

	/**
	 * A mapping from the terrain type number in one particular map to what it
	 * will be called in the XML
	 */
	private static final Map<Character, String> CHAR_TILE_TYPE = new HashMap<Character, String>();
	static {
		CHAR_TILE_TYPE.put(Character.valueOf('0'), "tundra");
		CHAR_TILE_TYPE.put(Character.valueOf('1'), "desert");
		CHAR_TILE_TYPE.put(Character.valueOf('2'), "mountain");
		CHAR_TILE_TYPE.put(Character.valueOf('3'), "boreal_forest");
		CHAR_TILE_TYPE.put(Character.valueOf('4'), "temperate_forest");
		CHAR_TILE_TYPE.put(Character.valueOf('5'), "ocean");
		CHAR_TILE_TYPE.put(Character.valueOf('6'), "plains");
		CHAR_TILE_TYPE.put(Character.valueOf('7'), "jungle");
	}

	/**
	 * Convert a tile's intermediate (one-char) format to a more verbose
	 * description
	 * 
	 * @param character
	 *            the tile's type ID (from the original map's converted format)
	 * @return a description of the tile type
	 */
	public String getTileType(final char character) {
		return CHAR_TILE_TYPE.get(Character.valueOf(character));
	}
}
