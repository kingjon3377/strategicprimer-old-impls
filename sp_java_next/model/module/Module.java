package model.module;

import model.location.Location;

/**
 * A module in the game.
 * @author Jonathan Lovelace
 */
public interface Module {

	/**
	 * Take an attack from a specified module.
	 * 
	 * @see Weapon#attack(Module)
	 * @param attacker
	 *            The attacker
	 */
	void takeAttack(Weapon attacker);

	/**
	 * Execute upkeep functions. Should pass this on to submodules.
	 * 
	 * @param interval
	 *            How long it's been since the last time upkeep was executed.
	 */
	void upkeep(long interval);

	/**
	 * @return whether the module is able to move
	 */
	boolean isMobile();

	/**
	 * Since all modules on each side form a tree, we can't rely on the parent
	 * field to tell us whether a Module should be able to move or not, and
	 * since all Modules should have locations (for FPS interface if nothing
	 * else) we can't rely on that either ...
	 * 
	 * TODO: Should this be folded into "mobile"?
	 * 
	 * @return whether the module is "top-level".
	 */
	boolean isTopLevel();

	/**
	 * @return The module's location
	 */
	Location getLocation();

	/**
	 * @return the module's parent in the tree
	 */
	Module getParent();

}