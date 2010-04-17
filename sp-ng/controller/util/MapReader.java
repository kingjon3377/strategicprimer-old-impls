package controller.util;

import java.io.BufferedReader;
import java.io.IOException;

import model.map.SPMap;

/**
 * A class to read a map from file.
 * 
 * @author Jonathan Lovelace.
 * 
 *         Stream-handling code based vaguely on the EasyIO framework from
 *         <i>Java: An Introduction to Computing</i> by Adams, Nyhoff, and
 *         Nyhoff.
 * 
 */
public class MapReader {
	/**
	 * Read a map from a file.
	 * 
	 * @param filename
	 *            the file to read from
	 * @return the map in that file
	 */
	public SPMap readMap(final String filename) {
		return new SPMap();
	}
	/**
	 * Position the cursor before the next relevant character.
	 * @param istream the stream we're reading from
	 * @throws IOException on EOF or other I/O error
	 */
	private static void prepare(final BufferedReader istream) throws IOException {
		int ch;
		while (true) {
			istream.mark(2);
			ch = istream.read();
			if (ch < 1) {
				break;
			} else if (!Character.isWhitespace((char) ch)) {
				istream.reset();
				break;
			}
		}
	}

	/**
	 * Read the next value. If our map file format adds something more than
	 * integers, we'll need to modularize this.
	 * 
	 * @param istream
	 *            the stream we're reading from
	 * @return the next (integer) value in it, which we've then passed by.
	 * @throws IOException
	 *             on EOF or other I/O error
	 */
	private static int readValue(final BufferedReader istream)
			throws IOException {
		int ch;
		prepare(istream);
		StringBuilder builder = new StringBuilder();
		while (true) {
			istream.mark(4);
			ch = istream.read();
			if (ch < 1 || Character.isWhitespace((char) ch)) {
				istream.reset();
				break;
			} else {
				builder.append((char) ch);
			}
		}
		return Integer.parseInt(builder.toString());
	}
}
