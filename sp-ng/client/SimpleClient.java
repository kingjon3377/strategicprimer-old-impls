package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import common.LoadMessage;
import common.ProtocolMessage;
import common.QueryMessage;
import common.QuitMessage;
import common.SPPoint;

/**
 * A simple client, primarily to test the server. Should perhaps eventually
 * become the wrapper around the network API.
 * 
 * @author Jonathan Lovelace
 */
public class SimpleClient {
	/**
	 * Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(SimpleClient.class
			.getName());

	/**
	 * @param args
	 *            Ignored
	 */
	public static void main(final String[] args) {
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
		} catch (final UnknownHostException except) {
			LOGGER.log(Level.SEVERE, "Unknown host localhost", except);
			System.exit(1);
			return;
		}
		int port = 9099;
		Socket sock;
		try {
			sock = new Socket(addr, port, InetAddress.getLocalHost(), 11236);
		} catch (final IOException except) {
			LOGGER.log(Level.SEVERE, "Opening socket failed", except);
			System.exit(2);
			return;
		}
		ObjectOutputStream os;
		ObjectInputStream is;
		try {
			os = new ObjectOutputStream(sock.getOutputStream());
			is = new ObjectInputStream(sock.getInputStream());
		} catch (IOException except) {
			LOGGER.log(Level.SEVERE, "Opening streams failed", except);
			closeSocket(sock);
			System.exit(3);
			return;
		}
		try {
			os.writeObject(new LoadMessage("/home/kingjon/test.map"));
		} catch (final IOException except) {
			LOGGER.log(Level.SEVERE, "Sending load message failed; continuing",
					except);
		}
		Object ack;
		try {
			ack = is.readObject();
			if (ack instanceof ProtocolMessage) {
				if (ProtocolMessage.MessageType.Ack
						.equals(((ProtocolMessage) ack).getMessageType())) {
					LOGGER.info("Load message acknowledged");
				} else if (ProtocolMessage.MessageType.Fail
						.equals(((ProtocolMessage) ack).getMessageType())) {
					LOGGER.info("Server failed to load map");
				} else {
					LOGGER.warning("Unexpected reply to load message");
				}
			}
		} catch (final IOException except) {
			LOGGER.log(Level.SEVERE, "I/O error receiving reply", except);
		} catch (final ClassNotFoundException except) {
			LOGGER.log(Level.SEVERE, "Wasn't the ACK or Fail we expected",
					except);
		}
		try {
			os.writeObject(new QueryMessage("size"));
		} catch (final IOException except) {
			LOGGER.log(Level.SEVERE,
					"Sending size-query message failed; continuing", except);
		}
		try {
			ack = is.readObject();
			if (ack instanceof ProtocolMessage) {
				if (ProtocolMessage.MessageType.Reply
						.equals(((ProtocolMessage) ack).getMessageType())) {
					if ("size".equals(((ProtocolMessage) ack).getFirstArg())
							&& ((ProtocolMessage) ack).getSecondArg() instanceof SPPoint) {
						LOGGER.info("Size of map is "
								+ ((SPPoint) ((ProtocolMessage) ack)
										.getSecondArg()).row()
								+ " rows and "
								+ ((SPPoint) ((ProtocolMessage) ack)
										.getSecondArg()).col() + " columns.");
					} else {
						LOGGER
								.warning("Didn't get the reply we expected to size query.");
					}
				} else if (ProtocolMessage.MessageType.Fail
						.equals(((ProtocolMessage) ack).getMessageType())) {
					LOGGER.info("Server failed to determine map size");
				} else {
					LOGGER.warning("Unexpected reply to size query");
				}
			}
		} catch (final IOException except) {
			LOGGER.log(Level.SEVERE, "I/O error receiving reply", except);
		} catch (final ClassNotFoundException except) {
			LOGGER.log(Level.SEVERE, "Wasn't the Reply or Fail we expected",
					except);
		}
		try {
			os.writeObject(new QuitMessage());
		} catch (final IOException except) {
			LOGGER.log(Level.SEVERE, "Sending quit message failed; continuing",
					except);
		}
		try {
			ack = is.readObject();
			LOGGER.info("Quit acknowledged: " + ack);
		} catch (final IOException except) {
			LOGGER.log(Level.SEVERE, "I/O error receiving ACK", except);
		} catch (final ClassNotFoundException except) {
			LOGGER.log(Level.SEVERE, "Wasn't the ACK we expected", except);
		}
		closeSocket(sock);
		return;
	}

	private static void closeSocket(final Socket sock) {
		try {
			sock.close();
		} catch (final IOException except) {
			LOGGER.log(Level.SEVERE, "I/O error while closing socket", except);
		}
	}
}
