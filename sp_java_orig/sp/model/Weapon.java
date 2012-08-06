package sp.model;

import java.io.Serializable;

/**
 * Something that can attack.
 * 
 * @author Jonathan Lovelace
 * 
 */
public interface Weapon extends Serializable {

	/**
	 * Attack a specified module. At present caller is responsible for checking
	 * whether this attack is valid. TODO: Fix this with a server-side check.
	 * Subclasses are responsible for ensuring this actually does anything.
	 * 
	 * @param target
	 *            The target module.
	 */
	void attack(final IModule target);

	/**
	 * Checks whether it is possible to attack the specified module. Subclasses
	 * are responsible for implementing this stub; here this returns false.
	 * TODO: This should check that the module can attack the target, and that
	 * the target can be attacked by the module.
	 * 
	 * @param target
	 *            The potential target
	 * @return whether the attack is possible.
	 */
	boolean checkAttack(final IModule target);

	/**
	 * Check whether it is possible to make a ranged attack against the
	 * specified module. TODO: Roll this into checkAttack(). Subclasses are
	 * responsible for implementing this; here it just returns false.
	 * 
	 * @param target
	 *            The potential target
	 * @return whether a ranged attack against the target is possible.
	 */
	boolean checkRangedAttack(final IModule target);

	/**
	 * @return whether the module has attacked this turn
	 */
	boolean isHasAttacked();

	/**
	 * Make a ranged attack on a specified module. This is temporary until we
	 * get a properly modular version of attack() that checks distance and which
	 * would be better. Subclasses are responsible for replacing this stub;
	 * caller is at present responsible for checking whether this is valid.
	 * TODO: fold this into attack() with a proper distance- and "stat"-checking
	 * algorithm.
	 * 
	 * @param target
	 *            The target module
	 */
	void rangedAttack(final IModule target);

	/**
	 * @param hasAttacked
	 *            whether the module has attacked this turn
	 */
	void setHasAttacked(final boolean hasAttacked);

	/**
	 * @param module
	 *            A module to perhaps attack
	 * @return How much damage an attack on it would do
	 * @bug FIXME: This should probably return a class similar to Statistics,
	 *      since not all modules are defined by hit points, attacks can have
	 *      other effects, and an attack on a very important, very durable
	 *      module that did only 5 points of damage but destroyed it should
	 *      probably be preferred to an attack on a very fragile but resilient
	 *      not very important module that did over a hundred but left it
	 *      standing.
	 */
	int predictAttackResult(final IModule module);
}
