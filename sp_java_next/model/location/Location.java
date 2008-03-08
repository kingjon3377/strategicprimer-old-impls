package model.location;

import java.io.Serializable;

import model.module.Module;

/**
 * A location in the game -- probably a Tile, but not necessarily. Locations can
 * be recursive; even a whole army is a module (each side's modules form a
 * single-root tree) and each module should have a location, if nothing else for
 * the FPS interface.
 * 
 * TODO: Should this be an interface or an abstract class?
 * 
 * @author Jonathan Lovelace.
 */
public interface Location extends Serializable {
	/**
	 * Does this location contain another?
	 * 
	 * @param location
	 *            Another location
	 * @return true if that location is in this one (whatever that means); false
	 *         otherwise
	 */
	boolean contains(Location location);

	/**
	 * Is a specified module in this location?
	 * 
	 * @param module
	 *            A module
	 * @return true if the module is in this location (whatever that means);
	 *         false otherwise
	 */
	boolean contains(Module module);
}
