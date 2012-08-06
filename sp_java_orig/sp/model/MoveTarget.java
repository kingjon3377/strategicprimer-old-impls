package sp.model;


/**
 * A place to which a unit can move--a location in the game, at whatever level
 * of detail.
 * 
 * FIXME: We need some way of seeing that only Units can move during the game,
 * but that we can set fixed things in their locations at startup or
 * deserialization.
 * 
 * @author Jonathan Lovelace
 * 
 */
public interface MoveTarget {
	/**
	 * Add a unit to those in me.
	 * 
	 * @param module
	 *            The module.
	 */
	void add(IModule module);

	/**
	 * Remove a module from those in me.
	 * 
	 * @param module
	 *            The module.
	 */
	void remove(IModule module);

	/**
	 * FIXME: We need to rework stuff like cover.
	 * 
	 * @param module
	 *            A module in the location
	 * @return The bonus this area provides it as far as cover.
	 */
	double coverBonus(IModule module);
	
	/**
	 * FIXME: We need to rework stuff like defense-bonus.
	 * @param module A module in the location
	 * @return The bonus this area provides it as far as defense.
	 */
	int defenseBonus(IModule module);
}
