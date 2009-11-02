package model.location;

import java.util.HashSet;

import model.module.Module;

/**
 * A set of locations can also be a location -- higher-level (in the tree)
 * Modules require summary Locations.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class LocationSet extends HashSet<Location> implements Location {

	/**
	 * Generated serial UID.
	 */
	private static final long serialVersionUID = 932215871788666938L;

	/**
	 * Do not use this method -- call it on one of the members of the set.
	 * 
	 * @param module
	 *            unused.
	 */
	public void add(final Module module) {
		throw new IllegalStateException(
				"Call add() on one of the members of a set of locations, not on the set itself.");
	}

	/**
	 * @see model.location.Location#contains(model.location.Location)
	 * @param loc a location
	 * @return whether the set contains the given location.
	 */
	@Override
	public boolean contains(final Location loc) { // NOPMD
		return super.contains(loc);
	}

	/**
	 * @see model.location.Location#contains(model.module.Module)
	 * @param module
	 *            the module to search for.
	 * @return whether the set contains the given module
	 */
	public boolean contains(final Module module) {
		// TODO Auto-generated method stub
		boolean temp = false; // NOPMD by kingjon on 5/19/08 12:26 AM
		for (final Location loc : this) {
			// ESCA-JAVA0119:
			temp = temp || loc.contains(module);
		}
		return temp;
	}

	/**
	 * Removes the module from whichever of my children the module is in.
	 * 
	 * @param module
	 *            The module to remove.
	 */
	public void remove(final Module module) {
		for (final Location loc : this) {
			if (loc.contains(module)) {
				loc.remove(module);
				return;
			}
		}
	}
	/**
	 * @param module a module
	 * @return that it's not possible to directly add it to a LocationSet./
	 */
	@Override
	public boolean checkAdd(final Module module) {
		return false;
	}
}
