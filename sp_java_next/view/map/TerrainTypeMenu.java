package view.map;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPopupMenu;

import model.location.TerrainType;
import model.location.Tile;
/**
 * A popup menu to allow an admin to change a tile's terrain type
 * @author Jonathan Lovelace
 *
 */
public final class TerrainTypeMenu extends JPopupMenu implements ActionListener {
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = -8078872146001436461L;
	/**
	 * The tile that this menu would change
	 */
	private GUITile tile;
	/**
	 * The singleton object
	 */
	public static final TerrainTypeMenu MENU = new TerrainTypeMenu();
	/**
	 * Constructor
	 */
	private TerrainTypeMenu() {
		super();
		for (TerrainType type : TerrainType.values()) {
			add(type.toString()).addActionListener(this);
		}
	}
	/**
	 * Show the menu in a component
	 */
	@Override
	public void show(final Component invoker, final int xCoord, final int yCoord) {
		if (invoker instanceof GUITile) {
			tile = (GUITile) invoker;
		}
		super.show(invoker, xCoord, yCoord);
	}
	/**
	 * Handle menu item clicks
	 * @param event The event we're handling
	 */
	@Override
	public void actionPerformed(final ActionEvent event) {
		if (tile != null) {
			tile.getTile().setTerrain(TerrainType.getTileType(event.getActionCommand()));
			tile.refreshTerrainImage();
		}
	}
}
