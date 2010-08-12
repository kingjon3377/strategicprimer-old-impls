package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import common.AckMessage;
import common.ProtocolMessage;

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
				if (quit) break;
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
			// loadFile(obj);
			break;
		default:
			break;
		}
	}
	/**
	 * Acknowledge
	 * @param msg the message to acknowledge
	 * @param os The stream to send the ACK on
	 */
	private static void acknowledge(final ProtocolMessage msg, final ObjectOutputStream os) {
		try {
			os.writeObject(new AckMessage(msg));
		} catch (final IOException except) {
			LOGGER.log(Level.SEVERE, "I/O error while sending ACK", except);
		}
	}
}
