package model.module;

/**
 * A resource in the game.
 * @author Jonathan Lovelace
 *
 */
public interface Resource {
	/**
	 * Which resource is this?
	 * @return the resource ID.
	 */
	long getResourceID();
	
	/**
	 * @return how much of the resource there is
	 */
	double getAmount();
	
	/**
	 * Split the resource.
	 * @param amount The amount to put into the split-off resource 
	 * @return A split-off resource
	 */
	Resource split(double amount);
	
	/**
	 * Remove the specified amount from the resource
	 * @param amount The amount of the resource to remove.
	 */
	void tap(double amount);
}
