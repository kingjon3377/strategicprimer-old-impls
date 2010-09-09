package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A thread that listens for clients and spawns SPServers to handle them.
 * 
 * @author Jonathan Lovelace.
 * 
 */
public class SocketListener extends Thread {
	/**
	 * Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(SocketListener.class
			.getName());
	/**
	 * The port we listen on
	 */
	private static final int PORT = 9099;
	/**
	 * The singleton listener.
	 */
	public static final SocketListener LISTENER = new SocketListener();
	/**
	 * Should we stop listening for clients?
	 */
	private boolean quit = false;

	/**
	 * Stop listening for clients and shut down the thread.
	 */
	public void quit() {
		quit = true;
	}

	/**
	 * Can we accept clients? Useful for clients spawning a server.
	 */
	private boolean ready = false;

	/**
	 * @return whether we can accept clients yet.
	 */
	public boolean ready() {
		return ready;
	}

	/**
	 * Main method of the thread.
	 */
	@Override
	public void run() {
		// ESCA-JAVA0177:
		ServerSocket sock;
		try {
			sock = new ServerSocket(PORT);
		} catch (IOException except) {
			LOGGER.log(Level.SEVERE, "Failed to start server", except);
			return;
		}
		try {
			sock.setSoTimeout(1000);
		} catch (SocketException e) {
			LOGGER.log(Level.SEVERE, "Exception setting server socket timeout",
					e);
		}
		while (!quit) {
			LOGGER.info("Ready to receive clients");
			ready = true;
			// ESCA-JAVA0177:
			Socket clientSock;
			try {
				clientSock = sock.accept();
			} catch (SocketTimeoutException except) {
				continue;
			} catch (IOException except) {
				LOGGER.log(Level.SEVERE, "I/O error in accepting connection",
						except);
				return;
			}
			if (clientSock != null) {
				LOGGER.info("Accepted a client from "
						+ clientSock.getInetAddress().getHostAddress() + " at "
						+ new Date());
				APIServer server = new APIServer(clientSock);
				server.start();
			}
		}
	}
}
