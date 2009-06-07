package finalproject;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import finalproject.astar.Location;

/**
 * GUI for the map. It has a two-dimensional array of GUITiles, which do all the
 * work of drawing the map, and references to the panel of labels (so it can
 * refresh it at appropriate times) and to the main Game object (for the map and
 * the two remembered tiles). It knows how big the map is.
 * 
 * @author jsl7
 * 
 */
public class MapPanel extends JPanel {
	/**
	 * A listener to scroll the map.
	 */
	public final ScrollListener listener;
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = -2820645668170153242L;
	/**
	 * The graphical tiles that make up the map
	 */
	private final GUITile[][] tiles; // NOPMD
	/**
	 * How many rows there are.
	 */
	private final int numRows; // NOPMD
	/**
	 * How many columns there are
	 */
	private final int numCols; // NOPMD
	/**
	 * How many rows are visible? FIXME: Should somehow vary with window size.
	 */
	private final transient int visibleRows;
	/**
	 * How many columns are visible? FIXME: Should somehow vary with window
	 * size.
	 */
	private final transient int visibleCols;
	/**
	 * The X offset.
	 */
	private transient int offsetX;
	/**
	 * The Y offset
	 */
	private transient int offsetY;
	/**
	 * The button to scroll up
	 */
	private final transient JButton northButton;
	/**
	 * The button to scroll down
	 */
	private final transient JButton southButton;
	/**
	 * The button to scroll right (or is it left?)
	 */
	private final transient JButton eastButton;
	/**
	 * The button to scroll left (or is it right?)
	 */
	private final transient JButton westButton;
	/**
	 * The panel the tiles are displayed on when we allow scrolling
	 */
	private final transient JPanel panel;

