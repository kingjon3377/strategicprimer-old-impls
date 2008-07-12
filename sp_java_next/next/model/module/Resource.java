package next.model.module;

/**
 * A resource in the game. This is something that is used up (at least usually),
 * which is quantifiable, and which (generally) isn't a unit, implement, or any
 * other kind of module. (Unfortunately there's no way to specify that ...)
 * 
 * @author kingjon
 * 
 */
public interface Resource extends Module {
	/**
	 * @return how much of this resource there is in this collection of it.
	 */
	double getQuantity();

	/**
	 * Remove the specified quantity of the resource and return it in a new
	 * object.
	 * 
	 * What to do when there isn't enough is unspecified; some resources will
	 * throw an exception, while others will let the user go into debt. There
	 * ought to be a way to have some resources let the user specify what to do.
	 * 
	 * @param _quantity
	 *            How much to remove
	 * @return The removed collection of the resource.
	 */
	Resource tap(double _quantity);
}
