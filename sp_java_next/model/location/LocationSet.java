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
	 * @see model.location.Location#contains(model.location.Location)
	 * @return whether the set contains the given location.
	 */
	@Override
	public boolean contains(final Location loc) {
		return false || super.contains(loc);
	}

	/**
	 * @see model.location.Location#contains(model.module.Module)
	 * @return whether the set contains the given module
	 */
	public boolean contains(final Module module) {
		// TODO Auto-generated method stub
		boolean temp = false;
		for (Location loc : this) {
			// ESCA-JAVA0119:
			temp = temp || loc.contains(module);
		}
		return temp;
	}

}
