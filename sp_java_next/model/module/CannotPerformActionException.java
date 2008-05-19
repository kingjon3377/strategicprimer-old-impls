package model.module;

/**
 * Thrown when a Module tries to perform an action it doesn't know how to do.
 * 
 * @author Jonathan Lovelace
 */
public class CannotPerformActionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1155239676509329662L;

	/**
	 * @see Exception#Exception(String)
	 * @param message
	 *            a message describing the circumstances
	 */
	public CannotPerformActionException(final String message) {
		super(message);
	}
}
