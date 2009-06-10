package edu.calvin.jsl7.finalproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.HashMap;

import javax.swing.JPanel;

/**
 * A GUI wrapper around a Tile object, to make the MapPanel code less messy. It
 * has a Tile and its Image representation, and knows its width and height. It
 * also knows how to draw a representation of a unit on itself.
 * 
 * @author Jonathan Lovelace
 * @assignment Final Project
 * @course CS108A
 * @semester FA06
 * 
 */
public class GUITile extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4656775986914024351L;
	/**
	 * Default width of a tile
	 */
	public static final int DEFAULT_WIDTH = 10;
	/**
	 * Default height of a tile
	 */
	public static final int DEFAULT_HEIGHT = 10;
	/**
	 * Width of a tile
	 */
	private int myWidth;
	/**
	 * Image on the tile
	 */
	private final transient Image image;
	/**
	 * If units are on the tile, the ID of the player that owns them. Otherwise,
	 * 0.
	 */
	private Tile tile;

	/**
	 * @return the tile this represents
	 */
	public Tile getTile() {
		return tile;
	}

	/**
	 * @param _tile
	 *            the tile this will now represent
	 */
	public void setTile(final Tile _tile) {
		tile = _tile;
	}

	/**
	 * Constructor
	 * 
	 * @param url
	 *            The filename of an image representing the tile
	 */
	public GUITile(final URL url) {
		super();
		image = getToolkit().getImage(url);
	}

	/**
	 * Draw my Image representation of the tile, then, if there is a unit or
	 * building on the tile, draws a circle or square (respectively) in that
	 * player's color on itself.
	 * 
	 * @param pen
	 *            the graphics context
	 */
	@Override
	public void paint(final Graphics pen) {
		super.paintComponent(pen); // clears the frame
		pen.drawImage(image, 0, 0, myWidth, getHeight(), Color.white, this);
		if (tile.getUnitOwner() > 0) {
			setPlayerColor(pen);
			pen.fillOval(getWidth() / 4, getHeight() / 4, getWidth() / 2,
					getHeight() / 2);
		}
		if (tile.getUnitOwner() < 0) {
			setPlayerColor(pen);
			pen.fillRect(getWidth() / 4, getHeight() / 4, getWidth() / 2,
					getHeight() / 2);
		}
	}

	/**
	 * A mapping from player number to color.
	 */
	private static final HashMap<Integer, Color> PLAYER_COLOR = new HashMap<Integer, Color>();//NOPMD
	static {
		PLAYER_COLOR.put(Integer.valueOf(1), Color.black);
		PLAYER_COLOR.put(Integer.valueOf(2), Color.orange);
		PLAYER_COLOR.put(Integer.valueOf(3), Color.cyan);
		PLAYER_COLOR.put(Integer.valueOf(4), Color.darkGray);
		PLAYER_COLOR.put(Integer.valueOf(5), Color.gray);
		PLAYER_COLOR.put(Integer.valueOf(6), Color.green);
		PLAYER_COLOR.put(Integer.valueOf(7), Color.lightGray);
		PLAYER_COLOR.put(Integer.valueOf(8), Color.magenta);
		PLAYER_COLOR.put(Integer.valueOf(9), Color.blue);
		// ESCA-JAVA0076:
		PLAYER_COLOR.put(Integer.valueOf(10), Color.pink);
		PLAYER_COLOR.put(Integer.valueOf(11), Color.red);
		PLAYER_COLOR.put(Integer.valueOf(12), Color.yellow);
	}

	/**
	 * Returns the player color of the owner of the unit on the tile. (Since I
	 * plan to add buildings and maybe something else soon, I plan to rewrite
	 * that bit of backend code and remove the code here that assumes the only
	 * kind of module is a unit.) This is mainly to clean up paint().
	 * 
	 * @param pen
	 */
	public void setPlayerColor(final Graphics pen) {
		pen.setColor(PLAYER_COLOR.get(Integer.valueOf(Math.abs(tile
				.getUnitOwner()))));
	}

	/**
	 * To prevent PMD's "don't create objects in loops" warning
	 * 
	 * @param filename
	 *            The parameter to pass to the constructor
	 * @return A new GUITile
	 */
	public static GUITile createGUITile(final URL filename) {
		return new GUITile(filename);
	}
}
