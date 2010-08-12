package common;
/**
 * A message acknowledging reciept of another message
 * @author Jonathan Lovelace
 *
 */
public class AckMessage extends ProtocolMessage {
	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = 4133680635586364370L;

	/**
	 * Constructor.
	 * @param msg the message to acknowledge
	 */
	public AckMessage(final ProtocolMessage msg) {
		super(MessageType.Ack, msg);
	}
}
