package common;

/**
 * A message to query the other side.
 * 
 * @author Jonathan Lovelace
 */
public class QueryMessage extends ProtocolMessage {
	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = -8405409233529079182L;

	/**
	 * Constructor.
	 * 
	 * @param query
	 *            what we're querying. TODO: enumerate this?
	 */
	public QueryMessage(final String query) {
		super(ProtocolMessage.MessageType.Query, query);
	}
	/**
	 * Constructor with argument.
	 * @param query what we're querying
	 * @param arg the argument
	 */
	public QueryMessage(final String query, final Object arg) {
		super(ProtocolMessage.MessageType.Query, query, arg);
	}
}
