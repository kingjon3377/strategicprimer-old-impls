package model.module;

/**
 * A resource in the game.
 * 
 * @author Jonathan Lovelace
 * @deprecated in favor of Resource
 */
@Deprecated
public interface OldResource extends Resource {
	/**
	 * @return how much of the resource there is
	 */
	double getAmount();

	/**
	 * Which resource is this?
	 * 
	 * @return the resource ID.
	 */
	long getResourceID();

	/**
	 * Split the resource.
	 * 
	 * @param amount
	 *            The amount to put into the split-off resource
	 * @return A split-off resource
	 */
	Resource split(double amount);
}
