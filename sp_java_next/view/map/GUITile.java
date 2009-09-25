package view.map;

import java.awt.BorderLayout;
import java.awt.Color;
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
	 * 
	 */
	private static final long serialVersionUID = -189052820681357406L;
	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(GUITile.class
			.getName());

	/**
	 * Constructor, to make warnings go away.
	 * 
	 * @param _tile
	 *            The tile this is the GUI for
	 */
	public GUITile(final Tile _tile) {
		super(new BorderLayout());
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
	}
}
