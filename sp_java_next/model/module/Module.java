package model.module;

import model.location.Location;

/**
 * A component in a unit, building, etc. (Need better description.)
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
	 * @param attacker The weapon attacking or being used to attack me.
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

}
