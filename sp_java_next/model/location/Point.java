package model.location;

/**
 * A pair (X,Y) used to identify a Tile in a map (or a GUITile in a GUIMap)
 * 
 * A single step within a path
 * 
 * @author Kevin Glass
 */
public class Point implements IPoint {
	/** The x coordinate at the given step */
	private final int xCoord;
	/** The y coordinate at the given step */
	private final int yCoord;

	/**
	 * Create a new step
	 * 
	 * @param _xCoord
	 *            The x coordinate of the new step
	 * @param _yCoord
	 *            The y coordinate of the new step
	 */
	public Point(final int _xCoord, final int _yCoord) {
		this.xCoord = _xCoord;
		this.yCoord = _yCoord;
	}
	/**
	 * Get the x coordinate of the point
	 * 
	 * @return The x coodindate of the point
	 */
	public int getX() {
		return xCoord;
	}

	/**
	 * Get the y coordinate of the point
	 * 
	 * @return The y coodindate of the point
	 */
	public int getY() {
		return yCoord;
	}

	/**
	 * @see Object#hashCode()
	 * @return a hash code for the object
	 */
	@Override
	public int hashCode() {
		return xCoord * yCoord;
	}

	/**
	 * @see Object#equals(Object)
	 * @return whether this Step equals another object
	 */
	@Override
	public boolean equals(final Object other) {
		return other instanceof Point && (((Point) other).xCoord == xCoord)
				&& (((Point) other).yCoord == yCoord);
	}
	/**
	 * @return a String representation of the Point
	 */
	@Override
	public String toString() {
		return "(" + xCoord + "," + yCoord + ")";
	}
	/**
	 * Copy constructor
	 * @param point The IPoint to copy
	 */
	public Point(final IPoint point) {
		this(point.getX(),point.getY());
	}
}