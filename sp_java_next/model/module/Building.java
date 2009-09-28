package model.module;

/**
 * A module that can perform an action (or more than one).
 * 
 * FIXME: A building, in the new parlance, is a kind of Location, so this
 * interface should be renamed (or its method moved to, perhaps, Implement?).
 * 
 * @author Jonathan Lovelace
 */
public interface Building {
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
	// args[] should be the "any number of arguments" construct, but the
	// compiler doesn't like what I remember that as.
}
