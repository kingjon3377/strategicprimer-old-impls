package view.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EnumMap;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import model.map.SPMap;
import model.map.TerrainObject;
import model.map.TileType;
import view.util.TerrainObjectMenu;
import view.util.TileTypeMenu;
import view.util.Window;

/**
 * A graphical representation of the map.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class MapPanel extends JPanel implements PropertyChangeListener {
	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = 7688251350376774189L;
	/**
	 * The map this represents
	 */
	private SPMap theMap;
	/**
	 * How wide a tile will be on this view. TODO: make this changeable at
	 * runtime.
	 */
	private static final int TILE_WIDTH = 10;
	/**
	 * How tall a tile will be on this view. TODO: make this changeable at
	 * runtime.
	 */
	private static final int TILE_HEIGHT = 10;
	/**
	 * The row of the currently selected tile
	 */
	private int currentRow = -1;
	/**
	 * The column of the currently selected tile
	 */
	private int currentCol = -1;

	/**
	 * Whether we've set up stuff on the containing frame yet. (This can't be
	 * done in the constructor because this panel is created before the frame.)
	 */
	private boolean initialized;

	/**
	 * Constructor.
	 * 
	 * @param map
	 *            the map
	 */
	public MapPanel(final SPMap map) {
		theMap = map;
		setPreferredSize(new Dimension(map.getCols() * TILE_WIDTH, map
				.getRows()
				* TILE_HEIGHT));
		setMinimumSize(getPreferredSize());
		addMouseListener(new MouseListener() {
			/**
			 * Select the tile under the cursor as the current tile.
			 * 
			 * @param event
			 *            used to determine the tile in question
			 */
			@Override
			public void mouseClicked(final MouseEvent event) {
				if (event.getX() < getMap().getCols() * TILE_WIDTH
						&& event.getY() < getMap().getRows() * TILE_HEIGHT) {
					setCurrentCol(event.getX() / TILE_WIDTH);
					setCurrentRow(event.getY() / TILE_HEIGHT);
				} else {
					setCurrentCol(-1);
					setCurrentRow(-1);
				}
				repaint();
			}

			/**
			 * @param event
			 *            ignored
			 */
			@Override
			public void mouseEntered(final MouseEvent event) {
				// Do nothing
			}

			/**
			 * @param event
			 *            ignored
			 */
			@Override
			public void mouseExited(final MouseEvent event) {
				// Do nothing
			}

			/**
			 * @param event
			 *            ignored
			 */
			@Override
			public void mousePressed(final MouseEvent event) {
				// Do nothing
			}

			/**
			 * @param event
			 *            ignored
			 */
			@Override
			public void mouseReleased(final MouseEvent event) {
				// Do nothing
			}
		});
		initialized = false;
		menu = new JMenuBar();
		menu.add(new TileTypeMenu(this));
		menu.add(new TerrainObjectMenu(this));
	}

	/**
	 * Constructor.
	 */
	public MapPanel() {
		this(new SPMap());
		theMap.terrainAt(0, 0).setObject(TerrainObject.NOTHING);
	}

	/**
	 * Entry point for the program.
	 * 
	 * @param args
	 *            ignored for now
	 */
	public static void main(final String[] args) {
		// final Window window = new Window(new MapPanel(new SPMap()));
		final Window window = new Window(new MapPanel());
		window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		window.setVisible(true);
	}

	@Override
	public void paint(final Graphics pen) {
		if (!initialized) {
			// ESCA-JAVA0177:
			Component comp;
			for (comp = this; comp != null && !(comp instanceof JFrame); comp = comp
					.getParent()) {
				// do nothing
			}
			if (comp != null) {
				((JFrame) comp).setJMenuBar(menu);
				initialized = true;
				return;
			}
		}
		Color origColor = pen.getColor();
		for (int row = 0; row < theMap.getRows(); row++) {
			for (int col = 0; col < theMap.getCols(); col++) {
				pen.setColor(COLOR_MAP
						.get(theMap.terrainAt(row, col).getType()));
				pen.fillRect(col * TILE_WIDTH, row * TILE_HEIGHT, TILE_WIDTH,
						TILE_HEIGHT);
				if (!(TerrainObject.NOTHING.equals(theMap.terrainAt(row, col)
						.getObject()))) {
					pen.setColor(Color.pink);
					// FIXME: That should be something guaranteed to contrast
					// with the tile type color
					pen.fillOval(col * TILE_WIDTH, row * TILE_HEIGHT,
							TILE_WIDTH, TILE_HEIGHT);
				}
				pen.setColor(Color.black);
				pen.drawRect(col * TILE_WIDTH, row * TILE_HEIGHT, TILE_WIDTH,
						TILE_HEIGHT);
			}
		}
		if (currentRow != -1 && currentCol != -1) {
			pen.setColor(Color.magenta);
			pen.drawRect(currentCol * TILE_WIDTH, currentRow * TILE_HEIGHT,
					TILE_WIDTH, TILE_HEIGHT);
		}
		pen.setColor(origColor);
	}

	/**
	 * @return the map
	 */
	public SPMap getMap() {
		return theMap;
	}

	/**
	 * @param col
	 *            the column of the new selected tile
	 */
	public void setCurrentCol(int col) {
		currentCol = col;
	}

	/**
	 * @param row
	 *            the row of the new selected tile
	 */
	public void setCurrentRow(int row) {
		currentRow = row;
	}

	/**
	 * Colors to use in the map.
	 */
	private static final EnumMap<TileType, Color> COLOR_MAP = new EnumMap<TileType, Color>(
			TileType.class);
	/**
	 * The menu bar. We need to store it as a local variable because when this
	 * panel is initialized it doesn't have a frame to stick the menu on yet.
	 */
	private final JMenuBar menu;
	// ESCA-JAVA0076:
	static {
		COLOR_MAP.put(TileType.DESERT, new Color(249, 233, 28));
		COLOR_MAP.put(TileType.ICE, new Color(153, 153, 153));
		COLOR_MAP.put(TileType.PLAINS, new Color(0, 117, 0));
		COLOR_MAP.put(TileType.SWAMP, new Color(0, 44, 0));
		COLOR_MAP.put(TileType.WATER, new Color(0, 0, 255));
		COLOR_MAP.put(TileType.UNEXPLORED, new Color(255, 255, 255));
	}

	/**
	 * Handle property changes; at present just tile type changes.
	 * 
	 * @param evt
	 *            the event we're handling
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt != null && "tiletype".equals(evt.getPropertyName())) {
			theMap.terrainAt(currentRow, currentCol).setType(
					(TileType) evt.getNewValue());
			repaint();
		} else if (evt != null && "terr_obj".equals(evt.getPropertyName())) {
			theMap.terrainAt(currentRow, currentCol).setObject(
					(TerrainObject) evt.getNewValue());
		}
	}
}
