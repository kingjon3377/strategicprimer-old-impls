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
		Ack,
		/**
		 * The opposite of Ack; dealing with a message failed.
		 */
		Fail;
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
	 * The first argument, if any
	 */
	private Object arg1;
	/**
	 * @return the first argument
	 */
	public Object getFirstArg() {
		return arg1;
	}
	/**
	 * Constructor.
	 * @param msgType what kind of message this is
	 * @param args arguments
	 */
	protected ProtocolMessage(final MessageType msgType, final Object... args) {
		messageType = msgType;
		if (args.length > 0) {
			arg1 = args[0];
		}
	}
}
