package model.module;

import model.location.Location;

/**
 * A module that can move.
 * 
 * @author Jonathan Lovelace
 */
public interface Unit {
	/**
	 * Move to the given location
	 * 
	 * @param loc
	 *            The location to which to move.
	 */
	void move(Location loc);
	/**
	 * Move to the specified location. Only implemented to make "is not read"
	 * warnings go away.
	 * 
	 * TODO: Implement properly
	 * 
	 * TODO: Use more specific exceptions.
	 * 
	 * @param loc
	 *            The location to go to.
	 * @throws UnableToMoveException
	 *             if we can't move.
	 */
	// public void move(final Location loc) throws UnableToMoveException {
	// if (!isMobile()) {
	// throw new UnableToMoveException("I'm not able to move!");
	// }
	// if (location == null) {
	// throw new UnableToMoveException("I don't know where I am!");
	// }
	// if (!topLevel) {
	// throw new UnableToMoveException(
	// "Not a top-level module; use parent instead");
	// }
	// location = loc;
	// }
}
