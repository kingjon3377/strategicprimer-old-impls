package utils;
/**
 * A class to get rid of alleged DU-anomalies in the MapReaderDriver.
 * @author Jonathan Lovelace
 *
 */
public class Incrementor {
	/**
	 * The counter
	 */
	private int nextValue;
	/**
	 * Constructor
	 */
	public Incrementor() {
		super();
		nextValue = -1;
	}
	/**
	 * Increments and returns the next value.
	 * @return the next value
	 */
	public int getNextValue() {
		nextValue++;
		return nextValue;
	}
}
