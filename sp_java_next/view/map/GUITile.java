package view.map;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.ImageProducer;
import java.io.IOException;

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
			terrainImage = createImage((ImageProducer) getClass().getResource(
					getTerrainTypeString(_tile)).getContent());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaint();
	}

	private transient Image terrainImage;

	private static String getTerrainTypeString(Tile _tile) {
		throw new IllegalStateException("Unimplemented");
	}
}
