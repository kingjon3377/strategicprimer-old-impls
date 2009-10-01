package pathfinding;

import java.util.ArrayList;
import java.util.List;

import model.location.IPoint;
import model.location.Point;

/**
 * A path determined by some path finding algorithm. A series of steps from the
 * starting location to the target location. This includes a step for the
 * initial location.
 * 
 * @author Kevin Glass
 */
public class Path {
	/** The list of steps building up this path */
	private final List<Point> steps;

	/**
	 * Create an empty path
	 */
	public Path() {
		super();
		steps = new ArrayList<Point>();
	}

	/**
	 * Get the length of the path, i.e. the number of steps
	 * 
	 * @return The number of steps in this path
	 */
	public int getLength() {
		return steps.size();
	}

	/**
	 * Get the step at a given index in the path
	 * 
	 * @param index
	 *            The index of the step to retrieve. Note this should be >= 0
	 *            and < getLength();
	 * @return The step information, the position on the map.
	 */
	public IPoint getStep(final int index) {
		return steps.get(index);
	}

	/**
	 * Get the x coordinate for the step at the given index
	 * 
	 * @param index
	 *            The index of the step whose x coordinate should be retrieved
	 * @return The x coordinate at the step
	 */
	public int getX(final int index) {
		return getStep(index).getX();
	}

	/**
	 * Get the y coordinate for the step at the given index
	 * 
	 * @param index
	 *            The index of the step whose y coordinate should be retrieved
	 * @return The y coordinate at the step
	 */
	public int getY(final int index) {
		return getStep(index).getY();
	}

	/**
	 * Append a step to the path.
	 * 
	 * @param xCoord
	 *            The x coordinate of the new step
	 * @param yCoord
	 *            The y coordinate of the new step
	 */
	public void appendStep(final int xCoord, final int yCoord) {
		steps.add(new Point(xCoord, yCoord));
	}

	/**
	 * Prepend a step to the path.
	 * 
	 * @param xCoord
	 *            The x coordinate of the new step
	 * @param yCoord
	 *            The y coordinate of the new step
	 */
	public void prependStep(final int xCoord, final int yCoord) {
		steps.add(0, new Point(xCoord, yCoord));
	}

	/**
	 * Check if this path contains the given step
	 * 
	 * @param xCoord
	 *            The x coordinate of the step to check for
	 * @param yCoord
	 *            The y coordinate of the step to check for
	 * @return True if the path contains the given step
	 */
	public boolean contains(final int xCoord, final int yCoord) {
		return steps.contains(new Point(xCoord, yCoord));
	}

	/**
	 * Because we override equals()
	 * 
	 * @return a hash value for the class
	 */
	@Override
	public int hashCode() {
		return steps.hashCode();
	}

	/**
	 * Check if this path equals another one.
	 * 
	 * @param other
	 *            The other object
	 * @return whether it's a Path equal to this one
	 */
	@Override
	public boolean equals(final Object other) {
		return other instanceof Path && ((Path) other).steps.equals(steps);
	}
	/**
	 * @return a String representation of the Path
	 */
	@Override
	public String toString() {
		final StringBuffer buf = new StringBuffer();
		for (Point step : steps) {
			buf.append(step.toString());
		}
		return buf.toString();
	}
}