/**
 * 
 */
package view.map;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import view.main.ActionsMenu;

import model.location.IPoint;
import model.location.Point;
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
	 * The tiles on the map.
	 */
	private final transient Map<Point, GUITile> tiles;
	/**
	 * The currently selected tile.
	 */
	private transient GUITile selected;
	/**
	 * The listener to handle selection changes.
	 */
	private transient SelectionListener slist;

	/**
	 * Set the SelectionListener.
	 * 
	 * @param list
	 *            the new SelectionListener
	 */
	public void setSelectionListener(final SelectionListener list) {
		slist = list;
		for (GUITile tile : tiles.values()) {
			tile.addMouseListener(slist);
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param map
	 *            the map this class is displaying
	 */
	public GUIMap(final SPMap map) {
		super(new GridLayout(map.getSizeRows(), map.getSizeCols()));
		tiles = new HashMap<Point, GUITile>();
		reloadMap(map);
		repaint();
		setPreferredSize(new Dimension(map.getSizeCols()
				* tiles.get(new Point(0, 0)).getWidth(), map.getSizeRows()
				* tiles.get(new Point(0, 0)).getHeight()));
		ActionsMenu.ACTIONS_MENU.setMap(this);
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
		initializeTile(new Point(row, col), tile);
	}

	/**
	 * @param point
	 *            The coordinates of a tile
	 * @param tile
	 *            The tile at those coordinates that needs a GUI
	 */
	private void initializeTile(final Point point, final Tile tile) {
		if (tiles.containsKey(point)) {
			tiles.get(point).setTile(tile);
		} else {
			tiles.put(point, new GUITile(tile));
		}
		add(tiles.get(point));
		if (slist != null) {
			tiles.get(point).addMouseListener(slist);
		}
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
		return tileAt(new Point(row, col));
	}

	/**
	 * @param point
	 *            A set of coordinates
	 * @return The tile at those coordinates
	 */
	public GUITile tileAt(final IPoint point) {
		return tiles.get(point);
	}

	/**
	 * Select a new tile.
	 * 
	 * @param selection
	 *            the new selected tile
	 */
	public void select(final GUITile selection) {
		if (selected != null) {
			selected.setSelected(false);
		}
		if (selection != null) {
			selection.setSelected(true);
		}
		selected = selection;
		repaint();
	}

	/**
	 * Is this map an admin view of the map? FIXME: We need a way to switch
	 * between the two; this is a hack.
	 * 
	 * @return whether this is showing an admin view of the map
	 */
	public boolean isAdmin() {
		return true;
	}
	/**
	 * @return the currently selected tile
	 */
	public GUITile getSelection() {
		return selected;
	}
	/**
	 * Refresh the view after a change to the map model.
	 * @param map The map model we're a view of
	 */
	public final void reloadMap(final SPMap map) {
		removeAll();
		tiles.clear();
		for (int i = 0; i < map.getSizeRows(); i++) {
			for (int j = 0; j < map.getSizeCols(); j++) {
				initializeTile(i, j, map.getTileAt(i, j));
			}
		}
		repaint();
	}
	/**
	 * Set all tiles to not being part of a path.
	 */
	public final void clearPaths() {
		for (GUITile tile : tiles.values()) {
			tile.setPartOfAPath(false);
		}
	}
}
