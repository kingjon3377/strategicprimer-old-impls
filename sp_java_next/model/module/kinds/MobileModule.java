package model.module.kinds;

import model.location.Location;
import model.module.Module;
import model.module.UnableToMoveException;

/**
 * A module that can move.
 * 
 * @author Jonathan Lovelace
 */
public interface MobileModule extends Module {
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
