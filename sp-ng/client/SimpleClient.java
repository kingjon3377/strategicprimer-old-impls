package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.SocketListener;

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
			SocketListener.LISTENER.start();
			while (!SocketListener.LISTENER.ready()) {
				try {
					Thread.sleep(90);
				} catch (InterruptedException e) {
					// ignore
				}
			}
			main(args);
			// System.exit(2);
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
		try {
			api.send(new IsAdminMessage(true));
			LOGGER.info("Sent initial message");
		} catch (final IOException except) {
			LOGGER.log(Level.SEVERE,
					"Sending initial message failed; continuing", except);
		}
		ProtocolMessage ack;
		try {
			ack = api.receive();
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
			api.send(new LoadMessage("/home/kingjon/test.map"));
		} catch (final IOException except) {
			LOGGER.log(Level.SEVERE, "Sending load message failed; continuing",
					except);
		}
		try {
			ack = api.receive();
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
			api.send(new QueryMessage("size"));
		} catch (final IOException except) {
			LOGGER.log(Level.SEVERE,
					"Sending size-query message failed; continuing", except);
		}
		try {
			ack = api.receive();
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
			api.send(new QueryMessage("tiles", tempList));
		} catch (final IOException except) {
			LOGGER.log(Level.SEVERE, "asking for tile failed; continuing",
					except);
		}
		try {
			ack = api.receive();
			if (ProtocolMessage.MessageType.Reply.equals(ack.getMessageType())) {
				if ("tiles".equals(((ProtocolMessage) ack).getFirstArg())
						&& ack.getSecondArg() instanceof List<?>) {
					LOGGER.info("Tile at (0, 0) is "
							+ ((List<ClientTile>) ((ProtocolMessage) ack)
									.getSecondArg()).get(0).getType());
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
			api.send(new QuitMessage());
		} catch (final IOException except) {
			LOGGER.log(Level.SEVERE, "Sending quit message failed; continuing",
					except);
		}
		try {
			ack = api.receive();
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
