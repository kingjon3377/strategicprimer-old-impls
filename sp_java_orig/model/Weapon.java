package model;

/**
 * A weapon is something that can attack a module.
 * 
 * @author Jonathan Lovelace
 */
public interface Weapon {
	/**
	 * Attack a module
	 * 
	 * @param module
	 *            The module to attack
	 */
	void attack(Module module);

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
	int predictAttackResult(Module module);
}
