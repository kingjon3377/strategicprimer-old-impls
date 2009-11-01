package model.location;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The map on which the game takes place.
 * 
 * @author kingjon
 * 
 */
public class SPMap implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8308256668999321710L;
	/**
	 * The map's default number of rows
	 */
	private static final int DEFAULT_SIZE_ROWS = 10;
	/**
	 * The map's default number of columns
	 */
	private static final int DEFAULT_SIZE_COLS = 10;
	/**
	 * The size of the map in rows
	 */
	private final int sizeRows;
	/**
	 * The size of the map in columns
	 */
	private final int sizeCols;
	/**
	 * The tiles on the map
	 */
	private final Map<Point, Tile> tiles = new HashMap<Point, Tile>();

	/**
	 * Constructor, making the map be of the default size.
	 */
	public SPMap() {
		sizeRows = DEFAULT_SIZE_ROWS;
		sizeCols = DEFAULT_SIZE_COLS;
	}

	/**
	 * @param _rows
	 *            How tall this map is
	 * @param _cols
	 *            How wide this map is
	 */
	public SPMap(final int _rows, final int _cols) {
		sizeRows = _rows;
		sizeCols = _cols;
	}

	/**
	 * Set up all uninitialized tiles as blank ones.
	 */
	public void setUpEmptyTiles() {
		for (int i = 0; i < sizeRows; i++) {
			for (int j = 0; j < sizeCols; j++) {
				initializeTile(i, j);
			}
		}
	}

	/**
	 * @return the number of columns wide this map is
	 */
	public int getSizeCols() {
		return sizeCols;
	}

	/**
	 * @return the number of rows long this map is
	 */
	public int getSizeRows() {
		return sizeRows;
	}

	/**
	 * @param row
	 *            a row number
	 * @param col
	 *            a column number
	 * @return the tile at that location
	 */
	public Tile getTileAt(final int row, final int col) {
		return getTileAt(new Point(row, col));
	}

	/**
	 * @param point
	 *            A set of coordinates
	 * @return The tile at those coordinates
	 */
	public Tile getTileAt(final IPoint point) {
		if (tiles.containsKey(point)) {
			return tiles.get(point);
		} else {
			throw new IllegalArgumentException("Point " + point + " not in map");
		}
	}

	/**
	 * To appease static analysis plugins, and Eclipse in general
	 * 
	 * @return the array of tiles
	 */
	protected Map<Point, Tile> getTiles() {
		return Collections.unmodifiableMap(tiles);
	}

	/**
	 * @param row
	 *            The row to put the new tile
	 * @param col
	 *            The column to put the new tile
	 */
	private void initializeTile(final int row, final int col) {
		initializeTile(new Point(row, col));
	}

	/**
	 * @param point
	 *            The location to put the new tile.
	 */
	private void initializeTile(final Point point) {
		if (!tiles.containsKey(point)) {
			tiles.put(point, new Tile(point));
		}
	}
	/**
	 * Add tiles to the map.
	 * @param map a Map containing the tiles.
	 */
	public void addTiles(final Map<Point,Tile> map) {
		tiles.putAll(map);
	}
}
