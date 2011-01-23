package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import common.ClientTile;
import common.IsAdminMessage;
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
		int port = 9099;
		int cport = new Random().nextInt(64335) + 1200;
		Socket sock;
		try {
			addr = InetAddress.getLocalHost();
			sock = ClientAPIWrapper.connect(addr, port, cport);
		} catch (final UnknownHostException except) {
			LOGGER.log(Level.SEVERE, "Unknown host localhost", except);
			System.exit(1);
			return;
		} catch (final IOException except) {
			LOGGER.log(Level.SEVERE, "Opening socket failed", except);
			System.exit(2);
			return;
		}
		ClientAPIWrapper api;
		try {
			api = new ClientAPIWrapper(sock);
		} catch (IOException except) {
			LOGGER.log(Level.SEVERE, "Opening streams failed", except);
			closeSocket(sock);
			System.exit(3);
			return;
		}
		ProtocolMessage ack;
		try {
			ack = api.call(new IsAdminMessage(true));
			if (ProtocolMessage.MessageType.Ack.equals(ack.getMessageType())) {
				LOGGER.info("Initial message acknowledged");
			} else if (ProtocolMessage.MessageType.Fail.equals(ack
					.getMessageType())) {
				LOGGER.warning("Initial message failed");
			} else {
				LOGGER.warning("Unexpected reply to initial message");
			}
		} catch (final IOException except) {
			LOGGER.log(Level.SEVERE, "I/O error receiving reply", except);
		} catch (final ClassNotFoundException except) {
			LOGGER.log(Level.SEVERE, "Wasn't the ACK or Fail we expected",
					except);
		}
		try {
			ack = api.call(new LoadMessage("/home/kingjon/test.map"));
			if (ProtocolMessage.MessageType.Ack.equals(ack.getMessageType())) {
				LOGGER.info("Load message acknowledged");
			} else if (ProtocolMessage.MessageType.Fail.equals(ack
					.getMessageType())) {
				LOGGER.info("Server failed to load map");
			} else {
				LOGGER.warning("Unexpected reply to load message");
			}
		} catch (final IOException except) {
			LOGGER.log(Level.SEVERE, "I/O error receiving reply", except);
		} catch (final ClassNotFoundException except) {
			LOGGER.log(Level.SEVERE, "Wasn't the ACK or Fail we expected",
					except);
		}
		try {
			ack = api.call(new QueryMessage("size"));
			if (ProtocolMessage.MessageType.Reply.equals(ack.getMessageType())) {
				if ("size".equals(ack.getFirstArg())
						&& ack.getSecondArg() instanceof SPPoint) {
					LOGGER.info("Size of map is "
							+ ((SPPoint) ack.getSecondArg()).row()
							+ " rows and "
							+ ((SPPoint) ack.getSecondArg()).col()
							+ " columns.");
				} else {
					LOGGER
							.warning("Didn't get the reply we expected to size query.");
				}
			} else if (ProtocolMessage.MessageType.Fail.equals(ack
					.getMessageType())) {
				LOGGER.info("Server failed to determine map size");
			} else {
				LOGGER.warning("Unexpected reply to size query");
			}
		} catch (final IOException except) {
			LOGGER.log(Level.SEVERE, "I/O error receiving reply", except);
		} catch (final ClassNotFoundException except) {
			LOGGER.log(Level.SEVERE, "Wasn't the Reply or Fail we expected",
					except);
		}
		try {
			List<SPPoint> tempList = new ArrayList<SPPoint>();
			tempList.add(new SPPoint(0, 0));
			ack = api.call(new QueryMessage("tiles", tempList));
			if (ProtocolMessage.MessageType.Reply.equals(ack.getMessageType())) {
				if ("tiles".equals(ack.getFirstArg())
						&& ack.getSecondArg() instanceof List<?>) {
					LOGGER.info("Tile at (0, 0) is "
							+ ((List<ClientTile>) ack.getSecondArg()).get(0)
									.getType());
				} else {
					LOGGER
							.warning("Didn't get the reply we expected to tile query.");
				}
			} else if (ProtocolMessage.MessageType.Fail.equals(ack
					.getMessageType())) {
				LOGGER.info("Server failed to get tile");
			} else {
				LOGGER.warning("Unexpected reply to tile query");
			}
		} catch (final IOException except) {
			LOGGER.log(Level.SEVERE, "I/O error receiving reply", except);
		} catch (final ClassNotFoundException except) {
			LOGGER.log(Level.SEVERE, "Wasn't the Reply or Fail we expected",
					except);
		}
		try {
			ack = api.call(new QuitMessage());
			LOGGER.info("Quit acknowledged: " + ack);
		} catch (final IOException except) {
			LOGGER.log(Level.SEVERE, "I/O error receiving ACK", except);
		} catch (final ClassNotFoundException except) {
			LOGGER.log(Level.SEVERE, "Wasn't the ACK we expected", except);
		}
		try {
			api.close();
		} catch (IOException e) {
			closeSocket(sock);
		}
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
