package model.module;

import java.io.Serializable;

import model.location.Location;

/**
 * A module [unit, building, weapon, tool, wonder, resource, or
 * other object] in the game.
 * 
 * @author Jonathan Lovelace
 * @deprecated in favor of the new Module.
 */
@Deprecated
public interface OldModule extends Serializable, Module {

	/**
	 * @return The module's location
	 */
	Location getLocation();

	/**
	 * @return the module's parent in the tree
	 */
	Module getParent();

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

}

