package model.location;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.activity.InvalidActivityException;

/**
 * The map on which the game takes place.
 * 
 * @author kingjon
 * 
 */
public class Map implements Serializable {
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
	private int sizeRows;
	/**
	 * The size of the map in columns
	 */
	private int sizeCols;
	/**
	 * The tiles on the map. TODO: Should this be a Collection?
	 */
	private Tile[][] tiles;

	/**
	 * Constructor, making the map be of the default size.
	 */
	public Map() {
		constructor(DEFAULT_SIZE_ROWS, DEFAULT_SIZE_COLS);
	}

	/**
	 * @param _rows
	 *            How tall this map is
	 * @param _cols
	 *            How wide this map is
	 */
	public Map(final int _rows, final int _cols) {
		constructor(_rows, _cols);
	}

	/**
	 * @param _rows
	 * @param _cols
	 */
	private void constructor(final int _rows, final int _cols) {
		sizeRows = _rows;
		sizeCols = _cols;
		tiles = new Tile[sizeRows][sizeCols];
		for (int i = 0; i < sizeRows; i++) {
			for (int j = 0; j < sizeCols; j++) {
				initializeTile(i, j);
			}
		}
	}

	/**
	 * @param row
	 * @param col
	 */
	private void initializeTile(final int row, final int col) {
		tiles[row][col] = new Tile(row, col);
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
		return tiles[row][col];
	}

	/**
	 * @param inStream
	 *            The stream
	 * @throws IOException
	 *             When caught
	 * @throws ClassNotFoundException
	 *             When caught
	 */
	private void readObject(final ObjectInputStream inStream)
			throws IOException, ClassNotFoundException {
		inStream.defaultReadObject();
		constructor(sizeRows, sizeCols);
	}

	/**
	 * @param out
	 *            the stream
	 * @throws IOException
	 */
	private void writeObject(final ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}

	// ESCA-JAVA0173:
	/**
	 * To make the static analysis plugins happy despite cols being final in all
	 * but name.
	 * 
	 * @param _cols
	 * @throws InvalidActivityException
	 */
	public void setSizeCols(@SuppressWarnings("unused")
	final int _cols) throws InvalidActivityException {
		throw new InvalidActivityException(
				"Size of a map cannot be changed at runtime");
	}

	// ESCA-JAVA0173:
	/**
	 * To make static analysis plugins happy despite rows being final in all but
	 * name
	 * 
	 * @param _rows
	 * @throws InvalidActivityException
	 */
	public void setSizeRows(@SuppressWarnings("unused")
	final int _rows) throws InvalidActivityException {
		throw new InvalidActivityException(
				"Size of a map cannot be changed at runtime");
	}

	/**
	 * To make static analysis plugins happy despite tiles[][] being final in
	 * all but name.
	 * 
	 * @param _tiles
	 * @throws InvalidActivityException
	 */
	// ESCA-JAVA0130:
	@SuppressWarnings("unused")
	public void setTiles(final Tile[][] _tiles) throws InvalidActivityException {
		throw new InvalidActivityException(
				"Can't substitute a new map array at runtime");
	}
}
