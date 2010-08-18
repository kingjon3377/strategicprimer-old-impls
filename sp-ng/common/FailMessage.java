package common;

/**
 * A message to report failure in dealing with another message.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class FailMessage extends ProtocolMessage {
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = -8790192055382388257L;

	/**
	 * Constructor.
	 * 
	 * @param msg
	 *            the message that caused the failure
	 * @param except
	 *            the exception that was raised, if any
	 */
	public FailMessage(final ProtocolMessage msg, final Exception except) {
		super(MessageType.Fail, msg, except);
	}
}
