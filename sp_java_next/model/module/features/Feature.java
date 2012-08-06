package model.module.features;

import model.module.Module;

/**
 * An interface for landscape features to implement.
 * 
 * @author Jonathan Lovelace
 */
public interface Feature extends Module {
	/**
	 * @return a description of the feature (suitable for giving to the user
	 *         when the feature is encountered for the first time)
	 */
	String description();
}
