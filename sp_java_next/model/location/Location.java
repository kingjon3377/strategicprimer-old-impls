package model.location;

import java.io.Serializable;
import java.util.Set;

import model.module.Module;

/**
 * A location in the game -- probably a Tile, but not necessarily. Locations can
 * be recursive; even a whole army is a module (each side's modules form a
 * single-root tree) and each module should have a location, if nothing else for
 * the FPS interface.
 * 
 * TODO: Should this be an interface or an abstract class?
 * 
 * TODO: How do we model the effects of terrain (other than difficulty of
 * movement)?
 * 
 * @author Jonathan Lovelace.
 */
public interface Location extends Serializable {
	/**
	 * Add a module to the location.
	 * 
	 * @param module
	 *            The module to be added.
	 */
	void add(Module module);

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

	/**
	 * Remove a module from the location.
	 * 
	 * @param module
	 *            The module to be removed.
	 */
	void remove(Module module);

	/**
	 * Is it possible to add the given module to this location?
	 * 
	 * @param module
	 *            a module to pretend to add
	 * @return whether it is possible to add the module
	 */
	boolean checkAdd(Module module);

	/**
	 * Mark the given module as "selected." This may mark other modules as not
	 * selected. Actions that are targeted at a location should generally affect
	 * its selected module(s).
	 * 
	 * @param module
	 *            the module to select
	 */
	void select(Module module);
	/**
	 * Returns a set of all modules at least directly in the location. It may
	 * include those indirectly in the location, but this is not required.
	 * Assuming no intervening steps, for any member x of this set, contains(x)
	 * should return true.
	 * 
	 * @return a set of all modules (at least directly) in the location.
	 */
	Set<Module> getModules();
	/**
	 * @return the currently "selected" module in the location.
	 */
	Module getSelected();
}
