package view.map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;

import model.location.Tile;

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
	private static final Logger LOGGER = Logger.getLogger(GUITile.class
			.getName());
	/**
	 * Is this tile currently selected?
	 */
	private boolean selected = false;

	/**
	 * Constructor, to make warnings go away.
	 * 
	 * @param _tile
	 *            The tile this is the GUI for
	 */
	public GUITile(final Tile _tile) {
		super(new BorderLayout());
		setMinimumSize(new Dimension(GUI_TILE_MIN_SIZE, GUI_TILE_MIN_SIZE));
		setPreferredSize(getMinimumSize());
		setTile(_tile);
	}

	/**
	 * The tile this is the GUI representation of
	 */
	private Tile tile;

	/**
	 * @return The tile this is the GUI representation of
	 */
	public Tile getTile() {
		return tile;
	}

	/**
	 * @param _tile
	 *            The new tile this should represent
	 */
	public final void setTile(final Tile _tile) {
		tile = _tile;
		try {
			final URL url = getClass().getResource(getTerrainTypeString(_tile));
			if (url == null) {
				LOGGER.severe("Couldn't find image for " + _tile.getTerrain());
				return;
			}
			terrainImage = createImage((ImageProducer) url.getContent());
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,
					"I/O exception trying to set up image representing tile "
							+ _tile.getLocation(), e);
		}
		repaint();
	}

	/**
	 * The image representing the tile's terrain
	 */
	private transient Image terrainImage;

	/**
	 * @param _tile
	 *            A tile
	 * @return The resource filename for an image representing the tile's
	 *         terrain
	 */
	private static String getTerrainTypeString(final Tile _tile) {
		return "/" + _tile.getTerrain().toString() + ".png";
	}

	/**
	 * Draw the tile.
	 * 
	 * @param pen
	 *            The graphics context.
	 */
	@Override
	public void paint(final Graphics pen) {
		super.paintComponent(pen);
		pen.drawImage(terrainImage, 0, 0, getWidth(), getHeight(), Color.white,
				this);
		if (selected) {
			pen.setXORMode(getBackground());
			pen.drawRect(0, 0, getWidth(), getHeight());
			pen.drawRect(1, 1, getWidth() - 2, getHeight() - 2);
			pen.drawRect(2, 2, getWidth() - 4, getHeight() - 4);
		}
	}

	/**
	 * @param _selected
	 *            Whether this tile is now selected
	 */
	public void setSelected(final boolean _selected) {
		selected = _selected;
	}

	/**
	 * @return whether this tile is selected
	 */
	public boolean isSelected() {
		return selected;
	}
	/**
	 * Change the terrain image if the tile's terrain has changed
	 */
	public void refreshTerrainImage() {
		final URL url = getClass().getResource(getTerrainTypeString(tile));
		if (url == null) {
			LOGGER.severe("Couldn't find image for " + tile.getTerrain());
			return;
		}
		try {
			terrainImage = createImage((ImageProducer) url.getContent());
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "I/O exception while refreshing terrain image",e);
		}
	}
}
