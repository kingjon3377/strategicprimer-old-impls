package common;

import java.io.Serializable;

/**
 * A class encapsulating a row value and a column value.
 * 
 * @author Jonathan Lovelace
 */
public class SPPoint implements Serializable {
	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = 6356928228400695515L;
	/**
	 * Row.
	 */
	private final int row;
	/**
	 * Column.
	 */
	private final int col;

	/**
	 * @return the row value
	 */
	public int row() {
		return row;
	}

	/**
	 * @return the column value
	 */
	public int col() {
		return col;
	}

	/**
	 * Constructor.
	 * 
	 * @param _row
	 *            the row value
	 * @param _col
	 *            the column value
	 */
	public SPPoint(final int _row, final int _col) {
		row = _row;
		col = _col;
	}

	/**
	 * @param obj another point
	 * @return whether this is the same as it
	 */
	@Override
	public boolean equals(final Object obj) {
		return obj instanceof SPPoint && ((SPPoint) obj).row == row
				&& ((SPPoint) obj).col == col;
	}
	/**
	 * @return a hash value for this object
	 */
	@Override
	public int hashCode() {
		return 1024 * row + col;
	}
}
