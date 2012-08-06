package model.module.kinds;

import java.util.Set;

import model.module.Module;
import model.module.actions.Action;

/**
 * A module that can perform an action (or more than one).
 * 
 * @author Jonathan Lovelace
 */
public interface FunctionalModule extends Module {
	/**
	 * Perform the action, perhaps dispatching this to other methods.
	 * 
	 * @param action
	 *            Which action to perform
	 * @param args
	 *            Anything used by the action (and perhaps a Container for
	 *            results to be returned in)
	 */
	void action(long action, Module... args);
	/**
	 * @return The actions this module supports
	 */
	Set<Action> supportedActions();
}
