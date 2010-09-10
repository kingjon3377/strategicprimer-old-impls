package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import common.AckMessage;
import common.FailMessage;
import common.IsAdminMessage;
import common.NotReadyException;
import common.ProtocolMessage;
import common.QuitMessage;

/**
 * A server to determine what kind of client we're handling and hand control off
 * appropriately. The main() method perhaps also ought to be in here.
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
	 * Main method
	 */
	@Override
	public void run() {
		// ESCA-JAVA0177:
		ObjectInputStream is;
		// ESCA-JAVA0177:
		ObjectOutputStream os;
		try {
			is = new ObjectInputStream(socket.getInputStream());
			os = new ObjectOutputStream(socket.getOutputStream());
			os.flush();
			socket.setSoTimeout(0);
		} catch (IOException except) {
			LOGGER.log(Level.SEVERE, "I/O error setting up streams", except);
			return;
		}
		try {
			Object obj = is.readObject();
			LOGGER.info("Read what should be an IsAdminMessage");
			while(!"quit".equals(obj) && !(obj instanceof QuitMessage)) {
				if (obj instanceof IsAdminMessage) {
					if ((Boolean) (((IsAdminMessage) obj).getFirstArg())) {
						LOGGER.info("Launching admin APIServer");
						os.writeObject(new AckMessage((ProtocolMessage) obj));
						APIServer apiserv = new APIServer(socket, is, os);
						apiserv.start();
						apiserv.join();
					} else {
						LOGGER.info("Launching player APIServer");
						os.writeObject(new AckMessage((ProtocolMessage) obj));
						APIServer apiserv = new APIServer(socket, is, os);
						apiserv.start();
						apiserv.join();
					}
					break;
				} else if (obj instanceof ProtocolMessage) {
					LOGGER.info("A ProcolMessage we couldn't handle");
					os.writeObject(new FailMessage((ProtocolMessage) obj, new NotReadyException("Send an IsAdminMessage first")));
				} else {
					LOGGER.warning("Read something other than a ProtocolMessage");
				}
				obj = is.readObject();
			}
		} catch (final IOException except){
			LOGGER.log(Level.SEVERE, "I/O error in the loop", except);
		} catch (ClassNotFoundException except) {
			LOGGER.log(Level.SEVERE, "Protocol incompatibility", except);
		} catch (final InterruptedException except) {
			LOGGER.log(Level.INFO, "Interrupted after joining APIServer", except);
		}
	}
}
