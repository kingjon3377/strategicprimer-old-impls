package common;

/**
 * An exception to throw when you're not yet ready.
 * 
 * @author kingjon
 * 
 */
public class NotReadyException extends Exception {
	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = -1823624322109797910L;

	/**
	 * Constructor.
	 * 
	 * @param msg
	 *            the exception message.
	 */
	public NotReadyException(final String msg) {
		super(msg);
	}
}
