package model.module;

import model.location.Location;

/**
 * A module that can move.
 * 
 * @author Jonathan Lovelace
 */
public interface MobileModule {
	/**
	 * Move to the given location
	 * 
	 * TODO: Use more specific exceptions.
	 * 
	 * @param loc
	 *            The location to which to move.
	 * @throws UnableToMoveException if unable to move
	 */
	void move(Location loc) throws UnableToMoveException;
}
