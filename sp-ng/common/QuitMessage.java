package common;

/**
 * A Quit message
 * 
 * @author Jonathan Lovelace
 * 
 */
public class QuitMessage extends ProtocolMessage {
	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = -2824076581676373218L;

	/**
	 * Constructor
	 */
	public QuitMessage() {
		super(MessageType.Quit);
	}
}
