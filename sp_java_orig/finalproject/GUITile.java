package finalproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import finalproject.astar.Tile;

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
	 * A version UID for serialization
	 */
	private static final long serialVersionUID = 5998100741966380772L;
	/**
	 * The tile's default width
	 */
	public static final int DEFAULT_WIDTH = 10;
	/**
	 * The tile's default height
	 */
	public static final int DEFAULT_HEIGHT = 10;
	/**
	 * The image representing the tile's terrain
	 */
	private final Image image;

	/**
	 * The tile this class is the GUI representation of
	 */
	private Tile tile;
	/**
	 * @return the tile this class is the GUI representation of
	 */
	public Tile getTile() {
		return tile;
	}
	/**
	 * @param _tile the tile this class is the GUI representation of
	 */
	public void setTile(final Tile _tile) {
		tile = _tile;
	}
	/**
	 * Constructor
	 * @param url A URL for the image to represent this tile's terrain.
	 */
	public GUITile(final URL url) {
		super();
		image = getToolkit().getImage(url);
	}

	/**
	 * Draw my Image representation of the tile, then, if there is a unit or
	 * building on the tile, draws a circle or square (respectively) in that
	 * player's color on itself.
	 * @param pen The graphics context.
	 */
	@Override
	public void paint(final Graphics pen) {
		super.paintComponent(pen); // clears the frame
		pen.drawImage(image, 0, 0, getWidth(), getHeight(), Color.white, this);
		if (tile.getUnitOwner() > 0) {
			setPlayerColor(pen);
			pen.fillOval(getWidth() / 4, getHeight() / 4, getWidth() / 2, getHeight() / 2);
		}
		if (tile.getUnitOwner() < 0) {
			setPlayerColor(pen);
			pen.fillRect(getWidth() / 4, getHeight() / 4, getWidth() / 2, getHeight() / 2);
		}
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
		pen.setColor(PLAYER_COLORS.containsKey(Integer.valueOf(Math.abs(tile
				.getUnitOwner()))) ? PLAYER_COLORS.get(Integer.valueOf(Math
				.abs(tile.getUnitOwner()))) : Color.white);
	}
	/**
	 * A mapping from player number to player color.
	 */
	private static final Map<Integer, Color> PLAYER_COLORS = new HashMap<Integer, Color>();
	static {
		PLAYER_COLORS.put(1, Color.black);
		PLAYER_COLORS.put(2, Color.orange);
		PLAYER_COLORS.put(3, Color.cyan);
		PLAYER_COLORS.put(4, Color.darkGray);
		PLAYER_COLORS.put(5, Color.gray);
		PLAYER_COLORS.put(6, Color.green);
		PLAYER_COLORS.put(7, Color.lightGray);
		PLAYER_COLORS.put(8, Color.magenta);
		PLAYER_COLORS.put(9, Color.blue);
		// ESCA-JAVA0076:
		PLAYER_COLORS.put(10, Color.pink);
		PLAYER_COLORS.put(11, Color.red);
		PLAYER_COLORS.put(12, Color.yellow);
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
