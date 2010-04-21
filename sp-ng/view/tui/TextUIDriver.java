package view.tui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import model.map.SPMap;

/**
 * A more-fully-featured text UI.
 * 
 * @author Jonathan Lovelace
 */
public class TextUIDriver {
	/**
	 * The "control" by which we display the map.
	 */
	private TextMap map;

	/**
	 * Constructor.
	 * 
	 * @param mapObj
	 *            the map we'll be using
	 * @param rows
	 *            how many rows to make visible
	 * @param cols
	 *            how many columns to make visible
	 */
	public TextUIDriver(final SPMap mapObj, final int rows, final int cols) {
		map = new TextMap(mapObj, 2, 2, rows, cols);
	}

	/**
	 * Handle a command and display the map.
	 * 
	 * @param command
	 *            the command to handle
	 * @param out
	 *            the stream we're drawing to
	 */
	public void handleCommand(final char command, final PrintStream out) {
		switch (command) {
		case 'j':
		case 'J':
			map.down();
			break;
		case 'k':
		case 'K':
			map.up();
			break;
		case 'l':
		case 'L':
			map.right();
			break;
		case 'h':
		case 'H':
			map.left();
			break;
		default:
			// ignore
			break;
		}
		map.paint(out);
	}

	/**
	 * Entry point for the program.
	 * 
	 * @param args
	 *            ignored for now
	 */
	public static void main(final String[] args) {
		TextUIDriver tuid = new TextUIDriver(new SPMap(), 5, 5);
		tuid.handleCommand(' ', System.out);
		while (true) {
			try {
				final char command = (char) System.in.read();
				if (command == 'Q' || command == 'q') {
					break;
				} else {
					tuid.handleCommand(command, System.out);
				}
			} catch (final IOException except) {
				except.printStackTrace();
			}
		}
	}
}
