package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import common.AckMessage;
import common.FailMessage;
import common.NotReadyException;
import common.ProtocolMessage;
import common.QueryMessage;
import common.ReplyMessage;
import common.UnknownMessageException;

/**
 * A server for SP; this class, which perhaps ought to be renamed, is what
 * listens for connections from clients.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class SPServer extends Thread {
	/**
	 * Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(SPServer.class
			.getName());

	/**
	 * @param args
	 *            Ignored
	 */
	public static void main(String[] args) {
		final int port = 9099;
		ServerSocket sock;
		try {
			sock = new ServerSocket(port);
		} catch (IOException except) {
			LOGGER.log(Level.SEVERE, "Failed to start server", except);
			System.exit(1);
			return;
		}
		while (true) {
			LOGGER.info("Ready to receive clients");
			Socket clientSock;
			try {
				clientSock = sock.accept();
			} catch (IOException except) {
				LOGGER.log(Level.SEVERE, "I/O error in accepting connection",
						except);
				return;
			}
			LOGGER.info("Accepted a client from "
					+ clientSock.getInetAddress().getHostAddress() + " at "
					+ new Date());
			SPServer server = new SPServer(clientSock);
			server.start();
		}
	}

	/**
	 * The connection to the client
	 */
	private Socket socket;

	/**
	 * Constructor
	 * 
	 * @param sock
	 *            the socket connecting us to the client
	 */
	public SPServer(final Socket sock) {
		socket = sock;
	}

	/**
	 * Should we quit?
	 */
	private boolean quit = false;

	/**
	 * Main method
	 */
	@Override
	public void run() {
		LOGGER.info("Running client");
		if (GameServer.getGameServer() == null) {
			new GameServer().start();
		}
		// ESCA-JAVA0177:
		ObjectInputStream is;
		// ESCA-JAVA0177:
		ObjectOutputStream os;
		try {
			is = new ObjectInputStream(socket.getInputStream());
			os = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException except) {
			LOGGER.log(Level.SEVERE, "I/O error setting up streams", except);
			return;
		}
		try {
			Object obj = is.readObject();
			while (!"quit".equals(obj) && !quit) {
				if (obj instanceof ProtocolMessage) {
					handleProtocol((ProtocolMessage) obj, os);
				}
				if (quit)
					break;
				obj = is.readObject();
			}
		} catch (IOException except) {
			LOGGER.log(Level.SEVERE, "I/O error in the loop", except);
		} catch (ClassNotFoundException except) {
			LOGGER.log(Level.SEVERE, "Protocol incompatibility", except);
		}
		try {
			socket.close();
		} catch (IOException except) {
			LOGGER.log(Level.SEVERE, "I/O error while closing socket", except);
		}
		LOGGER.info("Closing server thread");
	}

	private void handleProtocol(final ProtocolMessage obj,
			final ObjectOutputStream os) {
		switch (obj.getMessageType()) {
		case Quit:
			quit = true;
			acknowledge(obj, os);
			break;
		case Load:
			try {
				GameServer.getGameServer().loadMap((String) obj.getFirstArg());
				acknowledge(obj, os);
			} catch (FileNotFoundException e) {
				LOGGER
						.log(Level.WARNING, "Tried to load a nonexistent file",
								e);
				fail(obj, e, os);
			} catch (IOException e) {
				LOGGER.log(Level.WARNING, "Loading from file caused I/O error",
						e);
				fail(obj, e, os);
			}
			break;
		case Query:
			try {
				reply((QueryMessage) obj, handleQuery((QueryMessage) obj), os);
			} catch (final UnknownMessageException except) {
				LOGGER.log(Level.WARNING,
						"Got a query we don't know how to handle", except);
				fail(obj, except, os);
			} catch (NotReadyException except) {
				LOGGER.log(Level.WARNING,
						"Got query for map size before map was loaded", except);
				fail(obj, except, os);
			}
			break;
		default:
			fail(obj, new UnknownMessageException(), os);
			break;
		}
	}

	/**
	 * Send a reply to a query
	 * 
	 * @param query
	 *            the query message we're replying to
	 * @param reply
	 *            the reply to send
	 * @param os
	 *            the stream to send it on
	 */
	private static void reply(QueryMessage query, Object reply,
			ObjectOutputStream os) {
		try {
			os.writeObject(new ReplyMessage((String) query.getFirstArg(),
					reply, query));
		} catch (final IOException except) {
			LOGGER.log(Level.SEVERE, "I/O error while sending reply", except);
		}
	}

	/**
	 * Acknowledge
	 * 
	 * @param msg
	 *            the message to acknowledge
	 * @param os
	 *            The stream to send the ACK on
	 */
	private static void acknowledge(final ProtocolMessage msg,
			final ObjectOutputStream os) {
		try {
			os.writeObject(new AckMessage(msg));
		} catch (final IOException except) {
			LOGGER.log(Level.SEVERE, "I/O error while sending ACK", except);
		}
	}

	/**
	 * Send a "failure" message
	 * 
	 * @param msg
	 *            the message that caused the failure
	 * @param except
	 *            the exception that was raised
	 * @param os
	 *            the steram to send the message on
	 */
	private static void fail(final ProtocolMessage msg, final Exception except,
			final ObjectOutputStream os) {
		try {
			os.writeObject(new FailMessage(msg, except));
		} catch (final IOException exc) {
			LOGGER.log(Level.SEVERE, "I/O error while sending failure message",
					exc);
		}
	}

	/**
	 * Handle a query.
	 * 
	 * @param msg
	 *            the message to handle.
	 * @return the object to send in the reply
	 * @throws UnknownMessageException
	 *             when we don't know how to handle that kind of query
	 * @throws NotReadyException
	 *             when the map isn't loaded yet
	 */
	private static Object handleQuery(final QueryMessage msg)
			throws UnknownMessageException, NotReadyException {
		if ("size".equals(msg.getFirstArg())) {
			return GameServer.getGameServer().getMapSize();
		} else {
			throw new UnknownMessageException();
		}
	}
}
