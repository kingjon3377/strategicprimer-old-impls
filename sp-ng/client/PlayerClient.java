package client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

/**
 * A client for a player
 * 
 * @author Jonathan Lovelace
 */
public class PlayerClient extends JFrame {
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = 2455613435519684475L;
	/**
	 * A logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(PlayerClient.class.getName());
	/**
	 * Main method.
	 * @param args ignored
	 */
	public static void main(final String[] args) {
		try {
			new PlayerClient(new ClientAPIWrapper(ClientAPIWrapper.connect())).setVisible(true);
		} catch (UnknownHostException e) {
			LOGGER.log(Level.SEVERE, "Localhost not found", e);
			System.exit(1);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "I/O error--maybe server wouldn't start?", e);
			System.exit(2);
		}
	}
	/**
	 * Our API connection to the server
	 */
	private final ClientAPIWrapper api;
	/**
	 * Constructor.
	 * @param apiWrap Our API connection to the server.
	 */
	public PlayerClient(final ClientAPIWrapper apiWrap) {
		api = apiWrap;
	}
}