	/**
	 * Constructor: Sets up a full-sized (showing all tiles at once) GUI map of
	 * the specified size, associating the GUITiles with the appropriate tiles
	 * in the game's map and passing them images based on the tiles' terrain
	 * type.
	 * 
	 * @param rows
	 *            The number of rows in the map
	 * @param cols
	 *            The number of columns in the map
	 * @param terrainIconFiles
	 *            An array of filenames pointing to images representing the
	 *            various terrain types
	 */
	public MapPanel(final int rows, final int cols,
			final TerrainIconFilenames terrainIconFiles) {
		super(new GridLayout(rows, cols));
		visibleRows = numRows = rows;
		visibleCols = numCols = cols;
		offsetX = offsetY = 0;
		tiles = new GUITile[rows][cols];
		createTiles(rows, cols, terrainIconFiles);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				add(tiles[i][j]);
				tiles[i][j].addMouseListener(TileListener.createListener(
						tiles[i][j], this));
			}
		}
		northButton = new JButton();
		southButton = new JButton();
		eastButton = new JButton();
		westButton = new JButton();
		panel = null;
		listener = null;
	}

	/**
	 * Factored out of the constructors
	 * 
	 * @param rows
	 *            How many rows of tiles to create
	 * @param cols
	 *            How many columns of tiles to create
	 * @param terrainIconFiles
	 *            Filenames to draw tile images from
	 */
	private void createTiles(final int rows, final int cols,
			final TerrainIconFilenames terrainIconFiles) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				tiles[i][j] = GUITile.createGUITile(terrainIconFiles.get(Game
						.getGame().getMap().getTile(Location.location(i, j))
						.getTerrainType()));
				tiles[i][j].setTile(Game.getGame().getMap().getTile(
						Location.location(i, j)));
			}
		}
	}

	/**
	 * Constructor: Sets up a partial GUI map of the specified size, associating
	 * the GUITiles with the appropriate tiles in the game's map and passing
	 * them images based on the tiles' terrain type.
	 * 
	 * @param rows
	 *            The number of rows in the map
	 * @param cols
	 *            The number of columns in the map
	 * @param terrainIconFiles
	 *            An array of filenames pointing to images representing the
	 *            various terrain types
	 * @param windowRows
	 *            The number of rows visible in the map
	 * @param windowCols
	 *            The number of columns visible in the map
	 */
	public MapPanel(final int rows, final int cols,
			final TerrainIconFilenames terrainIconFiles, final int windowRows,
			final int windowCols) {
		super(new BorderLayout());
		listener = new ScrollListener(this);
		northButton = new JButton(new ImageIcon("uparrow.jpg"));
		northButton.addMouseListener(listener);
		add(northButton, BorderLayout.NORTH);
		southButton = new JButton(new ImageIcon("downarrow.jpg"));
		southButton.addMouseListener(listener);
		add(southButton, BorderLayout.SOUTH);
		eastButton = new JButton(new ImageIcon("rightarrow.jpg"));
		eastButton.addMouseListener(listener);
		add(eastButton, BorderLayout.EAST);
		westButton = new JButton(new ImageIcon("leftarrow.jpg"));
		westButton.addMouseListener(listener);
		add(westButton, BorderLayout.WEST);

		panel = new JPanel();
		panel.setLayout(new GridLayout(rows, cols));
		numRows = rows;
		numCols = cols;
		visibleRows = windowRows;
		visibleCols = windowCols;
		offsetX = offsetY = 0;
		tiles = new GUITile[rows][cols];
		createTiles(rows, cols, terrainIconFiles);
		add(panel, BorderLayout.CENTER);
		shiftMap();
	}

	/**
	 * Shift the map to fit the new offset, making scrolling off the edge
	 * impossible. Should only be called if panel != null (i.e., if we have a
	 * partial map and buttons and all.)
	 */
	private void shiftMap() {
		northButton.removeMouseListener(listener);
		if (offsetX != 0) {
			northButton.addMouseListener(listener);
		}
		southButton.removeMouseListener(listener);
		if (offsetX + visibleRows != numRows) {
			southButton.addMouseListener(listener);
		}
		westButton.removeMouseListener(listener);
		if (offsetY != 0) {
			westButton.addMouseListener(listener);
		}
		eastButton.removeMouseListener(listener);
		if (offsetY + visibleCols != numCols) {
			eastButton.addMouseListener(listener);
		}
		panel.removeAll();
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				if (tileIsVisible(i, j)) {
					panel.add(tiles[i][j]);
				}
			}
		}
	}

	/**
	 * Is the given tile visible?
	 * 
	 * @param iCoord
	 *            The row? coordinate
	 * @param jCoord
	 *            The column? coordinate
	 * @return Whether the tile there is visible (i.e. on-screen)
	 */
	private boolean tileIsVisible(final int iCoord, final int jCoord) {
		return iCoord >= offsetX && iCoord < visibleRows + offsetX
				&& jCoord >= offsetY && jCoord < visibleCols + offsetY;
	}

	/**
	 * Move the (visible) map
	 * 
	 * @param source
	 *            Where this instruction came from.
	 */
	public void moveMap(final Object source) {
		if (northButton.equals(source)) {
			offsetY--;
			shiftMap();
		} else if (southButton.equals(source)) {
			offsetY++;
			shiftMap();
		} else if (eastButton.equals(source)) {
			offsetX++;
			shiftMap();
		} else if (westButton.equals(source)) {
			offsetX--;
			shiftMap();
		}
	}

	/**
	 * Handle a click on the given tile, based on the current mode. The button
	 * parameter is currently not used, but I plan to add some usage for it
	 * later.
	 * 
	 * @param iCoord
	 *            The x coordinate of the tile
	 * @param jCoord
	 *            The y coordinate of the tile
	 */
	public void handleClick(final int iCoord, final int jCoord) {
		if (Game.getGame().getTile1() == null) {
			Game.getGame().setTile1(tiles[iCoord][jCoord].getTile());
		} else {
			switch (Game.getGame().getMode()) {
			case Game.MOVE_MODE:
				handleMove(iCoord, jCoord);
				break;
			case Game.ATTACK_MODE:
				handleAttack(iCoord, jCoord);
				break;
			case Game.INFO_MODE:
				Game.getGame().setMode(Game.NO_MODE);
				break;
			case Game.RANGED_ATK_MODE:
				handleRanged(iCoord, jCoord);
				break;
			case Game.BUILD_MODE:
				Game.getGame().setTile1(tiles[iCoord][jCoord].getTile());
				break;
			default:
				throw new IllegalStateException("Unknown mode!");
			}
		}
		repaint();
	}

	/**
	 * @param indexOne
	 * @param indexTwo
	 */
	private void handleRanged(final int indexOne, final int indexTwo) {
		if (Game.getGame().getTile2() == null) {
			Game.getGame().setTile2(tiles[indexOne][indexTwo].getTile());
			if ((Game.getGame().getTile1() == Game.getGame().getTile2())
					|| (Game.getGame().getTile1().getModuleOnTile() == null)
					|| (!(Game.getGame().getTile1().getModuleOnTile() instanceof SimpleUnit))
					|| (Game.getGame().getTile1().getModuleOnTile().getOwner() != Game
							.getGame().getPlayer())
					|| (Game.getGame().getTile2().getModuleOnTile() == null)
					|| (Game.getGame().getTile2().getModuleOnTile().getOwner() == Game
							.getGame().getPlayer())
					|| (!(((SimpleUnit) Game.getGame().getTile1()
							.getModuleOnTile()).checkRangedAttack(Game
							.getGame().getTile2().getModuleOnTile())))) {
				Game.getGame().setMode(Game.NO_MODE);
			} else {
				((SimpleUnit) Game.getGame().getTile1().getModuleOnTile())
						.rangedAttack(Game.getGame().getTile2()
								.getModuleOnTile());
				Game.getGame().setMode(Game.NO_MODE);
			}
		}
	}

	/**
	 * @param iCoord
	 * @param jCoord
	 */
	private void handleAttack(final int iCoord, final int jCoord) {
		if (Game.getGame().getTile2() == null) {
			Game.getGame().setTile2(tiles[iCoord][jCoord].getTile());
			if ((Game.getGame().getTile1() != Game.getGame().getTile2())
					&& (Game.getGame().getTile1().getModuleOnTile() != null)
					&& ((Game.getGame().getTile1().getModuleOnTile() instanceof SimpleUnit))
					&& (Game.getGame().getTile1().getModuleOnTile().getOwner() == Game
							.getGame().getPlayer())
					&& (Game.getGame().getTile2().getModuleOnTile() != null)
					&& (Game.getGame().getTile2().getModuleOnTile().getOwner() != Game
							.getGame().getPlayer())
					&& (((SimpleUnit) Game.getGame().getTile1()
							.getModuleOnTile()).checkAttack(Game.getGame()
							.getTile2().getModuleOnTile()))) {
				((SimpleUnit) Game.getGame().getTile1().getModuleOnTile())
						.attack(Game.getGame().getTile2().getModuleOnTile());
			}
			Game.getGame().setMode(Game.NO_MODE);
		}
	}

	/**
	 * @param iCoord
	 * @param jCoord
	 */
	private void handleMove(final int iCoord, final int jCoord) {
		if (Game.getGame().getTile2() == null) {
			Game.getGame().setTile2(tiles[iCoord][jCoord].getTile());
			if (Game.getGame().getTile1() == Game.getGame().getTile2()) {
				Game.getGame().setMode(Game.NO_MODE);
			} else if (Game.getGame().getTile1().getModuleOnTile() == null) {
				Game.getGame().setMode(Game.NO_MODE);
			} else if ((Game.getGame().getTile1().getModuleOnTile() instanceof SimpleUnit)) {
				if (Game.getGame().getTile1().getModuleOnTile().getOwner() == Game
						.getGame().getPlayer()) {
					if (((SimpleUnit) Game.getGame().getTile1()
							.getModuleOnTile()).checkMove(Game.getGame()
							.getTile2(), Game.getGame().getMap())) {
						((SimpleUnit) Game.getGame().getTile1()
								.getModuleOnTile()).move(Game.getGame()
								.getTile2(), Game.getGame().getMap());
						Game.getGame().setMode(Game.NO_MODE);
					} else {
						Game.getGame().setMode(Game.NO_MODE);
					}
				} else {
					Game.getGame().setMode(Game.NO_MODE);
				}
			} else {
				Game.getGame().setMode(Game.NO_MODE);
			}
		}
	}

	/**
	 * When we repaint, we need to
	 */
	@Override
	public void repaint() {
		LabelPanel.getPanel().repaint();
		for (int i = 0; i < getComponentCount(); i++) {
			this.getComponent(i).repaint();
		}
		super.repaint();
	}
}
