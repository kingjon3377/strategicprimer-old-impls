package common;

/**
 * A reply to a query
 * 
 * @author Jonathan Lovelace
 * 
 */
public class ReplyMessage extends ProtocolMessage {
	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = -3313307061524093761L;

	/**
	 * Constructor
	 * 
	 * @param query
	 *            what was queried
	 * @param value
	 *            its value. TODO: subclass for type-safety.
	 * @param msg
	 *            the query message we're replying to
	 */
	public ReplyMessage(final String query, final Object value,
			final QueryMessage msg) {
		super(ProtocolMessage.MessageType.Reply, query, value, msg);
	}
}
