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
	private static final int TILE_WIDTH = 4;
	/**
	 * How many rows of characters tall each tile's representation will be.
	 */
	private static final int TILE_HEIGHT = 4;

	/**
	 * Constructor.
	 * 
	 * @param map
	 *            the map
	 */
	public TextMap(final SPMap map) {
		theMap = map;
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
		new TextMap(new SPMap()).paint(System.out);
	}

	/**
	 * Display the map
	 * 
	 * @param out
	 */
	private void paint(PrintStream out) {
	}
}
