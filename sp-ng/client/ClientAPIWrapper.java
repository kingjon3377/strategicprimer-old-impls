package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import server.SocketListener;

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
	
	/**
	 * Connect to the server, starting it first if it isn't up.
	 * @param host the host to connect to. (The "if not up" logic assumes it's the local host.)
	 * @param port the port to connect on.
	 * @param cport the client-side port.
	 * @return a socket connected to the server
	 * @throws IOException on I/O error after starting the server
	 * @throws UnknownHostException when localhost is unknown
	 */
	public static Socket connect(final InetAddress host, final int port, final int cport) throws UnknownHostException, IOException {
		// ESCA-JAVA0177:
		Socket sock;
			try {
				sock = new Socket(host, port, InetAddress.getLocalHost(), cport);
			} catch (IOException except) {
				SocketListener.LISTENER.start();
				while (!SocketListener.LISTENER.ready()) {
					try {
						// ESCA-JAVA0087:
						Thread.sleep(90);
					} catch (InterruptedException e) {
						// ignore
					}
				}
				sock = new Socket(host, port, InetAddress.getLocalHost(), cport);				
			}
			return sock;
	}
	/**
	 * Connect using default values: server running on localhost port 9099,
	 * client running on a random port.
	 * @throws IOException when the server won't start
	 * @throws UnknownHostException if localhost isn't found
	 * @return a connection to the server on localhost.
	 */
	public static Socket connect() throws UnknownHostException, IOException {
		return connect(InetAddress.getLocalHost(), 9099, new Random().nextInt(64335) + 1200);
	}
}
