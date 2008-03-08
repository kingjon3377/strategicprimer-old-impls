package model.module;

/**
 * Thrown when a module is unable to move.
 * @author Jonathan Lovelace
 */
public class UnableToMoveException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3627871066467518718L;

	/**
	 * @see Exception#Exception(String)
	 * @param message a message describing the circumstances
	 */
	public UnableToMoveException(String message) {
		super(message);
	}
}
