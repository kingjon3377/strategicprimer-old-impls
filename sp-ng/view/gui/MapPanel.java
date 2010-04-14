package view.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.map.SPMap;
import view.util.Window;
/**
 * A graphical representation of the map.
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
	 * How wide a tile will be on this view. TODO: make this changeable at runtime.
	 */
	private static final int TILE_WIDTH = 10;
	/**
	 * How tall a tile will be on this view. TODO: make this changeable at runtime.
	 */
	private static final int TILE_HEIGHT = 10;
	/**
	 * Constructor.
	 * @param map the map
	 */
	public MapPanel(final SPMap map) {
		theMap = map;
	}
	/**
	 * Constructor.
	 */
	public MapPanel() {
		this(new SPMap());
	}
	/**
	 * Entry point for the program.
	 * @param args ignored for now
	 */
	public static void main(final String[] args) {
		new Window(new MapPanel(new SPMap())).setVisible(true);
	}
	@Override
	public void paint(final Graphics pen) {
		Color origColor = pen.getColor();
		for (int row = 0; row < theMap.getRows(); row++) {
			for (int col = 0; col < theMap.getCols(); col++) {
				pen.setColor(Color.blue);
				pen.fillRect(col * TILE_WIDTH, row * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
				pen.setColor(Color.black);
				pen.drawRect(col * TILE_WIDTH, row * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
			}
		}
		pen.setColor(origColor);
	}
}
