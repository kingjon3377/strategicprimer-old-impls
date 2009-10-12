package model.location;

/**
 * An event on a tile (or, more generally, in a location). That is, this is
 * something that anyone passing through or exploring the location might
 * discover.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class LocationEvent {
	/**
	 * The number of the event
	 */
	private final int number;
	/**
	 * Constructor.
	 * @param num The number of the event
	 */
	public LocationEvent(final int num) {
		number = num;
	}
	/**
	 * @return the number of the event
	 */
	public int getNumber() {
		return number;
	}
}
