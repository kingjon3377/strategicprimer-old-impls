package model.map;

/**
 * The model class representing the map. At first this will just be the terrain
 * at each position, but other data will be added later.
 * 
 * @note The constructors do not (at present) guarantee anything about the
 *       contents of the map, only its size.
 * @author Jonathan Lovelace
 * 
 */
public class SPMap {
	/**
	 * The data about each position. TODO: should we do more than just trust the
	 * compiler to catch illegal array bounds, etc.?
	 */
	private int[][] theMap;

	/**
	 * Default constructor: make the map 69 rows by 88 columns, the size of the
	 * campaign map pre-revamp.
	 */
	public SPMap() {
		theMap = new int[69][88];
	}

	/**
	 * Get what's at a position on the map.
	 * 
	 * @param row
	 *            the row to look at
	 * @param col
	 *            the column to look at
	 * @return what's at that position
	 */
	public int terrainAt(final int row, final int col) {
		return theMap[row][col];
	}
}
