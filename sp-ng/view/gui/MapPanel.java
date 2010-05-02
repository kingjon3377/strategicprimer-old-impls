package view.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.EnumMap;

import javax.swing.JPanel;
import javax.swing.WindowConstants;

import model.map.SPMap;
import model.map.TileType;
import view.util.Window;

/**
 * A graphical representation of the map.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class MapPanel extends JPanel {
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
	}

	/**
	 * Constructor.
	 */
	public MapPanel() {
		this(new SPMap());
		theMap.terrainAt(0, 0).setObject(0);
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
		Color origColor = pen.getColor();
		for (int row = 0; row < theMap.getRows(); row++) {
			for (int col = 0; col < theMap.getCols(); col++) {
				pen.setColor(COLOR_MAP
						.get(theMap.terrainAt(row, col).getType()));
				pen.fillRect(col * TILE_WIDTH, row * TILE_HEIGHT, TILE_WIDTH,
						TILE_HEIGHT);
				if (theMap.terrainAt(row, col).getObject() != -1) {
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
		pen.setColor(origColor);
	}

	/**
	 * Colors to use in the map.
	 */
	private static final EnumMap<TileType, Color> COLOR_MAP = new EnumMap<TileType, Color>(
			TileType.class);
	// ESCA-JAVA0076:
	static {
		COLOR_MAP.put(TileType.DESERT, new Color(249, 233, 28));
		COLOR_MAP.put(TileType.ICE, new Color(153, 153, 153));
		COLOR_MAP.put(TileType.PLAINS, new Color(0, 117, 0));
		COLOR_MAP.put(TileType.SWAMP, new Color(0, 44, 0));
		COLOR_MAP.put(TileType.WATER, new Color(0, 0, 255));
		COLOR_MAP.put(TileType.UNEXPLORED, new Color(255, 255, 255));
	}
}
