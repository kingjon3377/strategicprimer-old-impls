package view.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
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
		setPreferredSize(new Dimension(map.getCols() * TILE_WIDTH, map.getRows() * TILE_HEIGHT));
		setMinimumSize(getPreferredSize());
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
		final Window window = new Window(new MapPanel(new SPMap()));
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setVisible(true);
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
