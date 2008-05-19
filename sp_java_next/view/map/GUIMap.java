/**
 * 
 */
package view.map;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.activity.InvalidActivityException;
import javax.swing.JPanel;

/**
 * @author Jonathan Lovelace
 * 
 */
public class GUIMap extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8873153191640697473L;
	/**
	 * The tiles on the map. TODO: Should this be a Collection?
	 */
	private transient GUITile[][] tiles;
	/**
	 * The number of rows of tiles
	 */
	private int rows;
	/**
	 * The number of columns of tiles
	 */
	private int cols;

	/**
	 * Constructor
	 * 
	 * @param _rows
	 *            the number of rows
	 * @param _cols
	 *            the number of columns
	 */
	public GUIMap(final int _rows, final int _cols) {
		super();
		constructor(_rows, _cols);
	}

	/**
	 * @param _rows
	 * @param _cols
	 */
	private void constructor(final int _rows, final int _cols) {
		rows = _rows;
		cols = _cols;
		tiles = new GUITile[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				initializeTile(i, j);
			}
		}
	}

	/**
	 * @return the cols
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @param row
	 *            the row number
	 * @param col
	 *            the column number
	 */
	private void initializeTile(final int row, final int col) {
		tiles[row][col] = new GUITile();
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
		constructor(rows, cols);
	}

	// ESCA-JAVA0173:
	/**
	 * To make the static analysis plugins happy despite cols being final in all
	 * but name.
	 * 
	 * @param _cols
	 * @throws InvalidActivityException
	 */
	public void setCols(@SuppressWarnings("unused")
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
	public void setRows(@SuppressWarnings("unused")
	final int _rows) throws InvalidActivityException {
		throw new InvalidActivityException(
				"Size of a map cannot be changed at runtime");
	}

	/**
	 * TODO: Is this really needed?
	 * 
	 * @param row
	 *            A row number
	 * @param col
	 *            A column number
	 * @return The tile at that position
	 */
	public GUITile tileAt(final int row, final int col) {
		return tiles[row][col];
	}

	/**
	 * @param out
	 *            the stream
	 * @throws IOException
	 */
	private void writeObject(final ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}

}
