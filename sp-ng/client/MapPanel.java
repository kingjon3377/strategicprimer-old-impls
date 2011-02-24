package client;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JPanel;

/**
 * The main map view for the player (and possibly the admin) GUI
 * 
 * @author Jonathan Lovelace
 */
public class MapPanel extends JPanel {
	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = -6232212994661106355L;
	/**
	 * The client-side map
	 */
	private final ClientMap map;
	/**
	 * Preferred size per tile, in pixels. To avoid "magic number" warnings.
	 */
	private static final int PREFERRED_TILE_SIZE = 10;

	/**
	 * Constructor.
	 * 
	 * @param api Passed to the map constructor.
	 * @throws ClassNotFoundException
	 *             on deserialization error while creating the map
	 * @throws IOException
	 *             on I/O error while creating the map
	 */
	public MapPanel(final ClientAPIWrapper api) throws IOException,
			ClassNotFoundException {
		map = new ClientMap(api);
		setPreferredSize(new Dimension(map.getCols() * PREFERRED_TILE_SIZE,
				map.getRows() * PREFERRED_TILE_SIZE));
		setMinimumSize(getPreferredSize());
		//
	}
}
