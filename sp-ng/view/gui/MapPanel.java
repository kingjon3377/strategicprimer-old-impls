package view.gui;

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
	 * The map this represents
	 */
	private SPMap theMap;
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
}
