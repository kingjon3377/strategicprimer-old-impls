package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import common.ProtocolMessage;

/**
 * A wrapper around a client-side socket to simplify API calls on the server.
 * @author Jonathan Lovelace
 *
 */
public class ClientAPIWrapper {
	/**
	 * Constructor.
	 * @param sock the socket this is a wrapper around.
	 * @throws IOException on I/O error setting up streams
	 */
	public ClientAPIWrapper(final Socket sock) throws IOException {
		socket = sock;
		os = new ObjectOutputStream(sock.getOutputStream());
		is = new ObjectInputStream(sock.getInputStream());
	}
	/**
	 * The socket this is a wrapper around.
	 */
	private Socket socket;
	/**
	 * The stream to send data into the socket.
	 */
	private ObjectOutputStream os;
	/**
	 * The stream to read data from the socket.
	 */
	private ObjectInputStream is;
	/**
	 * Send a message on the socket.
	 * @param message the message to send
	 * @throws IOException on I/O error
	 */
	public void send(final ProtocolMessage message) throws IOException {
		os.writeObject(message);
	}
	/**
	 * Receive a message from the socket
	 * @return the message received.
	 * @throws IOException on I/O error
	 * @throws ClassNotFoundException when deserialization fails
	 */
	public ProtocolMessage receive() throws IOException, ClassNotFoundException {
		Object ack = is.readObject();
		if (!(ack instanceof ProtocolMessage)) {
			throw new IllegalStateException("Not a ProtocolMessage on the wire");
		} else {
			return (ProtocolMessage) ack;
		}
	}
	/**
	 * Make an API call
	 * @param message the message to send
	 * @return the message received
	 * @throws ClassNotFoundException when deserialization of the reply fails
	 * @throws IOException on I/O error
	 */
	public ProtocolMessage call(final ProtocolMessage message) throws IOException, ClassNotFoundException {
		send(message);
		return receive();
	}
	/**
	 * Close the connection.
	 * @throws IOException on I/O error
	 */
	public void close() throws IOException {
		os.close();
		is.close();
		socket.close();
	}
}
