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
	 * @throws UnableToMoveException
	 *             if unable to move
	 */
	void move(Location loc) throws UnableToMoveException;

	/**
	 * Check whether this module can move to the given location. If
	 * move(Location) is called immediately after this returns true, it should
	 * not throw an UnableToMoveException, while it should if it is called
	 * immediately after this returns false.
	 * 
	 * TODO: This should probably give some indication of *why* it the module
	 * can't move to a location.
	 * 
	 * @param loc
	 *            the location we're pretending to move to
	 * @return whether this module can move there.
	 */
	boolean checkMove(Location loc);

	/**
	 * @param location
	 *            a location
	 * @return how much it will cost (on a scale unique to this module) to
	 *         travel to the given location (ignoring the cost of all locations
	 *         en route)
	 */
	double getCost(Location location);
}
