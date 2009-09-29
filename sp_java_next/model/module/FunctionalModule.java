package model.module;

/**
 * A module that can perform an action (or more than one).
 * 
 * FIXME: A building, in the new parlance, is a kind of Location, so this
 * interface should be renamed (or its method moved to, perhaps, Implement?).
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
}
