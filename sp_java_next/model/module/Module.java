package model.module;

import model.location.Location;
import model.module.kinds.Weapon;
import model.player.IPlayer;

/**
 * A component in a unit, building, etc. (Need better description.)
 * 
 * @author Jonathan Lovelace
 * 
 */
public interface Module {
	/**
	 * @return my universally unique ID -- *this* module rather than another of
	 *         its kind.
	 */
	long getUuid();

	/**
	 * @return what kind of module I am
	 */
	int getModuleID();

	/**
	 * Any kind of Module can be attacked.
	 * 
	 * @see model.module.kinds.Weapon#attack(Module)
	 * 
	 * @param attacker
	 *            The weapon attacking or being used to attack me.
	 */
	void takeAttack(Weapon attacker);

	/**
	 * @return The module's location
	 */
	Location getLocation();

	/**
	 * @return the module's parent in the tree
	 */
	Module getParent();

	/**
	 * Set the module's location. For non-top-level modules this might
	 * conceivably do nothing.
	 * 
	 * @param location
	 *            the new location
	 */
	void setLocation(final Location location);
	/**
	 * Die, cleaning up after yourself. This method could concievably leave
	 * equipment or some Resource behind.
	 */
	void die();

	/**
	 * @return The Module's name, which should be displayed by the GUI when the
	 *         module is selected.
	 */
	String getName();
	/**
	 * @return the player that owns this module
	 */
	IPlayer getOwner();
}
