package common;

/**
 * A message to tell the server to load a map
 * 
 * @author Jonathan Lovelace
 * 
 */
public class LoadMessage extends ProtocolMessage {
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = 3328622262320962282L;

	/**
	 * Constructor.
	 * 
	 * @param filename
	 *            the name of the file to load
	 */
	public LoadMessage(final String filename) {
		super(MessageType.Load, filename);
	}
}
