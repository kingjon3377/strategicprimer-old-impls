package common;
/**
 * The initial message, telling the server what kind of client this is.
 * @author Jonathan Lovelace
 *
 */
public class IsAdminMessage extends ProtocolMessage {
	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = 2526087873569690319L;

	/**
	 * Constructor.
	 * @param isAdmin true if this is an admin client, false if it's a player.
	 * @todo other kinds of clients?
	 */
	public IsAdminMessage(final boolean isAdmin) {
		super(MessageType.IsAdmin,isAdmin);
	}
}
