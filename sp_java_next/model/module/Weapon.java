package model.module;

/**
 * A weapon is a module that can be used to attack another module, whether
 * directly or (in the case of a unit) by passing this on to a submodule (or to
 * a job but TODO: how?)
 * 
 * @author Jonathan Lovelace
 * 
 */
public interface Weapon extends Module {
	/**
	 * Attack a module
	 * 
	 * @param defender
	 *            The module to attack
	 */
	void attack(Module defender);

	/**
	 * Predict the outcome of an attack (TODO: Maybe a more nuanced view of the
	 * outcome than simply damage dealt)
	 * 
	 * @param defender
	 *            The module being attacked
	 * @return The damage that would likely be dealt. (Should in most cases,
	 *         barring data intentionally hid from the prediction process, be at
	 *         least order-of-magnitude accurate.)
	 */
	int predictDamage(Module defender);
}
