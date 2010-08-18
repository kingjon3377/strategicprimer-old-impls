package common;

/**
 * An exception to throw or send when you don't know how to handle a message
 * you've received.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class UnknownMessageException extends Exception {
	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = 6567517338088151615L;

	/**
	 * Constructor
	 */
	public UnknownMessageException() {
		super("Don't know how to handle a message");
	}
}
