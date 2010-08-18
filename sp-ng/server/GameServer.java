package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.map.SPMap;

import common.NotReadyException;
import common.SPPoint;

import controller.util.MapReader;

/**
 * The server that manages the game. TODO: eventually we want to be able to run
 * multiple servers on a single VM. But don't worry about that yet.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class GameServer extends Thread {
	// ESCA-JAVA0057:
	/**
	 * Constructor, to make warnings go away.
	 */
	public GameServer() {
		super();
	}

	/**
	 * Logger; also used for synchronization
	 */
	private static final Logger LOGGER = Logger.getLogger(GameServer.class
			.getName());
	/**
	 * The singleton server.
	 */
	private static GameServer server = null;

	/**
	 * @return the singleton server
	 */
	public static GameServer getGameServer() {
		synchronized (LOGGER) {
			return server;
		}
	}

	/**
	 * @param srv
	 *            a server to perhaps make the singleton server
	 * @throws DuplicateServerException
	 *             when we already had a server
	 */
	private static void setGameServer(final GameServer srv)
			throws DuplicateServerException {
		synchronized (LOGGER) {
			if (server == null) {
				server = srv;
			} else if (server != srv) {
				throw new DuplicateServerException();
			}
		}
	}

	/**
	 * Run the server
	 */
	@Override
	public void run() {
		try {
			setGameServer(this);
		} catch (final DuplicateServerException except) {
			try {
				getGameServer().join();
			} catch (final InterruptedException exc) {
				LOGGER.log(Level.WARNING, "Secondary GameServer interrupted",
						exc);
			}
		}
	}

	/**
	 * Load a map from file
	 * 
	 * @param filename
	 *            the file to load from
	 * @throws IOException
	 *             on I/O error while reading the file
	 * @throws FileNotFoundException
	 *             when the file doesn't exist
	 */
	public void loadMap(final String filename) throws IOException,
			FileNotFoundException {
		setMap(new MapReader().readMap(filename));
	}

	/**
	 * The current map.
	 */
	private SPMap map;

	/**
	 * @return the current map
	 */
	public SPMap getMap() {
		return map;
	}

	/**
	 * @param newMap
	 *            the new map
	 */
	public void setMap(final SPMap newMap) {
		map = newMap;
	}

	/**
	 * @return the size of the map.
	 * @throws NotReadyException
	 *             if the map isn't loaded
	 */
	public SPPoint getMapSize() throws NotReadyException {
		if (map == null) {
			throw new NotReadyException("Don't have a map yet");
		} else {
			return new SPPoint(map.getRows(), map.getCols());
		}
	}

	/**
	 * An exception to throw when we already had a server. IllegalStateException
	 * is unchecked.
	 * 
	 * @author Jonathan Lovelace
	 * 
	 */
	private static class DuplicateServerException extends Exception {
		/**
		 * Version UID for serialization.
		 */
		private static final long serialVersionUID = -2997677357518749604L;

		// ESCA-JAVA0128:
		/**
		 * Constructor.
		 */
		public DuplicateServerException() {
			super("Already had a server");
		}
	}
}
