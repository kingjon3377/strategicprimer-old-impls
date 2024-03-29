package view.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EnumMap;
import java.util.EnumSet;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import model.building.SimpleBuilding;
import model.map.SPMap;
import model.map.TerrainObject;
import model.map.Tile;
import model.map.TileType;
import model.module.ActionHandler;
import model.module.SPModule;
import model.unit.SimpleUnit;
import model.unit.UnitAction;
import view.util.ActionMenu;
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
	 * The currently selected tile
	 */
	private Tile selectedTile = null;
	/**
	 * Whether we've set up stuff on the containing frame yet. (This can't be
	 * done in the constructor because this panel is created before the frame.)
	 */
	private boolean initialized;

	/**
	 * The action menu; we need to tell it what actions are supported by the
	 * currently selected unit every time a new unit is selected.
	 */
	private final ActionMenu actionMenu;
	/**
	 * The currently selected action.
	 */
	private UnitAction action = UnitAction.Cancel;

	/**
	 * The object to run actions. FIXME: This should be in the map, or
	 * something.
	 */
	private final ActionHandler actionHandler = new ActionHandler();

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
		addMouseListener(new MouseAdapter() {
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
					selectTile(event.getX() / TILE_WIDTH, event.getY()
							/ TILE_HEIGHT);
					// setCurrentCol(event.getX() / TILE_WIDTH);
					// setCurrentRow(event.getY() / TILE_HEIGHT);
				} else {
					selectTile(-1, -1);
					// setCurrentCol(-1);
					// setCurrentRow(-1);
				}
				repaint();
			}

		});
		initialized = false;
		menu = new JMenuBar();
		menu.add(new FileMenu(this));
		menu.add(new TileTypeMenu(this));
		menu.add(new TerrainObjectMenu(this));
		actionMenu = new ActionMenu(this);
		menu.add(actionMenu);
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
				if (pen.getClipBounds() != null
						&& (col * TILE_WIDTH > pen.getClipBounds().x
								+ pen.getClipBounds().width
								|| col * TILE_WIDTH + TILE_WIDTH < pen
										.getClipBounds().x
								|| row * TILE_HEIGHT > pen.getClipBounds().y
										+ pen.getClipBounds().height || row
								* TILE_HEIGHT + TILE_HEIGHT < pen
								.getClipBounds().y)) {
					continue;
				}
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
				} else if (theMap.terrainAt(row, col).getModule() != null
						&& theMap.terrainAt(row, col).getModule() instanceof SimpleUnit) {
					pen.setColor(Color.pink);
					pen.drawLine(col * TILE_WIDTH, row * TILE_HEIGHT, col
							* TILE_WIDTH + TILE_WIDTH, row * TILE_HEIGHT
							+ TILE_HEIGHT);
					pen.drawLine(col * TILE_WIDTH + TILE_WIDTH, row
							* TILE_HEIGHT, col * TILE_WIDTH, row * TILE_HEIGHT
							+ TILE_HEIGHT);
				} else if (theMap.terrainAt(row, col).getModule() != null
						&& theMap.terrainAt(row, col).getModule() instanceof SimpleBuilding) {
					pen.setColor(Color.pink);
					pen.drawLine(col * TILE_WIDTH + TILE_WIDTH / 2, row
							* TILE_HEIGHT, col * TILE_WIDTH + TILE_WIDTH / 2,
							row * TILE_HEIGHT + TILE_HEIGHT);
					pen.drawLine(col * TILE_WIDTH, row * TILE_HEIGHT
							+ TILE_HEIGHT / 2, col * TILE_WIDTH + TILE_WIDTH,
							row * TILE_HEIGHT + TILE_HEIGHT / 2);
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
	 * Set the selected tile.
	 * 
	 * @param col
	 *            the column of the new selected tile
	 * @param row
	 *            the row of the new selected tile
	 */
	public void selectTile(int col, int row) {
		currentCol = col;
		currentRow = row;
		if (currentCol == -1 || currentRow == -1) {
			selectedTile = null;
		} else {
			runAction(selectedTile, theMap.terrainAt(currentRow, currentCol));
			action = UnitAction.Cancel;
			selectedTile = theMap.terrainAt(currentRow, currentCol);
			actionMenu.hideInvalidItems(currentModule() == null ? EnumSet
					.noneOf(UnitAction.class) : currentModule()
					.supportedActions());
		}
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
			repaint();
		} else if (evt != null && "action".equals(evt.getPropertyName())) {
			handleAction(theMap.terrainAt(currentRow, currentCol).getModule(),
					(UnitAction) evt.getNewValue());
		}
	}

	/**
	 * Handle an action. Any actions that require an actor and an object are
	 * here just set as the current action; this method is more for actions that
	 * don't require an object.
	 * 
	 * @param module
	 *            the currently selected module
	 * @param newValue
	 *            the new action
	 */
	private void handleAction(final SPModule module, final UnitAction newValue) {
		action = newValue;
	}

	/**
	 * FIXME: Model-view mixing! This should just call methods in the model.
	 * 
	 * Handle an action that requires an object in addition to an actor.
	 * 
	 * @param old
	 *            the previously selected tile
	 * @param newTile
	 *            the newly selected tile
	 */
	private void runAction(final Tile old, final Tile newTile) {
		actionHandler.runAction(action, old, newTile);
		repaint();
	}

	/**
	 * Load in a new map
	 * 
	 * @param map
	 *            the new map
	 */
	public void setMap(final SPMap map) {
		theMap = map;
		repaint();
	}

	/**
	 * @return the currently selected module, if any
	 */
	public SPModule currentModule() {
		return (selectedTile == null ? null : selectedTile.getModule());
	}
}
