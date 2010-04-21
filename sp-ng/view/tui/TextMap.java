package view.tui;

import java.io.PrintStream;

import model.map.SPMap;

/**
 * A text (ASCII-art) representation of the map TODO: add an event loop.
 * 
 * @author Jonathan Lovelace
 * 
 * 
 * 
 */
public class TextMap {
	/**
	 * The map this represents.
	 */
	private SPMap theMap;
	/**
	 * How many characters wide each tile's representation will be.
	 */
	private final int tileWidth;
	/**
	 * How many rows of characters tall each tile's representation will be.
	 */
	private final int tileHeight;

	/**
	 * Constructor.
	 * 
	 * @param map
	 *            the map
	 * @param tWidth
	 *            how many characters wide to draw each tile
	 * @param tHeight
	 *            how many characters high to draw each tile
	 */
	public TextMap(final SPMap map, final int tWidth, final int tHeight) {
		theMap = map;
		tileWidth = tWidth;
		tileHeight = tHeight;
	}

	/**
	 * Constructor.
	 * 
	 * @param map
	 *            the map
	 */
	public TextMap(final SPMap map) {
		this(map, 2, 2);
	}

	/**
	 * Constructor.
	 */
	public TextMap() {
		this(new SPMap());
	}

	/**
	 * Entry point for the program.
	 * 
	 * @param args
	 *            ignored for now
	 */
	public static void main(final String[] args) {
		// ESCA-JAVA0266:
		new TextMap(new SPMap()).paint(System.out);
	}

	/**
	 * Display the map
	 * 
	 * @param out
	 */
	public void paint(PrintStream out) {
		if (tileHeight < 2 && tileWidth < 2) {
			for (int row = 0; row < theMap.getRows(); row++) {
				for (int col = 0; col < theMap.getCols(); col++) {
					paintTile(theMap.terrainAt(row, col), out);
				}
				out.append('\n');
			}
		} else if (tileHeight < 2 || tileWidth < 2) {
			throw new IllegalStateException(
					"Text tile height and width must either be both 1 or both greater than 1");
		} else {
			for (int row = 0; row < theMap.getRows(); row++) {
				for (int j = 0; j < tileWidth * theMap.getCols(); j++) {
					out.append('-');
				}
				out.append('\n');
				for (int i = 1; i < tileHeight; i++) {
					for (int col = 0; col < theMap.getCols(); col++) {
						out.append('|');
						for (int j = 1; j < tileWidth; j++) {
							paintTile(theMap.terrainAt(row, col), out);
						}
					}
					out.append('|');
					out.append('\n');
				}
			}
			for (int j = 0; j < tileWidth * theMap.getCols(); j++) {
				out.append('-');
			}
			out.append('\n');
		}
	}

	/**
	 * Display a tile. Actually, since we can't go back to previous lines in
	 * unbuffered stream I/O, most tile drawing is handled in the main
	 * map-drawing method.
	 * 
	 * @param tile
	 *            The tile to draw.
	 * @param out
	 *            The stream to draw to.
	 */
	private void paintTile(int tile, PrintStream out) {
		out.append('.');
	}
}
