/**
 * 
 */
package view.map;

import java.awt.GridLayout;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import model.location.SPMap;
import model.location.Tile;

/**
 * @author Jonathan Lovelace
 * 
 */
public class GUIMap extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8873153191640697473L;
	/**
	 * The tiles on the map
	 */
	private final transient Map<Point, GUITile> tiles;

	/**
	 * Constructor
	 * 
	 * @param map
	 *            the map this class is displaying
	 */
	public GUIMap(final SPMap map) {
		super(new GridLayout(map.getSizeRows(),map.getSizeCols()));
		tiles = new HashMap<Point, GUITile>();
		for (int i = 0; i < map.getSizeRows(); i++) {
			for (int j = 0; j < map.getSizeCols(); j++) {
				initializeTile(i, j, map.getTileAt(i, j));
			}
		}
		repaint();
	}

	/**
	 * @param row
	 *            the row number
	 * @param col
	 *            the column number
	 * @param tile
	 *            The tile at those coordinates that needs a GUI
	 */
	private void initializeTile(final int row, final int col, final Tile tile) {
		initializeTile(new Point(row, col),tile);
	}
	/**
	 * @param point The coordinates of a tile
	 * @param tile The tile at those coordinates that needs a GUI
	 */
	private void initializeTile(final Point point, final Tile tile) {
		if (tiles.containsKey(point)) {
			tiles.get(point).setTile(tile);
		} else {
			tiles.put(point, new GUITile(tile));
		}
		add(tiles.get(point));
	}
	/**
	 * TODO: Is this really needed?
	 * 
	 * @param row
	 *            A row number
	 * @param col
	 *            A column number
	 * @return The tile at that position
	 */
	public GUITile tileAt(final int row, final int col) {
		return tileAt(new Point(row,col));
	}
	/**
	 * @param point A set of coordinates
	 * @return The tile at those coordinates
	 */
	public GUITile tileAt(final Point point) {
		return tiles.get(point);
	}
}
