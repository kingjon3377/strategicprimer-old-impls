package model.location;

/**
 * A pair (X,Y) used to identify a Tile in a map (or a GUITile in a GUIMap).
 * 
 * A single step within a path.
 * 
 * @author Kevin Glass
 * @author Documented and extended by Jonathan Lovelace
 */
public class Point implements IPoint {
	/** The x coordinate at the given step. */
	private final int xCoord;
	/** The y coordinate at the given step. */
	private final int yCoord;

	/**
	 * Create a new step.
	 * 
	 * @param newXCoord
	 *            The x coordinate of the new step
	 * @param newYCoord
	 *            The y coordinate of the new step
	 */
	public Point(final int newXCoord, final int newYCoord) {
		this.xCoord = newXCoord;
		this.yCoord = newYCoord;
	}

	/**
	 * Get the x coordinate of the point.
	 * 
	 * @return The x coodindate of the point
	 */
	public final int getX() {
		return xCoord;
	}

	/**
	 * Get the y coordinate of the point.
	 * 
	 * @return The y coodindate of the point
	 */
	public final int getY() {
		return yCoord;
	}

	/**
	 * @see Object#hashCode()
	 * @return a hash code for the object
	 */
	@Override
	public final int hashCode() {
		return xCoord * yCoord;
	}

	/**
	 * @param other Another object
	 * @return whether that object is another Point equal to this one
	 */
	@Override
	public final boolean equals(final Object other) {
		return other instanceof Point && (((Point) other).xCoord == xCoord)
				&& (((Point) other).yCoord == yCoord);
	}

	/**
	 * @return a String representation of the Point
	 */
	@Override
	public final String toString() {
		return "(" + xCoord + "," + yCoord + ")";
	}

	/**
	 * Copy constructor.
	 * 
	 * @param point
	 *            The IPoint to copy
	 */
	public Point(final IPoint point) {
		this(point.getX(), point.getY());
	}
}
