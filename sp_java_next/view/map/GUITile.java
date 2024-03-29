package view.map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.location.TerrainType;
import model.location.Tile;
import model.module.Module;
import model.module.kinds.Fortress;
import model.module.kinds.RootModule;
import view.module.ModuleGUIManager;

/**
 * The graphical representation of a tile.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class GUITile extends JPanel {
	/**
	 * Minimum size for a GUI tile.
	 */
	private static final int GUI_TILE_MIN_SIZE = 50;
	/**
	 * 
	 */
	private static final long serialVersionUID = -189052820681357406L;
	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(GUITile.class.getName());
	/**
	 * Is this tile currently selected?
	 */
	private boolean selected = false;
	/**
	 * Is this tile currently part of a path (showing potential movement)?
	 */
	private boolean partOfAPath = false;

	/**
	 * Constructor, to make warnings go away.
	 * 
	 * @param newTile
	 *            The tile this is the GUI for
	 */
	public GUITile(final Tile newTile) {
		super(new BorderLayout());
		setMinimumSize(new Dimension(GUI_TILE_MIN_SIZE, GUI_TILE_MIN_SIZE));
		setPreferredSize(getMinimumSize());
		setTile(newTile);
	}

	/**
	 * The tile this is the GUI representation of.
	 */
	private Tile tile;

	/**
	 * @return The tile this is the GUI representation of
	 */
	public Tile getTile() {
		return tile;
	}

	/**
	 * @param newTile
	 *            The new tile this should represent
	 */
	public final void setTile(final Tile newTile) {
		tile = newTile;
		try {
			terrainImage = getImage(newTile.getTerrain());
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,
					"I/O exception trying to set up image representing tile "
							+ newTile.getLocation(), e);
		}
		repaint();
	}

	/**
	 * The image representing the tile's terrain.
	 */
	private transient Image terrainImage;

	/**
	 * @param terr
	 *            A terrain type
	 * @return The resource filename for an image representing that terrain
	 */
	private static String getTerrainTypeString(final TerrainType terr) {
		return "/" + terr.toString() + ".png";
	}

	/**
	 * The width in pixels of the border we draw (XOR) if the tile is selected.
	 */
	private static final int SELECTION_BORDER = 3;
	/**
	 * What fraction of the GUITile (in each dimension) a fortress should be
	 * drawn. (This is what the height and width are divided by to produce the
	 * appropriate results.)
	 */
	private static final int FORT_RATIO = 5;

	/**
	 * Draw the tile.
	 * 
	 * @param pen
	 *            The graphics context. TODO: Convert to the new
	 *            multiple-modules-on-a-tile syntax.
	 */
	@Override
	public void paint(final Graphics pen) {
		super.paint(pen);
		pen.drawImage(terrainImage, 0, 0, getWidth(), getHeight(), Color.white, this);
		if (tile.getSelected() instanceof Fortress) {
			drawModule(((Fortress) tile.getSelected()).getSelected(), pen);
			pen.drawImage(ModuleGUIManager.getImage(tile.getSelected()), getWidth()
					/ FORT_RATIO, getHeight() / FORT_RATIO, getWidth() / FORT_RATIO,
					getHeight() / FORT_RATIO, this);
		} else {
			drawModule(tile.getSelected(), pen);
		}
		if (selected) {
			pen.setXORMode(getBackground());
			for (int i = 0; i < SELECTION_BORDER; i++) {
				pen.drawRect(i, i, getWidth() - i * 2, getHeight() - i * 2);
			}
		}
		if (partOfAPath) {
			pen.setXORMode(getBackground());
			pen.fillOval(2 * getWidth() / FORT_RATIO, 2 * getHeight() / FORT_RATIO,
					getWidth() / FORT_RATIO, getHeight() / FORT_RATIO);
		}
	}

	/**
	 * Draw a module.
	 * 
	 * @param mod
	 *            the module to draw
	 * @param pen
	 *            the graphics context
	 */
	private void drawModule(final Module mod, final Graphics pen) {
		if (mod != null && !(mod instanceof RootModule)) {
			pen.drawImage(ModuleGUIManager.getImage(mod), 0, 0, getWidth(), getHeight(),
					this);
		}
	}

	/**
	 * @param isSelected
	 *            Whether this tile is now selected
	 */
	public void setSelected(final boolean isSelected) {
		selected = isSelected;
	}

	/**
	 * @param isPartOfAPath
	 *            whether this tile is now part of a path
	 */
	public void setPartOfAPath(final boolean isPartOfAPath) {
		partOfAPath = isPartOfAPath;
	}

	/**
	 * @return whether this tile is selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Change the terrain image if the tile's terrain has changed.
	 */
	public void refreshTerrainImage() {
		try {
			terrainImage = getImage(tile.getTerrain());
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "I/O exception while refreshing terrain image", e);
		}
		repaint();
	}

	/**
	 * @param terr
	 *            a terrain type
	 * @return an image representing that terrain
	 * @throws IOException
	 *             Thrown by a method used in producing the image
	 */
	private static Image getImage(final TerrainType terr) throws IOException {
		if (!imageMap.containsKey(terr)) {
			imageMap.put(terr, ImageIO.read(GUITile.class.getResource(getTerrainTypeString(terr))));
		} 
		return imageMap.get(terr);
	}

	/**
	 * A cache of terrain type images.
	 */
	private static Map<TerrainType, Image> imageMap = new EnumMap<TerrainType, Image>(
			TerrainType.class);
}
