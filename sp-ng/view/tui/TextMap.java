package view.tui;

import java.io.PrintStream;

import model.map.SPMap;
import model.map.TileType;

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
	 * The uppermost row to display
	 */
	private int topRow;
	/**
	 * How many rows to display
	 */
	private int rowsVisible;
	/**
	 * The leftmost column to display
	 */
	private int leftCol;
	/**
	 * How many columns to display
	 */
	private int colsVisible;

	/**
	 * Constructor.
	 * 
	 * @param map
	 *            the map
	 * @param tWidth
	 *            how many characters wide to draw each tile
	 * @param tHeight
	 *            how many characters high to draw each tile
	 * @param visibleRows
	 *            how many rows to display at once
	 * @param visibleCols
	 *            how many columns to display at once
	 */
	public TextMap(final SPMap map, final int tWidth, final int tHeight,
			final int visibleRows, final int visibleCols) {
		theMap = map;
		tileWidth = tWidth;
		tileHeight = tHeight;
		topRow = 0;
		leftCol = 0;
		rowsVisible = visibleRows;
		colsVisible = visibleCols;
	}
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
		this(map, tWidth, tHeight, map.getRows(), map.getCols());
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
			for (int row = topRow; row < topRow + rowsVisible && row < theMap.getRows(); row++) {
				for (int col = leftCol; col < leftCol + colsVisible && col < theMap.getCols(); col++) {
					paintTile(theMap.terrainAt(row, col), out);
				}
				out.append('\n');
			}
		} else if (tileHeight < 2 || tileWidth < 2) {
			throw new IllegalStateException(
					"Text tile height and width must either be both 1 or both greater than 1");
		} else {
			for (int row = topRow; row < topRow + rowsVisible && row < theMap.getRows(); row++) {
				for (int j = 0; j < tileWidth * rowsVisible; j++) {
					out.append('-');
				}
				out.append('\n');
				for (int i = 1; i < tileHeight; i++) {
					for (int col = leftCol; col < leftCol + colsVisible && col < theMap.getCols(); col++) {
						out.append('|');
						for (int j = 1; j < tileWidth; j++) {
							paintTile(theMap.terrainAt(row, col), out);
						}
					}
					out.append('|');
					out.append('\n');
				}
			}
			for (int j = 0; j < tileWidth * colsVisible; j++) {
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
	private void paintTile(TileType tile, PrintStream out) {
		out.append('.');
	}
	/**
	 * Shift the map left.
	 */
	public void left() {
		if (leftCol > 0) {
			leftCol--;
		}
	}
	/**
	 * Shift the map right.
	 */
	public void right() {
		if (leftCol + colsVisible < theMap.getCols()) {
			leftCol++;
		}
	}
	/**
	 * Shift the map up.
	 */
	public void up() {
		if (topRow > 0) {
			topRow--;
		}
	}
	/**
	 * Shift the map down.
	 */
	public void down() {
		if (topRow + rowsVisible < theMap.getRows()) {
			topRow++;
		}
	}
}
