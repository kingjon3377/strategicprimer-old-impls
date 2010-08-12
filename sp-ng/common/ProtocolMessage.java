package common;

import java.io.Serializable;

/**
 * The protocol to be passed over the wire between client and server. Perhaps
 * should be split into messages from client to server and vice versa.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class ProtocolMessage implements Serializable {
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = 2962655591210661540L;

	/**
	 * Kinds of messages.
	 */
	public enum MessageType {
		/**
		 * Shut down the server
		 */
		Quit,
		/**
		 * Load a map file
		 */
		Load,
		/**
		 * Acknowledge another message
		 */
		Ack;
	}
	/**
	 * What kind of message this is.
	 */
	private MessageType messageType;
	
	/**
	 * @return what kind of message this is.
	 */
	public MessageType getMessageType() {
		return messageType;
	}

	/**
	 * Constructor.
	 * @param msgType what kind of message this is
	 * @param args arguments
	 */
	public ProtocolMessage(final MessageType msgType, final Object... args) {
		messageType = msgType;
	}
}
