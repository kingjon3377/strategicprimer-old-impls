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
		Fail,
		/**
		 * Ask the other side something. Subclasses should have some way of
		 * specifying what they're querying.
		 */
		Query,
		/**
		 * Reply to a query.
		 */
		Reply;
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
	 * The second argument, if any
	 */
	private Object arg2;

	/**
	 * @return the second argument
	 */
	public Object getSecondArg() {
		return arg2;
	}

	/**
	 * The third argument, if any
	 */
	private Object arg3;

	/**
	 * @return the third argument
	 */
	public Object getThirdArg() {
		return arg3;
	}

	/**
	 * Constructor.
	 * 
	 * @param msgType
	 *            what kind of message this is
	 * @param args
	 *            arguments
	 */
	protected ProtocolMessage(final MessageType msgType, final Object... args) {
		messageType = msgType;
		if (args.length > 0) {
			arg1 = args[0];
		}
		if (args.length > 1) {
			arg2 = args[1];
		}
		if (args.length > 2) {
			arg3 = args[2];
		}
	}
}
